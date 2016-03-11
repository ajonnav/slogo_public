package command;

import java.util.List;
import model.IModelMap;


public class PiCommand extends Command {

    public PiCommand (IModelMap modelMap, List<String> text) {
    }

    @Override
    public double execute () {
        return Math.PI;
    }
}
