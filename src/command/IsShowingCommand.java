package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class IsShowingCommand extends Command {

    private boolean status;

    public IsShowingCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(0);
        status = ((TurtleModel) modelMap.get("turtle")).getShowStatus();
    }

    @Override
    public double execute () {
        return status ? 1 : 0;
    }

}
