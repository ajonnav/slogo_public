package command;

import java.util.List;
import java.util.Random;
import model.IModelMap;


public class RandomCommand extends Command {

    public RandomCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(1);
    }

    @Override
    public double execute () {
        double random = (new Random()).nextDouble();
        return getCommands().get(0).get(0).execute() * random;
    }
}
