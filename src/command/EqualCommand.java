package command;

import java.util.List;
import model.IModelMap;
import parser.Operator;


public class EqualCommand extends Command {

    public EqualCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(2);
    }

    @Override
    public double execute () {
        return Operator.EQUAL.operate(getCommands().get(0).get(0).execute(),getCommands().get(1).get(0).execute());
    }
}
