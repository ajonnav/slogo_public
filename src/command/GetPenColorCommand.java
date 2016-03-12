package command;

import java.util.List;
import model.IModelMap;


public class GetPenColorCommand extends Command {


    public GetPenColorCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(0);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("getPenColorIndex", null);
    }
}
