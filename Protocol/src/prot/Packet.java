package prot;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Andre Pontes (42845)
 */
public class Packet implements Serializable{
    PacketType type;
    Map<String, Object> params;

    protected Packet(PacketType type, Map<String, Object> params){
        this.type = type;
        if(params.keySet().containsAll(type.emptyParams().keySet()))
            this.params = params;
        else
            throw new InvalidPacketParams();
    }

    public PacketType getType(){
        return this.type;
    }

    public Map<String, Object> getParams(){
        return this.params;
    }

}
