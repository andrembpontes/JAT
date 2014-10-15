package sl;

import prot.Packet;

import java.io.*;
import java.net.Socket;

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
        //implement
        return null;
    }
}
