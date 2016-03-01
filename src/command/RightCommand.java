package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class RightCommand extends Command {

    private Map<String, Observable> modelMap;
    
    public RightCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(1);
        this.modelMap = modelMap;
    }

    public double execute () {
        double deg = getCommands().get(0).get(0).execute();
        ((TurtleModel) modelMap.get("turtle")).turn(deg);
        return deg;
    }
}
