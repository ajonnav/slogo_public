package command;

import java.util.List;
import model.ModelMap;


public class YCoordinateCommand extends Command {

    public static final int numChildren = 0;
    private double y;

    public YCoordinateCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        y = modelMap.getTurtle().getPositionY();
    }

    @Override
    public double execute () {
        return y;
    }

}
