package command;


import java.util.List;
import java.util.Map;
import java.util.Observable;

public class RepeatCommand implements ICommand{
    
    public static int numChildren = 2;
    
    private double repeat;
    private List<ICommand> commands;
    
    public RepeatCommand( Map<String, Observable> modelMap, List<List<ICommand>> commands) {
            this.repeat = commands.get(0).get(0).evaluate();
            this.commands = commands.get(1);
    }
    
    @Override
    public double execute () {
        double lastValue = 0;
        for(int i = 0; i < repeat; i++) {
            for(int j = 0; j < commands.size(); j++) {
                lastValue = commands.get(j).execute();
            }
        }
        return lastValue;
    }

    @Override
    
    public double evaluate () {
        double lastValue = 0;
        for(int i = 0; i < repeat; i++) {
            for(int j = 0; j < commands.size(); j++) {
                lastValue = commands.get(j).evaluate();
            }
        }
        return lastValue;
    }
    
    @Override
    public int getNumChildren () {
        return numChildren;
    }
    
}
