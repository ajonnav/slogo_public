package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

public class SetTowardsCommand implements ICommand {
    
    public static final int numChildren = 2;
    private double valueOne;
    private double valueTwo;
    
    public SetTowardsCommand(Map<String, Observable> modelMap, List<List<ICommand>> commands) {
            this.valueOne = commands.get(0).get(0).evaluate();
            this.valueTwo = commands.get(1).get(0).evaluate();
            
    }

    @Override
    public double execute() {
           return evaluate();
    }

    @Override
    public double evaluate () {
        return valueOne + valueTwo;
    }
    
    @Override
    public int getNumChildren () {
        return numChildren;
    }
    
}
