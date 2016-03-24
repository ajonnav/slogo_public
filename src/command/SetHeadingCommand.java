package command;

import java.util.Arrays;
import java.util.List;
import model.IModelMap;


public class SetHeadingCommand extends Command {


    public SetHeadingCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("setHeading", Arrays.asList(getCommands().get(0).get(0)));
    }

}
