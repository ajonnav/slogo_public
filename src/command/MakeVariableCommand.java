package command;

import java.util.List;
import exception.SLogoException;
import model.IModelMap;


public class MakeVariableCommand extends Command {


    public MakeVariableCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(2);
    }

    @Override
    public double execute () {
        if (!getCommands().get(0).get(0).getCommandName().equals("VariableCommand")) {
            throw new SLogoException("NoVariable");
        }
        String variable = ((VariableCommand) getCommands().get(0).get(0)).getName();
        return getModelMap().getVariable()
                .setVariable(variable, getCommands().get(1).get(0).execute());
    }
}
