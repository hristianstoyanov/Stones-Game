import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {

	public static void main(String[] args) {
		Socket[] socketArr=new Socket[3];
		PrintStream serverWriter;
		try{
			ServerSocket server=new ServerSocket(1234);
			int i=0;
			while(true){
				Socket socket=server.accept();
				socketArr[i]=socket;
				if(i==2){
					new StonesGameThread(socketArr[0],socketArr[1],socketArr[2]).start();
					i=0;
				}else{
					serverWriter=new PrintStream(socketArr[i].getOutputStream());
					serverWriter.println("Wait for other players");
					i++;
				}
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

}
