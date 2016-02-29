package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.VariableModel;

public class MakeVariableCommand implements ICommand{
    
    public final static int numChildren = 2;
    private Map<String, Observable> modelMap;
    private String variable;
    private double value;
    
    public MakeVariableCommand(Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.modelMap = modelMap;
        this.variable = ((VariableCommand) commands.get(0).get(0)).getName();
        this.value = commands.get(1).get(0).execute();
    }
    
    @Override
    public double execute () {
        return evaluate();
    }

    @Override
    public double evaluate () {
        return ((VariableModel) modelMap.get("variables")).setVariable(variable, value);
    }

    @Override
    public int getNumChildren () {
        return numChildren;
    }

}
