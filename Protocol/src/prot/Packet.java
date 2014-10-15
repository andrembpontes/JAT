package prot;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Andre Pontes (42845)
 */
public class Packet implements Serializable{
    PacketType type;
    Map<String, Object> params;

    public Packet(PacketType type, Map<String, Object> params){
        this.type = type;
        if(params.keySet().equals(type.emptyParams().keySet()))
            this.params = params;
        else
            throw new InvalidPacketParams();
    }
}
