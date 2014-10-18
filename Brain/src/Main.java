import br.Brain;
import cli.ComandLineInterface;

import java.io.IOException;

/**
 * @author Andre Pontes (42845)
 */
public class Main {

    public static final int PORT = 6666;

    public static void main(String[] args){
        Brain brain = null;
        try {
            brain = new Brain(PORT, System.out);
            ComandLineInterface ui = new ComandLineInterface(brain, System.in, System.out);

            brain.startListening();
            ui.startInteraction();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
