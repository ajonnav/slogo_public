package command;

import java.util.List;
import model.IModelMap;


public class PenDownCommand extends Command {


    public PenDownCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("penDown", null);
    }

}
