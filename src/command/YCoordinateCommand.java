package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class YCoordinateCommand extends Command {

    public static final int numChildren = 0;
    private double y;

    public YCoordinateCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(0);
        y = ((TurtleModel) modelMap.get("turtle")).getPositionY();
    }

    @Override
    public double execute () {
        return y;
    }

}
