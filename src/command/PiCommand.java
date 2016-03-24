package command;

import java.util.List;
import model.IModelMap;


public class PiCommand extends Command {

    public PiCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
    }

    @Override
    public double execute () {
        return Math.PI;
    }
}
