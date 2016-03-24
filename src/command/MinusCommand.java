package command;

import java.util.List;
import model.IModelMap;


public class MinusCommand extends Command {

    public MinusCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return -getCommands().get(0).get(0).execute();
    }
}
