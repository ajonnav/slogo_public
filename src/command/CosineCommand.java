package command;

import java.util.List;
import model.ModelMap;


public class CosineCommand extends Command {

    public CosineCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(1);
    }

    public void prepare () {
        return;
    }

    @Override
    public double execute () {
        return Math.cos(Math.toRadians(getCommands().get(0).get(0).execute()));
    }
}
