package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class NotEqualCommand implements ICommand {

    public static int numChildren = 2;
    private ICommand valueOne;
    private ICommand valueTwo;

    public NotEqualCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.valueOne = commands.get(0).get(0);
        this.valueTwo = commands.get(1).get(0);
    }

    @Override
    public double execute () {
        return valueOne.execute() != valueTwo.execute() ? 1 : 0;
    }
}
