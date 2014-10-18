package br;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Andre Pontes (42845)
 */
public class Brain{

    class Listener extends Thread{
        public static final int TIMEOUT = 10000;

        Brain brain;
        boolean stop;

        Listener(Brain brain){
            this.brain = brain;
            this.stop = false;
        }

        @Override
        public void run(){
            this.stop = false;
/*
            try {
                this.brain.getServerSocket().setSoTimeout(TIMEOUT);
            } catch (SocketException e) {
                e.printStackTrace();
            }
*/
            while(!stop){
                try {
                    Socket socket = this.brain.getServerSocket().accept();
                    if(socket != null)
                        this.brain.addSocket(socket);

                } catch (SocketTimeoutException e2){

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        void stopListening(){
            this.stop = true;
        }
    }


    ServerSocket serverSocket;
    ArrayList<SlaveConnection> slaves;
    Listener listener;
    PrintStream log;

    public Brain(int port, PrintStream log) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.slaves = new ArrayList<SlaveConnection>();
        this.listener = new Listener(this);
        this.log = log;
    }

    public void addSocket(Socket socket) throws IOException {
        this.slaves.add(new SlaveConnection(socket));
        this.log.println("[NEW CONNECTION] From: " + socket.getRemoteSocketAddress());
    }

    private ServerSocket getServerSocket(){
        return this.serverSocket;
    }

    public void startListening(){
        if(!this.listener.isAlive()){
            this.listener = new Listener(this);
            this.listener.start();
            this.log.println("Listener started at port: " + this.serverSocket.getLocalPort() );
        }
    }

    public void removeDisconnected() {
        for(SlaveConnection slave : this.slaves)
            if(!slave.getSocket().isConnected())
                this.slaves.remove(slave);
    }

    public void stopListening(){
        if(this.listener.isAlive())
            this.listener.stopListening();
    }

    public Iterator<SlaveConnection> getSlaves(){
        return this.slaves.iterator();
    }

}
