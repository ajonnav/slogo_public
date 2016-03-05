package command;

import java.util.List;
import model.ModelMap;


public class PiCommand extends Command {

    public PiCommand (ModelMap modelMap, List<String> text) {
    }

    @Override
    public double execute () {
        return Math.PI;
    }
}
