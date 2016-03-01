package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class XCoordinateCommand implements ICommand {

    public static final int numChildren = 0;
    private double x;

    public XCoordinateCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        x = ((TurtleModel) modelMap.get("turtle")).getPositionX();
    }

    @Override
    public double execute () {
        return x;
    }

}
