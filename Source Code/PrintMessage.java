import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class PrintMessage extends Thread {
	BufferedReader input ;
	Socket sock = null;
	String name;
	String line;
	BufferedReader getInput;
	PrintStream getPrint;
	
	public PrintMessage(Socket sock){
		this.sock = sock;
	}
	public void run(){
		while(true){
				try {
					input = new BufferedReader(new
							InputStreamReader(sock.getInputStream()));
					line = input.readLine();
					
					if(line.equals("terminate : code")){
						System.out.println("Server Closed");
						input.close();
						sock.close();
						System.exit(3);
					}
					if( !line.contains(Client.getName()+": "))
						System.out.println(line);
							
				
				} catch (IOException e) {
					try {
						input.close();
						sock.close();
						} catch (IOException e1) {
						System.out.println("Error in printmessage" + e);
					}
				}
		}
	}
}
