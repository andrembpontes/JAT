import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	ServerSocket serverSocket;
	Socket socket;
	ObjectInputStream in;
	ObjectOutputStream out;
	
	public Server(String dest, int port) throws IOException{
		this.socket = new Socket(dest, port);
	}
	
	boolean accept() throws IOException{
		//
		//this.socket = this.serverSocket.accept();
		this.in = new ObjectInputStream(this.socket.getInputStream());
		this.out = new ObjectOutputStream(this.socket.getOutputStream());
		return this.socket.isConnected();
	}
	
	Object receive() throws ClassNotFoundException, IOException{
		return this.in.readObject();
	}
	
	void send(Object obj) throws IOException{
		this.out.writeObject(obj);
	}
	
}
