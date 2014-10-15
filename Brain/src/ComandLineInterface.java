import br.Brain;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Andre Pontes (42845)
 */
public class ComandLineInterface {

    Scanner scan;
    PrintStream printer;
    Brain brain;

    public ComandLineInterface(Brain br, InputStream in, PrintStream out){
        this.scan = new Scanner(in);
        this.printer = out;
        this.brain = br;
    }
}
