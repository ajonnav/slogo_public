package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class XCoordinateCommand extends Command {

    private double x;

    public XCoordinateCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(0);
        x = ((TurtleModel) modelMap.get("turtle")).getPositionX();
    }

    @Override
    public double execute () {
        return x;
    }

}
