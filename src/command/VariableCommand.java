package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.VariableModel;


public class VariableCommand extends Command {

    private Map<String, Observable> modelMap;
    private String name;

    public VariableCommand (Map<String, Observable> modelMap, List<String> text) {
        this.modelMap = modelMap;
        this.name = text.get(0);
    }

    @Override
    public double execute () {
        return ((VariableModel) modelMap.get("variables")).getVariable(name);
    }

    public String getName () {
        return name;
    }
}
