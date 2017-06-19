import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	static String name;
	static String Inet = "127.0.0.1" ;
	static String getPort;
	static int Port = 10029;
	static PrintStream sendName;
	public static Socket clientSock = null;
	static Thread print,type;
	public static void main(String[] args){
		try {
			System.out.println("Server Inet Address(Default 127.0.0.1): ");
			Inet = new BufferedReader(new
					InputStreamReader(System.in)).readLine();
			System.out.println("Server Port(Default 10029): ");
			getPort = new BufferedReader(new
					InputStreamReader(System.in)).readLine();
		} catch (IOException e1) {
			System.out.println("Error : " +e1);
		}
		try {
			Port = Integer.parseInt(getPort);
		} catch (NumberFormatException e3) {
			System.out.println("Not a valid Port,Connecting to default Port..");
		}
		try {
			clientSock = new Socket(Inet,Port);
			System.out.println("Connected to the Server");
		} catch (UnknownHostException e2) {
			System.out.println("Host Unreachable");
			System.exit(1);
		} catch (IOException e2) {
			System.out.println("Can't Connect to the Server");
			System.exit(1);
		}
			
		System.out.print("Your Name: ");
		try {
			do{
				name = new BufferedReader(new
						InputStreamReader(System.in)).readLine();
			}while(name.length() == 0);
		} catch (IOException e1) {
			System.out.println("Error : " +e1);
		}
		try {
			sendName = new PrintStream(clientSock.getOutputStream());
			System.out.println("\n\tType \"-help\" to See available Commands\n");
		} catch (IOException e) {
			System.out.println("Can't Connect");
		}
		sendName.println(name + "##");
		print = new Thread (new PrintMessage(clientSock));
		print.start();
		type = new Thread(new WriteClient(clientSock));
		type.start();
	}
	public static String getName(){
		return name;
	}
}
