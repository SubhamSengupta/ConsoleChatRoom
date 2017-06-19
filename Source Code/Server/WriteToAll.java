import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;


public class WriteToAll{
	public BufferedReader getInput;
	public PrintStream getPrint;
	public Socket sock = null;
	String line;
	public WriteToAll(String line){
		this.line = line;
	}
	public void start(){
		if(!Server.socketMap.isEmpty()){
			for (Socket s : Server.socketMap) {
				try{
				getPrint = new PrintStream(s.getOutputStream());
				getPrint.println(line);
				}catch (IOException e) {
				try {
					getInput.close();
					getPrint.close();
					sock.close();
				} catch (IOException e1) {
					System.out.println("error Here");
				}
				}
			}
		}
	}
}
