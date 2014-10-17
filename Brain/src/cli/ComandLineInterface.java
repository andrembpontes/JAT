package cli;

import br.Brain;
import br.SlaveConnection;
import jdk.internal.util.xml.impl.Input;
import prot.Packet;
import prot.PacketFactory;
import prot.PacketFactoryInternal;
import prot.SlaveInfo;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static cli.Commands.*;

/**
 * @author Andre Pontes (42845)
 */
public class ComandLineInterface {

    Scanner scan;
    PrintStream printer;
    Brain brain;

    SlaveConnection selectedSlave;

    public ComandLineInterface(Brain br, InputStream in, PrintStream out){
        this.scan = new Scanner(in);
        this.printer = out;
        this.brain = br;
        startInteraction();
    }

    public void startInteraction(){
        while(true) {
            try {
                switch (valueOf(this.scan.next())) {
                    case LIST:
                        execList();
                        break;
                    case VIEW:
                        execView();
                        break;
                    case SELECT:
                        execSelect();
                        break;
                    case GET:
                        execGet();
                        break;
                    case SEND:
                        execSend();
                        break;
                    case HELP:
                        execHelp();
                        break;
                    case EXEC:
                        execExec();
                        break;
                }
            }
            catch (IOException ioE){
                ioE.printStackTrace();
            }
            catch (ClassNotFoundException cnfE){
                cnfE.printStackTrace();
            }

            this.scan.nextLine();
        }
    }

    private void execExec() throws IOException, ClassNotFoundException {
        this.getSelectedSlave().send(PacketFactory.exec(scan.nextLine()));
        this.printPacket(this.getSelectedSlave().receive());
    }

    private void execHelp() {
        this.printEnums("Avaliable commands:", Commands.values());
    }

    private void printEnums(String title, Enum[] enums) {
        this.printer.println(title);
        for(Enum e : enums)
            this.printer.println(e.toString());
    }

    private void printSlaves(){
        int i = 0;
        SlaveConnection slave;
        Iterator<SlaveConnection> slaves = this.brain.getSlaves();

        while(slaves.hasNext()) {
            slave = slaves.next();
            this.printer.println(i++ + "] " + slave.getSocket().getRemoteSocketAddress());
        }
    }

    private void printPacket(Packet packet){
        Iterator<String> paramsNames = packet.getParams().keySet().iterator();
        Iterator<Object> paramsValues = packet.getParams().values().iterator();

        this.printer.println("Printing packet");
        this.printer.println(packet.getType());

        while(paramsNames.hasNext()){
            this.printer.println(paramsNames.next() + " = ");

            Object objVal = paramsValues.next();
            if(objVal instanceof String[]){
                this.printer.println("[multi-lines-start]");
                for(String line : (String[]) objVal)
                    this.printer.println(line);
                this.printer.println("[multi-lines-end]");
            }
            else{
                this.printer.println(objVal + ";");
            }
        }

        this.printer.println("--- (PACKET_END) ---");
    }

    private void execSend() throws IOException, ClassNotFoundException {
        File file = new File(this.scan.next());
        InputStream fileIn = new FileInputStream(file);

        byte[] bytes = new byte[(int) file.getTotalSpace()];
        fileIn.read(bytes);

        Packet packet = PacketFactory.file(scan.next(), bytes);

        this.getSelectedSlave().send(packet);

        this.printPacket(this.getSelectedSlave().receive());
    }

    private void execGet() throws IOException, ClassNotFoundException {
        this.getSelectedSlave().send(PacketFactoryInternal.slaveInfo(null));
        this.printPacket(this.getSelectedSlave().receive());
    }

    private void execSelect() {
        int i = 0;
        Iterator<SlaveConnection> iterator = this.brain.getSlaves();

        while(i++ < scan.nextInt())
            iterator.next();

        this.selectedSlave = iterator.next();
    }

    private void execView() {

    }

    public void execList(){
        this.printSlaves();
    }

    public SlaveConnection getSelectedSlave(){
        if(selectedSlave == null)
            throw new NoSelectedSlaveException();

        return this.selectedSlave;
    }
}
