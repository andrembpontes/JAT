package prot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andre Pontes (42845)
 */
public enum PacketType implements Serializable {
    TASK_COMPLETE(),
    /**
     * error : Error
     */
    ERROR("error"),

    /**
     * path : String
     * bytes : byte[]
     */
    FILE("path", "bytes"),

    /**
     * command : String
     */
    EXEC("command"),

    /**
     * command : InternalCommand
     */
    INTERNAL_COMMAND("command");

    String[] params;

    PacketType(String... params){
        this.params = params;
    }

    Map<String, Object> emptyParams(){
        Map map = new HashMap<String, Object>();

        for(String param : params)
            map.put(param, null);

        return map;
    }
}
