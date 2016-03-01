package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.VariableModel;


public class ForCommand implements ICommand {

    public static final int numChildren = 2;
    private String variable;
    private double start;
    private double end;
    private double increment;
    private List<ICommand> block;
    private List<ICommand> loopBlock;
    private Map<String, Observable> modelMap;

    public ForCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.modelMap = modelMap;
        block = commands.get(0);
        loopBlock = commands.get(1);
        this.variable = ((VariableCommand) block.get(0)).getName();
    }

    @Override
    public double execute () {
        double lastValue = 0;
        this.start = block.get(1).execute();
        this.end = block.get(2).execute();
        this.increment = block.get(3).execute();
        for (double i = start; i <= end; i += increment) {
            ((VariableModel) modelMap.get("variables")).setVariable(variable, i);
            lastValue = loopExecute(loopBlock);
        }
        return lastValue;
    }
}