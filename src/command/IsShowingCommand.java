package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.TurtleModel;


public class IsShowingCommand implements ICommand {

    public static final int numChildren = 0;
    private boolean status;

    public IsShowingCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        status = ((TurtleModel) modelMap.get("turtle")).getShowStatus();
    }

    @Override
    public double execute () {
        return status ? 1 : 0;
    }

}
