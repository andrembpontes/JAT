package prot;

import java.util.Map;

/**
 * @author Andre Pontes (42845)
 */
public class PacketFactory {
    public static Packet error(Error error){
        Map<String, Object> args = PacketType.ERROR.emptyParams();
        args.put("error", error);

        return new Packet(PacketType.ERROR, args);
    }

    public static Packet taskCompleted(String msg){
        Map<String, Object> args = PacketType.TASK_COMPLETE.emptyParams();
        args.put("msg", msg);

        return new Packet(PacketType.TASK_COMPLETE, args);
    }

    public static Packet exception(Exception e){
        Map<String, Object> args = PacketType.EXCEPTION.emptyParams();
        args.put("exception", e);

        return new Packet(PacketType.EXCEPTION, args);
    }

    public static Packet file(String path, byte[] bytes){
        Map<String, Object> args = PacketType.FILE.emptyParams();

        args.put("path", path);
        args.put("bytes", bytes);

        return new Packet(PacketType.FILE, args);
    }

    public static Packet exec(String command){
        Map<String, Object> args = PacketType.EXEC.emptyParams();

        args.put("command", command);

        return new Packet(PacketType.EXEC, args);
    }

    public static Packet execResult(String[] stdOut, String[] stdErr){
        Map<String, Object> args = PacketType.EXEC_RESULT.emptyParams();

        args.put("stdOut", stdOut);
        args.put("stdErr", stdErr);

        return new Packet(PacketType.EXEC_RESULT, args);
    }
}
