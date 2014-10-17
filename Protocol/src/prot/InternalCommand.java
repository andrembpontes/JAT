package prot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andre Pontes (42845)
 */
public enum InternalCommand implements Serializable {
    /**
     * Echo a receive message and sent it back by a TaskCompleted Packet
     * aditional params: echo : String
     */
    ECHO ("echo"),

    /**
     * Get slave info
     * aditional params: slave_info : SlaveInfo
     */
    SLAVE_INFO("slave_info");

    String[] params;

    InternalCommand(String... params){
        this.params = params;
    }

    Map<String, Object> emptyParams(){
        Map map = new HashMap<String, Object>();

        for(String param : params)
            map.put(param, null);

        return map;
    }



}
