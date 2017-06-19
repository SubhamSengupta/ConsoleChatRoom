import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AddClient extends Thread {
	public static ServerSocket servSock = null;
	public static Socket newSock= null;
	String name ="Server";
	public static Thread newClientThread;
	public AddClient(){
		try {
			servSock = new ServerSocket(10029);
			System.out.println("Server Started. Listening for Client...");
			System.out.println("Server Inet Address: "+ servSock.getInetAddress());
			System.out.println("Server Port: "+ servSock.getLocalPort());
		} catch (IOException e) {
			System.out.println("Can't create the Server");
			try {
				if(servSock != null)
					servSock.close();
			} catch (IOException e1) {
				System.out.println("Can't Close Socket");
			}
			System.exit(1);
		}
	}
	public void run(){
		try {
			
			while(true){
				newSock = servSock.accept();
				Server.socketMap.add(newSock);
				newClientThread= new Thread(new PrintMessage(newSock));
				newClientThread.start();
			}
		} catch (IOException e) {
			System.out.println("Server Terminated ");
			try {
				servSock.close();
				newSock.close();
				newClientThread.interrupt();
				System.exit(2);
			} catch (IOException e1) {
				System.out.println("Error Happened "+e1);;
			}
		}
	}
}
