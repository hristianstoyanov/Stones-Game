import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client1 {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket=new Socket("192.168.1.101",1234);
		Scanner input = new Scanner(System.in);
		Scanner clientReader=new Scanner(socket.getInputStream());
		PrintStream clientWriter=new PrintStream(socket.getOutputStream());
		String inputNumber="Enter number between 3 and 10";
		String text;
	
		System.out.println(clientReader.nextLine());
		while(true) { 
			text=clientReader.nextLine();
			if(text.equals(inputNumber)) {
				System.out.println(text);
				clientWriter.println(input.nextInt());
			}
			else { 
				System.out.println(text);
				break;
			}
		}
	
		while (true) {
			text = clientReader.nextLine();
			if (text.charAt(0) == 'G') {
				System.out.println(text);
				clientWriter.println(input.nextInt());
			}
			else {
				System.out.println(text);
				break;
			}
		}
	}
}
