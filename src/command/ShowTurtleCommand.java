package command;

import java.util.List;
import model.IModelMap;


public class ShowTurtleCommand extends Command {

    private IModelMap modelMap;

    public ShowTurtleCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("show", null);
    }

}
