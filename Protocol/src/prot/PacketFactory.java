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

    public static Packet TaskCompleted(String msg){
        Map args = PacketType.TASK_COMPLETE.emptyParams();
        args.put("msg", msg);

        return new Packet(PacketType.TASK_COMPLETE, args);
    }

    public static Packet Exception(Exception e){
        Map args = PacketType.EXCEPTION.emptyParams();
        args.put("exception", e);

        return new Packet(PacketType.EXCEPTION, args);
    }
}
