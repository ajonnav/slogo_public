package command;

import java.util.List;
import model.IModelMap;


public class YCoordinateCommand extends Command {

    private IModelMap modelMap;

    public YCoordinateCommand (IModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("getPositionY", null);
    }

}
