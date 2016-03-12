package command;

import java.util.List;
import model.IModelMap;
import parser.Operator;


public class SumCommand extends Command {

    public SumCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(2);
        setTakesUnlimitedParameters(true);
    }

    @Override
    public double execute () {
        if(getCommands().get(0).size() > 1) {
            unlimitedExecute(Operator.SUM);
        }
        return Operator.SUM.operate(getCommands().get(0).get(0).execute(), getCommands().get(1).get(0).execute());
    }

}
