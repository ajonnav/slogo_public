package command;

import java.util.List;
import model.ModelMap;


public class YCoordinateCommand extends Command {

    private ModelMap modelMap;

    public YCoordinateCommand (ModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("getPositionY", null);
    }

}
