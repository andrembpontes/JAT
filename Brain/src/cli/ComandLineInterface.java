package cli;

import br.Brain;
import br.SlaveConnection;
import prot.Packet;
import prot.PacketFactory;
import prot.PacketFactoryInternal;
import prot.SlaveInfo;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

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
        //startInteraction();
    }

    public void startInteraction(){
        while(true) {
            boolean discardInput = true;

            try {
                switch (valueOf(this.scan.next().toUpperCase())) {
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
                    case TELNET:
                        execTelnet();
                        break;
                    case EXEC:
                        execExec();
                        discardInput = false;
                        break;
                }
            }
            catch (IOException ioE){
                ioE.printStackTrace();
            }
            catch (ClassNotFoundException cnfE){
                cnfE.printStackTrace();
            }
            catch (IllegalArgumentException iaE){
                this.printer.println("Invalid command. Use HELP to see available commands...");
            }
            catch (NoSelectedSlaveException nssE){
                this.printer.println("No slave selected :s");
            }

            if(discardInput)
                this.scan.nextLine();

            this.printer.println();
        }
    }

    private void execTelnet() {
        this.printer.println("Not implemented");
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
        this.printer.println("--- (PACKET_START) ---");
        this.printer.println("type = " + packet.getType());
        this.printer.println("packet params:");

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
        String localPath = this.scan.next(),
                destinationPath = scan.next();

        this.printer.print("Opening file... ");
        File file = new File(localPath);
        InputStream fileIn = new FileInputStream(file);
        this.printer.println("[DONE!]");

        this.printer.print("Reading file... ");
        byte[] bytes = new byte[(int) file.length()];
        fileIn.read(bytes);
        this.printer.println("[DONE!]");

        Packet packet = PacketFactory.file(destinationPath, bytes);

        this.printer.print("Sending file... ");
        this.getSelectedSlave().send(packet);
        this.printer.println("[DONE!]");

        this.printPacket(this.getSelectedSlave().receive());
    }

    private void execGet() throws IOException, ClassNotFoundException {
        this.printer.print("Requesting slave info... ");
        this.getSelectedSlave().send(PacketFactoryInternal.slaveInfo(null));
        this.printer.println("[DONE!]");

        this.printPacket(this.getSelectedSlave().receive());
    }

    private void execSelect() {
        int toSelect = scan.nextInt();
        int i = -1;
        Iterator<SlaveConnection> iterator = this.brain.getSlaves();

        while (++i < toSelect && iterator.hasNext())
            iterator.next();

        if (i == toSelect && iterator.hasNext()) {
            this.selectedSlave = iterator.next();
            this.printer.println("Slave " + this.selectedSlave.getSocket().getRemoteSocketAddress() + " selected");
        }
        else{
            this.printer.println("Invalid slave index!");
        }
    }

    private void execView() {

    }

    public void execList(){
        this.printer.println("Available slaves:");
        this.printSlaves();
    }

    public SlaveConnection getSelectedSlave(){
        if(selectedSlave == null)
            throw new NoSelectedSlaveException();

        return this.selectedSlave;
    }
}
