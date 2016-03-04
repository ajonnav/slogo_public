package command;

import java.util.List;
import model.ModelMap;


public class XCoordinateCommand extends Command {

    private ModelMap modelMap;

    public XCoordinateCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction("getPositionX", new double[]{-1});
    }

}
