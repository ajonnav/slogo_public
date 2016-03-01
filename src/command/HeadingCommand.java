package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class HeadingCommand extends Command {

    private double heading;

    public HeadingCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(0);
        heading = ((TurtleModel) modelMap.get("turtle")).getHeading();
    }

    @Override
    public double execute () {
        return heading;
    }
}
