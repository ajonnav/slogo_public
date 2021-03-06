package command;

import java.util.List;
import model.IModelMap;


public class XCoordinateCommand extends Command {

    public static final String command = "getPositionX";

    public XCoordinateCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().TurtleAction(command, null);
    }

}
