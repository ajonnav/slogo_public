package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.ModelMap;


public class ListStartCommand extends Command {

    public ListStartCommand (ModelMap modelMap, List<String> text) {
    }

    @Override
    public double execute () {
        return 0;
    }

}
