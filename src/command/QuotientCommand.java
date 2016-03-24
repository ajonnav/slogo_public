package command;

import java.util.List;
import model.IModelMap;


public class QuotientCommand extends Command {

    public QuotientCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(2);
        setTakesUnlimitedParameters(true);
    }

    @Override
    public double execute () {
        if(getCommands().get(0).size() > 1) {
            return unlimitedExecute(Operator.QUOTIENT);
        }
        return Operator.QUOTIENT.operate(getCommands().get(0).get(0).execute(), getCommands().get(1).get(0).execute());
    }

}
