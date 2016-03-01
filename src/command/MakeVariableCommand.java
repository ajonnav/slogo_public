package command;

import java.util.List;
import model.ModelMap;


public class MakeVariableCommand extends Command {

    private ModelMap modelMap;

    public MakeVariableCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        String variable = ((VariableCommand) getCommands().get(0).get(0)).getName();
        return modelMap.getVariable()
                .setVariable(variable, getCommands().get(1).get(0).execute());
    }
}
