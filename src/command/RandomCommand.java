package command;

import java.util.List;
import java.util.Random;
import model.IModelMap;


public class RandomCommand extends Command {

    public RandomCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(1);
    }

    @Override
    public double execute () {
        double random = (new Random()).nextDouble();
        return getCommands().get(0).get(0).execute() * random;
    }
}
