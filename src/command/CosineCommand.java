package command;

import java.util.List;
import model.IModelMap;


public class CosineCommand extends Command {

    public CosineCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
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
