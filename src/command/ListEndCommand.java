package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class ListEndCommand implements ICommand {

    public ListEndCommand (Map<String, Observable> modelMap, List<ICommand> commands) {
    }

    @Override
    public double execute () {
        return 0;
    }

}
