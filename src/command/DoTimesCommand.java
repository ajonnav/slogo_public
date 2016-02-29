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
        block = commands.get(0);
        loopBlock = commands.get(1);
        this.variable = ((VariableCommand) block.get(0)).getName();
        this.start = 1;
        this.end = block.get(1).evaluate();
        this.increment = 1;
    }

    @Override
    public double evaluate () {
        double lastValue = 0;
        for (double i = start; i < end; i += increment) {
            ((VariableModel) modelMap.get("variables")).setVariable(variable, i);
            for (int j = 0; j < loopBlock.size(); j++) {
                lastValue = loopBlock.get(j).evaluate();
            }
        }
        return lastValue;
    }

    @Override
    public double execute () {
        double lastValue = 0;
        for (double i = start; i < end; i += increment) {
            ((VariableModel) modelMap.get("variables")).setVariable(variable, i);
            for (int j = 0; j < loopBlock.size(); j++) {
                lastValue = loopBlock.get(j).execute();
            }
        }
        return lastValue;
    }

}
