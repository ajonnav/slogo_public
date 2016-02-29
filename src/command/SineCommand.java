package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class SineCommand implements ICommand {

    public static int numChildren = 1;
    private ICommand valueOne;

    public SineCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.valueOne = commands.get(0).get(0);
    }

    @Override
    public double execute () {
        return evaluate();
    }

    @Override
    public double evaluate () {
        return Math.sin(Math.toRadians(valueOne.evaluate()));
    }
}
