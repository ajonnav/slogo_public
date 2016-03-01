package command;

import java.util.List;
import model.ModelMap;


public class IsShowingCommand extends Command {

    private boolean status;

    public IsShowingCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(0);
        status = modelMap.getTurtle().getShowStatus();
    }

    @Override
    public double execute () {
        return status ? 1 : 0;
    }

}
