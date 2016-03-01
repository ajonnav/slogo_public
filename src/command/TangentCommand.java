package command;

import java.util.List;
import model.ModelMap;


public class TangentCommand extends Command {

    public TangentCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return Math.tan(Math.toRadians(getCommands().get(0).get(0).execute()));
    }
}
