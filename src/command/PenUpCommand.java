package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class PenUpCommand extends Command {

    private Map<String, Observable> modelMap;

    public PenUpCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(0);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        ((TurtleModel) modelMap.get("turtle")).penUp();
        return 0;
    }

}
