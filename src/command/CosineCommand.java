package command;

import java.util.List;
import model.IModelMap;


public class CosineCommand extends Command {

    public CosineCommand (IModelMap modelMap, List<String> text) {
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
