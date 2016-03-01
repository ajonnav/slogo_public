package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class SumCommand implements ICommand {

    public static int numChildren = 2;
    private ICommand valueOne;
    private ICommand valueTwo;

    public SumCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.valueOne = commands.get(0).get(0);
        this.valueTwo = commands.get(1).get(0);
    }

    @Override
    public double execute () {
        return valueOne.execute() + valueTwo.execute();
    }

}
