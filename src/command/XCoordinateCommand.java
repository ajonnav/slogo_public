package command;

import java.util.List;
import model.ModelMap;


public class XCoordinateCommand extends Command {

    private double x;

    public XCoordinateCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        x = modelMap.getTurtle().getPositionX();
    }

    @Override
    public double execute () {
        return x;
    }

}
