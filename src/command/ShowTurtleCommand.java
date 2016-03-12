package command;

import java.util.List;
import model.IModelMap;


public class ShowTurtleCommand extends Command {


    public ShowTurtleCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("show", null);
    }

}
