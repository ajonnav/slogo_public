package command;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import model.VariableModel;

public class ForCommand implements ICommand{
    
    public static int numChildren = 2;
    private int numArgs = 4;
    private String variable;
    private List<List<ICommand>> loop;
    private List<ICommand> block;
    private List<ICommand> loopBlock;
    private Map<String, Observable> modelMap;
    
    public ForCommand(Map<String, Observable> modelMap, List<List<ICommand>> commands) {
            this.modelMap = modelMap;
            this.loop = new ArrayList<List<ICommand>>();
            for(int i = 0; i < numArgs; i++) {
                loop.add(new ArrayList<ICommand>());
            }
            block = commands.get(0);
            int loopIndex = -1;
            int numChildren = 0;
            for(int i = block.size() - 1; i >= 0; i--) {
                if(numChildren == 0) {
                    loopIndex += 1;
                }        
                if(loopIndex == numArgs) {
                    break;
                }
                int currChildren = block.get(i).getNumChildren();
                if(currChildren > 0) {
                    numChildren += currChildren;
                }
                else if(currChildren == 0 && numChildren != 0) {
                    numChildren -= 1;
                }
                loop.get(loopIndex).add(block.get(i));
            }
            evaluateVariable();   
            loopBlock = commands.get(1);
    }
    
    public double evaluateVariable() {
        VariableCommand command = ((VariableCommand) loop.get(3).get(0));
        this.variable = command.getName();
        return command.evaluate();
    }
    
    public double evaluateLoop(int ind) {
        double lastValue = 0;
        for(int i = loop.get(ind).size() - 1; i >= 0; i-- ) {
            lastValue = loop.get(ind).get(i).evaluate();
        }
        return lastValue;
    }
    
    public double executeLoop(int ind) {
        double lastValue = 0;
        for(int i = loop.get(ind).size() - 1; i >= 0; i-- ) {
            lastValue = loop.get(ind).get(i).execute();
        }
        return lastValue;
    }    
    
    @Override
    public double evaluate () {
        double lastValue = 0;
        double start = evaluateLoop(0);
        double end = evaluateLoop(1);
        double increment = evaluateLoop(2);
        for(double i = start; i < end; i += increment) {
            ((VariableModel) modelMap.get("variable")).setVariable(variable, i);
            for(int j = 0; j < loopBlock.size(); j++) {
                lastValue = loopBlock.get(j).evaluate();
            }
        }
        return lastValue;
    }

    @Override
    public double execute () {
        double lastValue = 0;
        double increment = executeLoop(0);
        double end = executeLoop(1);
        double start = executeLoop(2);
        for(double i = start; i < end; i += increment) {
            ((VariableModel) modelMap.get("variable")).setVariable(variable, i);
            for(int j = 0; j < loopBlock.size(); j++) {
                lastValue = loopBlock.get(j).execute();
            }
        }
        return lastValue;
    }
    
    @Override
    public int getNumChildren () {
        return numChildren;
    }
}
