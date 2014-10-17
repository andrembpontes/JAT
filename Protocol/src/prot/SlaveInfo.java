package prot;

import java.net.SocketAddress;
import java.util.Properties;

/**
 * @author Andre Pontes (42845)
 */
public class SlaveInfo {

    private final SocketAddress socketAddress;
    private final Properties systemProperties;

    public SlaveInfo(SocketAddress socketAddress, Properties systemProperties) {
        this.socketAddress = socketAddress;
        this.systemProperties = systemProperties;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public Properties getSystemProperties() {
        return systemProperties;
    }

    @Override
    public String toString(){
        String toString = socketAddress.toString() + "\n";
        return toString;
    }
}
