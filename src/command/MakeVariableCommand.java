package command;

import java.util.List;
import exception.SLogoException;
import model.IModelMap;


public class MakeVariableCommand extends Command {

    private IModelMap modelMap;

    public MakeVariableCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(2);
    }

    @Override
    public double execute () {
        if (!getCommands().get(0).get(0).getCommandName().equals("VariableCommand")) {
            throw new SLogoException("NoVariable");
        }
        String variable = ((VariableCommand) getCommands().get(0).get(0)).getName();
        return modelMap.getVariable()
                .setVariable(variable, getCommands().get(1).get(0).execute());
    }
}
