package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.ModelMap;


public class ListEndCommand extends Command {

    public ListEndCommand (ModelMap modelMap, List<String> text) {
    }

    @Override
    public double execute () {
        return 0;
    }

}
