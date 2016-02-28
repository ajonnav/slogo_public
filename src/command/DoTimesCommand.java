package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.VariableModel;

public class DoTimesCommand implements ICommand{
    
    public static int numChildren = 2;
    private String variable;
    private double start = 1;
    private double end;
    private List<ICommand> block;
    private List<ICommand> loopBlock;
    private Map<String, Observable> modelMap;
    
    public DoTimesCommand(Map<String, Observable> modelMap, List<List<ICommand>> commands) {
        this.modelMap = modelMap;
        block = commands.get(0);
        variable = ((VariableCommand)block.get(0)).getName();
        loopBlock = commands.get(1);      
    }
    
    @Override
    public double execute () {
        double lastValue = 0;
        for(int i = 1; i < block.size(); i++) {
            end = block.get(i).execute();
        }
        for(double i = start; i < end; i++) {
            ((VariableModel) modelMap.get("variable")).setVariable(variable, i);
            for(int j = 0; j < loopBlock.size(); j++) {
                lastValue = loopBlock.get(j).execute();
            }
        }
        return lastValue;
    }

    @Override
    public double evaluate () {
        double lastValue = 0;
        for(int i = 1; i < block.size(); i++) {
            end = block.get(i).evaluate();
        }
        for(double i = start; i < end; i++) {
            ((VariableModel) modelMap.get("variable")).setVariable(variable, i);
            for(int j = 0; j < loopBlock.size(); j++) {
                lastValue = loopBlock.get(j).evaluate();
            }
        }
        return lastValue;
    }

    @Override
    public int getNumChildren () {
        return numChildren;
    }

}
