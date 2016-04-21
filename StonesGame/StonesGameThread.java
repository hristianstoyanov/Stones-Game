import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;


public class StonesGameThread extends Thread{
	Socket socket1;
	Socket socket2;
	Socket socket3;
	Scanner readerFromSocket1;
	Scanner readerFromSocket2;
	Scanner readerFromSocket3;
	PrintStream writerToSocket1;
	PrintStream writerToSocket2;
	PrintStream writerToSocket3;
	
	public StonesGameThread(Socket s1,Socket s2,Socket s3)throws IOException{
		socket1=s1;
		socket2=s2;
		socket3=s3;
		readerFromSocket1=new Scanner(socket1.getInputStream());
		readerFromSocket2=new Scanner(socket2.getInputStream());
		readerFromSocket3=new Scanner(socket3.getInputStream());
		writerToSocket1=new PrintStream(socket1.getOutputStream());
		writerToSocket2=new PrintStream(socket2.getOutputStream());
		writerToSocket3=new PrintStream(socket3.getOutputStream());
	}
	
	public void run(){
		try{
			int stones=this.player1();
			int N=this.player2();
			this.startGame(stones, N);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public int player1()throws IOException{
		int stones=0;
		int i=0;
		boolean flag=true;
		while(flag){
			try{
				writerToSocket1.println("Enter number between 20 and 100");
				stones=readerFromSocket1.nextInt();
				if(stones<20||stones>100){
					i++;
					if(i>=3){
						writerToSocket1.println("Disqualified");
						socket1.close();
					}
					else
						throw new InvalidInfoException();
				}
			}catch(InputMismatchException imme){
				continue;
			}catch(InvalidInfoException iie){
				continue;
			}
			writerToSocket1.println("The game continues");
			flag=false;
		}
		return stones;
	}
	
	public int player2()throws IOException{
		int N=0;
		int playerFaults=0;
		boolean flag=true;
		while(flag){
			try{
				writerToSocket2.println("Enter number between 3 and 10");
				N=readerFromSocket2.nextInt();
				if(N<3||N>10){
					playerFaults++;
					if(playerFaults>=3){
						writerToSocket2.println("Disqualified");
						socket2.close();
					}else
						throw new InvalidNumberException();
				}
				}catch(InputMismatchException imme){
					continue;
				}catch(InvalidNumberException ine){
					continue;
				}
			 writerToSocket1.println("The game continues");
			 flag=false;
		}
		
		return N;
	}
	
	public void startGame(int stones,int N)throws IOException {
		while(stones>0){
				if(!socket3.isClosed()){
					stones=takeStones(stones, N, writerToSocket3, readerFromSocket3);
					if(stones==0){
						writerToSocket3.println("You won");
						writerToSocket2.println("Player 3 won");
						writerToSocket1.println("Player 3 won");
						break;
					}
				}
				if(!socket1.isClosed()){
					stones=takeStones(stones, N, writerToSocket1, readerFromSocket1);
					if(stones==0){
						writerToSocket1.println("You won");
						writerToSocket2.println("Player 1 won");
						writerToSocket3.println("Player 1 won");
						break;
					}
				}
				if(!socket2.isClosed()){
					stones=takeStones(stones, N, writerToSocket2, readerFromSocket2);
					if(stones==0){
						writerToSocket2.println("You won");
						writerToSocket1.println("Player 2 won");
						writerToSocket3.println("Player 2 won");
						break;
					}
				}
		}	
	}
	
	public int takeStones(int stones, int N, PrintStream writerToSocket, Scanner readerFromSocket) throws IOException{
		int playerFaults=0;
		boolean flag=true;
		 
		while(flag){
			try {
				writerToSocket.println("Get stones between 1 and "+N+". "+stones+" left");
				int takenStones=readerFromSocket.nextInt();
				if(takenStones<1||takenStones>N) {
					playerFaults++;
					if(playerFaults>=3){
						writerToSocket.println("Disqualified");
						socket3.close();
					}
					else {
						throw new InvalidNumberException();
					}
				}
				else{
					stones=stones-takenStones;
					flag=false;
				}
			
			}catch(InvalidNumberException i){
				continue;
			}
		}
		return stones;
	}
	
}
