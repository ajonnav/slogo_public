package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.VariableModel;


public class DoTimesCommand implements ICommand {

    public static final int numChildren = 2;
    private String variable;
    private double start;
    private double end;
    private double increment;
    private List<ICommand> block;
    private List<ICommand> loopBlock;
    private Map<String, Observable> modelMap;

    public DoTimesCommand (Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.modelMap = modelMap;
        this.block = commands.get(0);
        this.loopBlock = commands.get(1);
        this.variable = ((VariableCommand) block.get(0)).getName();
        this.start = 1;
        this.increment = 1;
    }

    @Override
    public double execute () {
        double lastValue = 0;
        this.end = block.get(1).execute();
        for (double i = start; i < end; i += increment) {
            ((VariableModel) modelMap.get("variables")).setVariable(variable, i);
            lastValue = loopExecute(loopBlock);
        }
        return lastValue;
    }

}
