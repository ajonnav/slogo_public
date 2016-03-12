package command;

import java.util.List;
import model.IModelMap;


public class GetPenColorCommand extends Command {

    private IModelMap modelMap;

    public GetPenColorCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(0);
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("getPenColorIndex", null);
    }
}
