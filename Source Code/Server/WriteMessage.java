import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class WriteMessage extends Thread {
	public BufferedReader getInput;
	public PrintStream getPrint;
	public Socket sock = null;
	String line;
	String message;
	WriteToAll write;
	public void run(){
		while(true){
			getInput = new BufferedReader(new 
					InputStreamReader(System.in));
			try {
				if((message = getInput.readLine()).equalsIgnoreCase("close")){
					System.out.println("Server Terminated");
					line = "terminate : code";
					if(!Server.socketMap.isEmpty()){
						write = new WriteToAll(line);
						write.start();
					}
					System.exit(1);
				}
			} catch (IOException e2) {
				System.out.println("Error here" +e2);
			}
			line = "Server: "+  message;
			if(!Server.socketMap.isEmpty()){
				write = new WriteToAll(line);
				write.start();
			}
		}
	}
}