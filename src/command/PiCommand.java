package command;

import java.util.List;
import model.IModelMap;


public class PiCommand extends Command {

    public PiCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return Math.PI;
    }
}
