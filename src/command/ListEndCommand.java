package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class ListEndCommand extends Command {

    public ListEndCommand (Map<String, Observable> modelMap, List<String> text) {
    }

    @Override
    public double execute () {
        return 0;
    }

}
