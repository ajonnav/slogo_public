package command;

import java.util.List;
import model.IModelMap;


public class GetShapeCommand extends Command {


    public GetShapeCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(0);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("getImageIndex", null);
    }
}
