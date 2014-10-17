package sl;

import prot.*;

import java.net.Socket;

/**
 * @author Andre Pontes (42845)
 */
public class InternalCommands {
    public static Packet echo(Packet packet){
        return PacketFactory.taskCompleted((String) packet.getParams().get("echo"));
    }

    public static Packet slaveInfo(Packet packet, Socket socket){
        return PacketFactoryInternal.slaveInfo(new SlaveInfo(socket.getLocalSocketAddress(), System.getProperties()));
    }
}
