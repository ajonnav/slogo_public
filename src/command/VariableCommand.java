package command;

import java.util.List;
import model.IModelMap;


public class VariableCommand extends Command {

    private String name;

    public VariableCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        this.name = text.get(0);
    }

    @Override
    public double execute () {
        return getModelMap().getVariable().getVariable(name);
    }

    public String getName () {
        return name;
    }
}
