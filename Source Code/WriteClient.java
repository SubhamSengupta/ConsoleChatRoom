import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class WriteClient extends Thread {
	BufferedReader getInput;
	PrintStream getPrint;
	Socket sock = null;
	String line;
	public WriteClient(Socket sock){
		this.sock = sock;
	}
	public void run(){
		while(true){
				getInput = new BufferedReader(new 
						InputStreamReader(System.in));
			try {
				getPrint = new PrintStream(sock.getOutputStream());
				line = getInput.readLine();
				if(line.length() != 0){
					if(line.equalsIgnoreCase("-quit")){
						getPrint.println(Client.getName() + " is Offline");
						getInput.close();
						getPrint.close();
						sock.close();
						System.out.println("You are now Offline");
						System.exit(1);
					}
					if(line.equalsIgnoreCase("-help")){
						System.out.println("\n\t\tList Of available Commands:");
						System.out.println("\t\"-help\" : See available Commands");
						System.out.println("\t\"-who\" : Name of Active users");
						System.out.println("\t\"-quit\" : Exit Chat");
						System.out.println("\t\"-p target message\" : Send a Private message "
								+ "to target\n");
					}else{
						getPrint.println(Client.name + ": " +line);
					}
					
				}else{
					System.out.print("Enter a message: ");
				}
			}catch (IOException e) {
					System.out.println("Error error " +e);
					try {
						getInput.close();
						getPrint.close();
						sock.close();
					} catch (IOException e1) {
						System.out.println("Error occured "+e1);
					}
			}
		}
	}
}