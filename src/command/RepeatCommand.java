package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.VariableModel;


public class RepeatCommand implements ICommand {

    public static final int numChildren = 2;
    private ICommand repeat;
    private Map<String, Observable> modelMap;
    private List<ICommand> commands;

    public RepeatCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.modelMap = modelMap;
        this.repeat = commands.get(0).get(0);
        this.commands = commands.get(1);
    }

    @Override
    public double execute () {
        double lastValue = 0;
        for (int i = 0; i < repeat.execute(); i++) {
            ((VariableModel) modelMap.get("variables")).setVariable(":repcount", i+1);
            lastValue = loopExecute(commands);
        }
        return lastValue;
    }

}
