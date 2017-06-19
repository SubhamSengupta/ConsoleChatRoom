import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class PrintMessage extends Thread {
	BufferedReader input ;
	Socket sock = null;
	String name;
	String line = null;
	WriteToAll write ;
	
	public PrintMessage(Socket sock){
		this.sock = sock;
	}
	public void run(){
		while(true){
				try {
					input = new BufferedReader(new
							InputStreamReader(sock.getInputStream()));
					line = input.readLine();
				} catch (IOException e) {
					try {
						input.close();
						sock.close();
						} catch (IOException e1) {
						System.out.println("Error in printmessage" + e);
					}
				}
					
			if(line != null ){
				
						if(line.contains("##")){
							name = line.substring(0,line.length() - 2);
							line = name + " is online" ;
							Server.nameList.put(sock,name);
						}
						
						if(line.contains(" is Offline")){
							for (Socket s : Server.socketMap) {
								if(s.equals(sock)){
									Server.socketMap.remove(s);
									Server.nameList.remove(s);
									break;
								}
							}						
						}
						if(line.contains("-p")){
							if(Server.nameList.size() == 1){
								writeToClient(sock, "No other Active Users");
							}else{
								int index = line.indexOf("-p");
								String sourceName = line.substring(0, index - 2);
								String targetNamewithMessage;
								String targetName = "";
								try {
									targetNamewithMessage = line.substring(index + 3);
									int i = 0;
									char c;
									while((c = targetNamewithMessage.toCharArray()[i]) != ' ' ){
										targetName += c;
										i++;
									}
									int mIndex = line.indexOf(targetName);
									String message = line.substring(
											mIndex + targetName.length());
									Socket socket = Server.getSocket(targetName);
									if(socket == null)
										writeToClient(sock, "User is not Available");
									else
										writeToClient(socket, "\tmessage from "
												+ sourceName + ": " + message);
									writeToClient(sock, "(sent)");
								} catch (Exception e) {
									writeToClient(sock, "invalid Command");
								}
							}
													
						}else
						if(line.contains("-who")){
							String names = Server.getOnlineUsers();
							writeToClient(sock,names);
						}else
						if(!Server.socketMap.isEmpty()){
							System.out.println(line);
							write = new WriteToAll(line);
							write.start();
						}else{
							System.out.println("No Active Client");
							break;
						}
			}	
		}
	}
	void writeToClient(Socket sock,String string){
		PrintStream getPrint = null;
		try {
			getPrint = new PrintStream(sock.getOutputStream());
			getPrint.println(string);
		} catch (IOException e) {
			System.out.println("can't write to client");
		}
	}
}
