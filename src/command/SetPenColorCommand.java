package command;

import java.util.Arrays;
import java.util.List;
import model.IModelMap;


public class SetPenColorCommand extends Command {

    private IModelMap modelMap;

    public SetPenColorCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("setPenColorIndex", Arrays.asList(getCommands().get(0).get(0)));
    }

}
