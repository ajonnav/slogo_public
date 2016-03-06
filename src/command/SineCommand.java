package command;

import java.util.List;
import model.ModelMap;


public class SineCommand extends Command {

    public SineCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(1);
        setTakesUnlimitedParameters(false);
    }

    @Override
    public double execute () {
        return Math.sin(Math.toRadians(getCommands().get(0).get(0).execute()));
    }

}
