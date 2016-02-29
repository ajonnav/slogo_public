package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class GreaterThanCommand implements ICommand {

    public static int numChildren = 2;
    private ICommand valueOne;
    private ICommand valueTwo;

    public GreaterThanCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.valueOne = commands.get(0).get(0);
        this.valueTwo = commands.get(1).get(0);
    }

    @Override
    public double execute () {
        return evaluate();
    }

    @Override
    public double evaluate () {
        if(valueOne.evaluate() > valueTwo.evaluate()) {
            return 1;
        }
        return 0;
    }
}
