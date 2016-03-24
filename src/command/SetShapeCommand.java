package command;

import java.util.Arrays;
import java.util.List;
import model.IModelMap;


public class SetShapeCommand extends Command {

    public SetShapeCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("setImageIndex", Arrays.asList(getCommands().get(0).get(0)));
    }

}
