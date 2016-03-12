package command;

import java.util.List;
import model.IModelMap;
import parser.Operator;


public class PowerCommand extends Command {

    public PowerCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(2);
    }

    @Override
    public double execute () {
        if(getCommands().get(0).size() > 1) {
            return unlimitedExecute(Operator.POWER);
        }
        return Operator.POWER.operate(getCommands().get(0).get(0).execute(), getCommands().get(1).get(0).execute());
    }

}
