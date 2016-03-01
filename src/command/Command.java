package command;

import java.util.List;


public abstract class Command {

    private int numChildren;
    private String name;
    private List<List<Command>> commands;
    
    public abstract double execute ();
    
    public void prepare(List<List<Command>> commands) {
        this.commands = commands;
        this.name = this.getClass().getSimpleName();
    }
    
    public int getNumChildren() {
        return numChildren;
    }
    
    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }
    
    public List<List<Command>> getCommands() {
        return commands;
    }
    
    public String getName() {
        return name;
    }
    
    public double loopExecute (List<Command> commands) {
        double lastValue = 0;
        for (int i = 0; i < commands.size(); i++) {
            lastValue = commands.get(i).execute();
        }
        return lastValue;
    }
}
