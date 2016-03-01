package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class ListStartCommand extends Command {

    public ListStartCommand (Map<String, Observable> modelMap, List<String> text) {
    }

    @Override
    public double execute () {
        return 0;
    }

}
