package command;

import java.util.List;
import model.IModelMap;


public class ListStartCommand extends Command {

    public ListStartCommand (IModelMap modelMap, List<String> text) {
    }

    @Override
    public double execute () {
        return 0;
    }

}
