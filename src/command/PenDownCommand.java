package command;

import java.util.List;
import model.IModelMap;


public class PenDownCommand extends Command {

    private IModelMap modelMap;

    public PenDownCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("penDown", null);
    }

}
