package command;

import java.util.List;
import exception.SLogoSyntaxException;
import model.IModelMap;


public class MakeVariableCommand extends Command {

    private IModelMap modelMap;

    public MakeVariableCommand (IModelMap modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        if (!getCommands().get(0).get(0).getCommandName().equals("VariableCommand")) {
            throw new SLogoSyntaxException("No Variable Specified");
        }
        String variable = ((VariableCommand) getCommands().get(0).get(0)).getName();
        return modelMap.getVariable()
                .setVariable(variable, getCommands().get(1).get(0).execute());
    }
}
