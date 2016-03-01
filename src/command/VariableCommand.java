package command;

import java.util.Map;
import java.util.Observable;
import model.VariableModel;


public class VariableCommand implements ICommand {

    public static final int numChildren = 0;
    private Map<String, Observable> modelMap;
    private String name;

    public VariableCommand (Map<String, Observable> modelMap, String name) {
        this.modelMap = modelMap;
        this.name = name;
    }

    @Override
    public double execute () {
        System.out.println(((VariableModel) modelMap.get("variables")).getVariable(name));
        return ((VariableModel) modelMap.get("variables")).getVariable(name);
    }

    public String getName () {
        return name;
    }
}
