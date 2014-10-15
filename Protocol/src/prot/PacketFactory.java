package prot;

import java.util.Map;

/**
 * @author Andre Pontes (42845)
 */
public class PacketFactory {
    public static Packet Error(Error error){
        Map args = PacketType.ERROR.emptyParams();
        args.put("error", error);

        return new Packet(PacketType.ERROR, args);
    }
}
