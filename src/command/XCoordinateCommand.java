package command;

import java.util.List;
import model.ModelMap;


public class XCoordinateCommand extends Command {

    private ModelMap modelMap;
    public static final String command = "getPositionX";

    public XCoordinateCommand (ModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction(command, null);
    }

}
