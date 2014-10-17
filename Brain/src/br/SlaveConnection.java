package br;

import prot.Packet;

import java.io.*;
import java.net.Socket;

/**
 * @author Andre Pontes (42845)
 */
public class SlaveConnection {
    Socket socket;
    ObjectOutput out;
    ObjectInput in;

    public SlaveConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    public void send(Packet packet) throws IOException {
        this.out.writeObject(packet);
    }

    public Packet receive() throws IOException, ClassNotFoundException {
        return (Packet) this.in.readObject();
    }
}
