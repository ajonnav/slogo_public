package command;

import java.util.Arrays;
import java.util.List;
import model.IModelMap;


public class ForwardCommand extends Command {

    private IModelMap modelMap;

    public ForwardCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("forward", Arrays.asList(getCommands().get(0).get(0)));
    }
}
