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
        double lastValue = 0;
        if (bool.execute() != 0) {
            for (int i = 0; i < ifcommands.size(); i++) {
                lastValue = ifcommands.get(i).execute();
            }
        }
        else {
            for (int i = 0; i < elsecommands.size(); i++) {
                lastValue = elsecommands.get(i).execute();
            }
        }
        return lastValue;
    }

    @Override
    public double evaluate () {
        double lastValue = 0;
        if (bool.evaluate() != 0) {
            for (int i = 0; i < ifcommands.size(); i++) {
                lastValue = ifcommands.get(i).evaluate();
            }
        }
        else {
            for (int i = 0; i < elsecommands.size(); i++) {
                lastValue = elsecommands.get(i).evaluate();
            }
        }
        return lastValue;
    }

}
