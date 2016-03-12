package command;

import java.util.List;
import model.IModelMap;


public class YCoordinateCommand extends Command {

    private IModelMap modelMap;

    public YCoordinateCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("getPositionY", null);
    }

}
