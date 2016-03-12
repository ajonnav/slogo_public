package command;

import java.util.Arrays;
import java.util.List;
import model.IModelMap;


public class RightCommand extends Command {

    private IModelMap modelMap;

    public RightCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(1);
    }

    public double execute () {
        return modelMap.getDisplay().TurtleAction("turnRight", Arrays.asList(getCommands().get(0).get(0)));
    }
}
