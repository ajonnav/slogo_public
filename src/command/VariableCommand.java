package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.ModelMap;
import model.VariableModel;


public class VariableCommand extends Command {

    private ModelMap modelMap;
    private String name;

    public VariableCommand (ModelMap modelMap, List<String> text) {
        this.modelMap = modelMap;
        this.name = text.get(0);
    }

    @Override
    public double execute () {
        return modelMap.getVariable().getVariable(name);
    }

    public String getName () {
        return name;
    }
}
