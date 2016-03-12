package command;

import java.util.List;
import model.IModelMap;
import parser.Operator;


public class DifferenceCommand extends Command {

    public DifferenceCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(2);
        setTakesUnlimitedParameters(true);
    }

    @Override
    public double execute () {
        if(getCommands().get(0).size() > 1) {
            unlimitedExecute(Operator.DIFFERENCE);
        }
        return Operator.DIFFERENCE.operate(getCommands().get(0).get(0).execute(),getCommands().get(1).get(0).execute());
    }
}
