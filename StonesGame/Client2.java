import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client2 {
	public static void main(String[] args) throws UnknownHostException,
			IOException {
		Socket socket = new Socket("192.168.0.101", 1234);
		Scanner input = new Scanner(System.in);
		Scanner clientReader = new Scanner(socket.getInputStream());
		PrintStream clientWriter = new PrintStream(socket.getOutputStream());
		String text;
		
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
