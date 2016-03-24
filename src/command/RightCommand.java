package command;

import java.util.Arrays;
import java.util.List;
import model.IModelMap;


public class RightCommand extends Command {


    public RightCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(1);
    }

    public double execute () {
        return getModelMap().getDisplay().TurtleAction("turnRight", Arrays.asList(getCommands().get(0).get(0)));
    }
}
