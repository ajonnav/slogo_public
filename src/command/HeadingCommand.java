package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.ModelMap;
import model.TurtleModel;


public class HeadingCommand extends Command {

    private double heading;

    public HeadingCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        heading = modelMap.getTurtle().getHeading();
    }

    @Override
    public double execute () {
        return heading;
    }
}
