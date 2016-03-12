package command;

import java.util.List;
import model.IModelMap;


public class PenUpCommand extends Command {


    public PenUpCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("penUp", null);
    }

}
