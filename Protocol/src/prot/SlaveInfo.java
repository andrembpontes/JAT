package prot;

import java.io.*;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * @author Andre Pontes (42845)
 */
public class SlaveInfo implements Serializable {

    private final SocketAddress socketAddress;
    private final Properties systemProperties;

    public SlaveInfo(SocketAddress socketAddress, Properties systemProperties) {
        this.socketAddress = socketAddress;
        this.systemProperties = systemProperties;
    }

    public Map<String, String> parseSystemProperties(Properties properties){
        Map<String, String> props = new HashMap<String, String>();
        for(Object oProp : properties.keySet()){
            String prop = oProp.toString();
            props.put(prop, properties.getProperty(prop));
        }

        return props;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public Properties getSystemProperties() {
        return systemProperties;
    }

    @Override
    public String toString(){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pOut = new PrintStream(out);

        pOut.println("SLAVE INFO: " + this.socketAddress);
        pOut.println("[OS] = " + this.systemProperties.getProperty("os.name"));
        pOut.println("[USER_NAME] = " + this.systemProperties.getProperty("user.name"));
        pOut.println("[SRV_DIR] = " + this.systemProperties.getProperty("user.dir"));
        pOut.println();
        pOut.println("detailed info:");
        this.systemProperties.list(pOut);

        return out.toString();
    }
}
