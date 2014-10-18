/**
 * 
 */

import sl.Slave;

import java.net.ConnectException;

/**
 * @author Vanessa
 *
 */
public class Main{
	
	static final int PORT = 6666;
	static final String DEST = "localhost";
    static final int MIN_SECONDS_BETWEEN_TRIES = 2;
	static final int MAX_SECONDS_BETWEEN_TRIES = 60;
    static final int SLEEP_GROWTH_TAX = 2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while(true){
            int actualWait = MIN_SECONDS_BETWEEN_TRIES;

            try{
                System.getProperties().list(System.out);

                while(true) {
                    try {
                        Slave slave = new Slave(DEST, PORT);
                        while (true) {
                            slave.send(
                                    slave.interpretPacket(
                                            slave.receive()));
                        }
                    } catch (ConnectException cE) {
                        System.out.println("Connection refused!");
                        System.out.println("Going to sleep for " + actualWait + " seconds...");
                        Thread.sleep(actualWait * 1000);
                        actualWait = getWaitSeconds(actualWait);
                    }
                }
			}
			catch(Exception e){
				//do nothing
                e.printStackTrace();
			}
		}
	}

    private static int getWaitSeconds(int actualWait){
        return (actualWait * 2 > MAX_SECONDS_BETWEEN_TRIES) ? MAX_SECONDS_BETWEEN_TRIES : actualWait * 2;
    }

}
