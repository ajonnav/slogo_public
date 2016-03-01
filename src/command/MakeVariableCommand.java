package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.VariableModel;


public class MakeVariableCommand extends Command {

    private Map<String, Observable> modelMap;

    public MakeVariableCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        String variable = ((VariableCommand) getCommands().get(0).get(0)).getName();
        return ((VariableModel) modelMap.get("variables"))
                .setVariable(variable, getCommands().get(1).get(0).execute());
    }
}
