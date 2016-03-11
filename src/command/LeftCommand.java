package command;

import java.util.Arrays;
import java.util.List;
import model.IModelMap;


public class LeftCommand extends Command {

    private IModelMap modelMap;

    public LeftCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(1);
        setTakesUnlimitedParameters(false);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return -modelMap.getDisplay().TurtleAction("turnLeft", Arrays.asList(getCommands().get(0).get(0)));
    }

}
