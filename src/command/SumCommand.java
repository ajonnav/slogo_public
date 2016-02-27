package command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class SumCommand implements ICommand {
    
    public static int numChildren = 2;
    
    private Map<String, Observable> modelMap = new HashMap<String, Observable>();  
    private double valueOne;
    private double valueTwo;
    
    public SumCommand(Map<String, Observable> modelMap, List<ICommand> commands) {
            this.modelMap = modelMap;
            this.valueOne = commands.get(0).evaluate();
            this.valueTwo = commands.get(1).evaluate();
    }

    @Override
    public double execute() {
           System.out.println(valueOne + valueTwo);
           return evaluate();
    }

    @Override
    public double evaluate () {
        return valueOne + valueTwo;
    }
}
