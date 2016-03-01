package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;


public class RandomCommand extends Command {

    public RandomCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return getCommands().get(0).get(0).execute() * (new Random()).nextDouble();
    }
}
