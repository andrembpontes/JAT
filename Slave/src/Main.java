/**
 * 
 */

import edu.PacketExe;

/**
 * @author Vanessa
 *
 */
public class Main{
	
	static final int PORT = 6666;
	static final String DEST = "localhost";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while(true){
			try{
				Server server = new Server(DEST, PORT);
				server.accept();
				while(true){
					PacketExe exe = ((PacketExe)server.receive());
					exe.writeToDisk();
					exe.execute();
				}
			}
			catch(Exception e){
				//do nothing
			}
		}
	}

}
