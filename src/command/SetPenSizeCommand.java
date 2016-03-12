package command;

import java.util.Arrays;
import java.util.List;
import model.IModelMap;


public class SetPenSizeCommand extends Command {

    private IModelMap modelMap;

    public SetPenSizeCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("setLineWidth", Arrays.asList(getCommands().get(0).get(0)));
    }

}
