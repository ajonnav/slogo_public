package command;

import java.util.List;
import model.IModelMap;


public class HeadingCommand extends Command {


    public HeadingCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(0);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("getHeading", null);
    }
}
