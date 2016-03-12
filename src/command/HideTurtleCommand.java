package command;

import java.util.List;
import model.IModelMap;


public class HideTurtleCommand extends Command {


    public HideTurtleCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(0);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("hide", null);
    }

}
