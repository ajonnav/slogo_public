package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class IfElseCommand implements ICommand {

    public static final int numChildren = 3;
    private ICommand bool;
    private List<ICommand> ifcommands;
    private List<ICommand> elsecommands;

    public IfElseCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.bool = commands.get(0).get(0);
        this.ifcommands = commands.get(1);
        this.elsecommands = commands.get(2);
    }
    
    @Override
    public double execute () {
        return bool.execute() != 0.0 ? loopExecute(ifcommands) : loopExecute(elsecommands);
    }
}
