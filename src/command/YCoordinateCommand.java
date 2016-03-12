package command;

import java.util.List;
import model.IModelMap;


public class YCoordinateCommand extends Command {


    public YCoordinateCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction("getPositionY", null);
    }

}
