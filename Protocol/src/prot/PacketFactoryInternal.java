package prot;

import java.util.Map;
import java.util.Objects;

/**
 * @author Andre Pontes (42845)
 */
public class PacketFactoryInternal {
    private static Map<String, Object> getInternalEmptyParams(){
        return PacketType.INTERNAL_COMMAND.emptyParams();
    }

    private static Packet newInternalCommand(Map<String, Object> params){
        return new Packet(PacketType.INTERNAL_COMMAND, params);
    }

    public static Packet echo(String msg){
        Map<String, Object> params = getInternalEmptyParams();

        params.put("command", InternalCommand.ECHO);
        params.put("echo", msg);

        return newInternalCommand(params);
    }

    public static Packet slaveInfo(SlaveInfo slaveInfo){
        Map<String, Object> params = getInternalEmptyParams();

        params.put("command", InternalCommand.SLAVE_INFO);
        params.put("slave_info", slaveInfo);

        return newInternalCommand(params);
    }
}
