package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;


public class RandomCommand implements ICommand {

    public static int numChildren = 1;
    private ICommand max;

    public RandomCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.max = commands.get(0).get(0);
    }

    @Override
    public double execute () {
        return evaluate();
    }

    @Override
    public double evaluate () {
        Random r = new Random();
        return max.evaluate() * r.nextDouble();
    }
}
