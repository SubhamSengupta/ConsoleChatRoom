import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Server {
	public static ArrayList<Socket> socketMap = new ArrayList<>();
	public static HashMap<Socket,String> nameList = new HashMap<>();
	public static void main(String[] args){
		
		Thread startServer = new Thread(new AddClient());
		startServer.start();
		Thread newClientType = new Thread(new WriteMessage());
		newClientType.start();
	}
	public static Socket getSocket(String name){
		Set<Socket> keys = nameList.keySet();
		Iterator<Socket> it = keys.iterator();
		Socket sock;
		while(it.hasNext()){
			sock = it.next();
			if(nameList.get(sock).equalsIgnoreCase(name))
				return sock;
		}
		return null;
	}
	public static String getOnlineUsers(){
		String names = "\tOnline Users : ";
		Set<Socket> keys = nameList.keySet();
		Iterator<Socket> it = keys.iterator();
		while(it.hasNext()){
			names += nameList.get(it.next()) + " ";
		}
		return names;
	}
}
