package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class IfCommand implements ICommand {

    public static final int numChildren = 2;
    private ICommand bool;
    private List<ICommand> commands;

    public IfCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.bool = commands.get(0).get(0);
        this.commands = commands.get(1);
    }
    
    @Override
    public double execute () {
        return bool.execute() != 0 ? loopExecute(commands) : 0;
    }
}
