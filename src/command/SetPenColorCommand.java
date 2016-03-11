package command;

import java.util.Arrays;
import java.util.List;
import model.IModelMap;


public class SetPenColorCommand extends Command {

    private IModelMap modelMap;

    public SetPenColorCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(1);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("setPenColorIndex", Arrays.asList(getCommands().get(0).get(0)));
    }

}
