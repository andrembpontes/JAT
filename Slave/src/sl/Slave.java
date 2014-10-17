package sl;

import prot.*;
import prot.Error;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Andre Pontes (42845)
 */
public class Slave {
    Socket socket;
    private ObjectInput in;
    private ObjectOutput out;

    public Slave(String destination, int port) throws IOException {
        this.socket = new Socket(destination, port);
        this.initialiseStreams();
    }

    public boolean isConnected(){
        return this.socket.isConnected();
    }

    public void initialiseStreams() throws IOException {
        this.in = new ObjectInputStream(this.socket.getInputStream());
        this.out = new ObjectOutputStream(this.socket.getOutputStream());
    }

    public void send(Packet packet) throws IOException {
        this.out.writeObject(packet);
    }

    public Packet receive() throws IOException, ClassNotFoundException {
        return (Packet) this.in.readObject();
    }

    public Packet interpretPacket(Packet packet){
        try {
            //TODO implement missing
            switch (packet.getType()) {
                case INTERNAL_COMMAND:
                    return interpretInternalCommand(packet);
                case FILE:
                    return interpretFile(packet);
                case EXEC:
                    return interpretExec(packet);
                default:
                    return notImplemented(packet);
            }
        } catch(Exception e){
            return PacketFactory.exception(e);
        }
    }

    private Packet interpretExec(Packet packet) throws IOException {
        Process process = Runtime.getRuntime().exec((String) packet.getParams().get("command"));
        InputStream stdOutStream = process.getInputStream();
        InputStream stdErrStream = process.getErrorStream();

        ArrayList<String> stdOut = new ArrayList<String>(), stdErr = new ArrayList<String>();
        Scanner stdOutScan = new Scanner(stdOutStream), stdErrScan = new Scanner(stdErrStream);

        while(stdOutScan.hasNextLine())
            stdOut.add(stdOutScan.nextLine());

        while(stdErrScan.hasNextLine())
            stdErr.add(stdErrScan.nextLine());

        return PacketFactory.execResult(stdOut.toArray(new String[stdOut.size()]), stdErr.toArray(new String[stdErr.size()]));
    }

    private Packet interpretFile(Packet packet) throws IOException {
        File file = new File((String) packet.getParams().get("path"));
        FileOutputStream outFile = new FileOutputStream(file);
        outFile.write((byte[]) packet.getParams().get("bytes"));
        outFile.close();

        return PacketFactory.taskCompleted("File writed to: " + file.getAbsolutePath());
    }

    private Packet interpretInternalCommand(Packet packet) {
        //TODO implement
        switch ((InternalCommand) packet.getParams().get("command")) {
            case ECHO: return InternalCommands.echo(packet);
            case SLAVE_INFO: return InternalCommands.slaveInfo(packet, this.socket);
            default: return notImplemented(packet);
        }
    }

    private Packet notImplemented(Packet packet) {
        return PacketFactory.error(Error.NOT_IMPLEMENTED);
    }
}
