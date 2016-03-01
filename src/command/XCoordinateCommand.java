package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.ModelMap;
import model.TurtleModel;


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
