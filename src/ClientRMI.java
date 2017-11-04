import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Timer;

public class ClientRMI extends UnicastRemoteObject implements ClientInterface{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7699384301633005058L;
	GUIInterface chatGUIInterface;
	InterfaceChatSRV serverInterface;
	User user;

	
public ClientRMI() throws RemoteException{
        	chatGUIInterface = new Chat_GUI(this);
        	//updater = new Timer (3000, chatGUIInterface);
        	try {
				 serverInterface = (InterfaceChatSRV) Naming.lookup("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/TestRMI");
			} catch (MalformedURLException | RemoteException | UnknownHostException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
}

public static void main(String[] argv) {
	try {
		new ClientRMI();
	} catch (Exception e) {
		System.err.println("Can't open program:");
        e.printStackTrace();
        System.exit(1);
	}
		
	}

public void login(String userName) {
        		user = new User(userName);
        		System.out.println(userName);
}

@Override
public void addRoom(User user, int id, String name) {
	
	try {
		serverInterface.addRoom(user, id, name);
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		System.out.println(user.getNom());
		e.printStackTrace();
	}
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

public Hashtable<Integer, Room> getRooms() {
	try {
		System.out.println("Rooms getted");
		return serverInterface.getRooms();
	} catch (RemoteException e) {
		e.printStackTrace();
		System.out.println("Rooms not getted");
		return null;
	}
	
}



	/*public Chat_client() {
		
		chatGUI = new Chat_GUI();
		
		
		// connect server
	}*/

}
