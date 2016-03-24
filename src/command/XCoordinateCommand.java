package command;

import java.util.List;
import model.IModelMap;


public class XCoordinateCommand extends Command {

    public static final String command = "getPositionX";

    public XCoordinateCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction(command, null);
    }

}
