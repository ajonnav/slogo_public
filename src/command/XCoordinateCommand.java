package command;

import java.util.List;
import model.IModelMap;


public class XCoordinateCommand extends Command {

    private IModelMap modelMap;
    public static final String command = "getPositionX";

    public XCoordinateCommand (IModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().TurtleAction(command, null);
    }

}
