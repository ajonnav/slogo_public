package command;

import java.util.Arrays;
import java.util.List;
import model.IModelMap;


public class LeftCommand extends Command {

    private IModelMap modelMap;

    public LeftCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(1);
        setTakesUnlimitedParameters(false);
    }

    @Override
    public double execute () {
        return -modelMap.getDisplay().TurtleAction("turnLeft", Arrays.asList(getCommands().get(0).get(0)));
    }

}
