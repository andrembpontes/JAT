package prot;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andre Pontes (42845)
 */
public enum PacketType {
    FILE("path"),
    EXEC("command"),
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
