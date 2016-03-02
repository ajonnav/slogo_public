package command;

import java.util.List;
import model.ModelMap;

public class AndCommand extends Command {

    public AndCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
    }

    @Override
    public double execute () {
        return getCommands().get(0).get(0).execute() != 0 && getCommands().get(1).get(0).execute() != 0 ? 1 : 0;
    }

}