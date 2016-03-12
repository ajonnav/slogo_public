package command;

import java.util.List;
import exception.SLogoException;
import model.IModelMap;


public class DoTimesCommand extends Command {

    private IModelMap modelMap;

    public DoTimesCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(2);
    }

    @Override
    public double execute () {
        double lastValue = 0;
        if (getCommands().get(0).size() != 2) {
            throw new SLogoException(getErrorBundle().getString("WrongNumberArguments"));
        }
        if (!getCommands().get(0).get(0).getCommandName().equals("VariableCommand")) {
            throw new SLogoException(getErrorBundle().getString("NoVariable"));
        }
        for (double i = 1; i <= getCommands().get(0).get(1).execute(); i++) {
            modelMap.getVariable()
                    .setVariable(((VariableCommand) getCommands().get(0).get(0)).getName(), i);
            lastValue = loopExecute(getCommands().get(1));
        }
        return lastValue;
    }

}
