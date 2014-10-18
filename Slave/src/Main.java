/**
 * 
 */

import sl.Slave;

/**
 * @author Vanessa
 *
 */
public class Main{
	
	static final int PORT = 6666;
	static final String DEST = "localhost";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while(true){
			try{
                Slave slave = new Slave(DEST, PORT);
				while(true){
					slave.send(
                            slave.interpretPacket(
                                    slave.receive()));
				}
			}
			catch(Exception e){
				//do nothing
                e.printStackTrace();
			}
		}
	}

}
