package command;

import java.util.Map;
import java.util.Observable;
import model.VariableModel;

public class VariableCommand implements ICommand{
    
    public final static int numChildren = 0;
    private Map<String, Observable> modelMap;
    private String name;
    
    public VariableCommand(Map<String, Observable> modelMap, String name) {
        this.modelMap = modelMap;
        this.name = name;
    }
    
    @Override
    public double execute () {
        return evaluate();
    }

    @Override
    public double evaluate () {
        return ((VariableModel) modelMap.get("variable")).getVariable(name);
    }
    
    public String getName () {
        return name;
    }

    @Override
    public int getNumChildren () {
        return numChildren;
    }   
}
