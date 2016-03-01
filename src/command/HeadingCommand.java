package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class HeadingCommand implements ICommand {

    public static final int numChildren = 0;
    private double heading;

    public HeadingCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        heading = ((TurtleModel) modelMap.get("turtle")).getHeading();
    }

    @Override
    public double execute () {
        return heading;
    }
}
