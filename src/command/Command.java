package command;

import java.util.List;
import java.util.ResourceBundle;

import constants.ResourceConstants;
import model.IModelMap;


public abstract class Command {

    private int numChildren = 0;
    private IModelMap modelMap;
    private String expression;
    private List<String> text;
    private String name;
    private boolean takesUnlimitedParameters = false;
    private List<List<Command>> commands;
    private ResourceBundle errorBundle = ResourceBundle.getBundle(ResourceConstants.DEFAULT_RESOURCE + ResourceConstants.ERRORS);

    public Command(IModelMap modelMap, String expression, List<String> text) {
        this.setModelMap(modelMap);
        this.setExpression(expression);
        this.setText(text);
    }
    public abstract double execute ();

    public void prepare (List<List<Command>> commands) {
        this.commands = commands;
        this.name = this.getClass().getSimpleName();
    }
    
    public double loopExecute (List<Command> commands) {
        double lastValue = 0;
        for (int i = 0; i < commands.size(); i++) {
            lastValue = commands.get(i).execute();
        }
        return lastValue;
    }
    
    public double unlimitedExecute(Operator operation) {
        double current = getCommands().get(0).get(0).execute();
        for(int i = 1; i < getCommands().get(0).size(); i++) {
            current = operation.operate(current, getCommands().get(0).get(i).execute());
        }
        return current;
    }
    
    public int getNumChildren () {
        return numChildren;
    }

    public void setNumChildren (int numChildren) {
        this.numChildren = numChildren;
    }

    public List<List<Command>> getCommands () {
        return commands;
    }

    public String getCommandName () {
        return name;
    }
    
    public boolean takesUnlimitedParameters () {
        return takesUnlimitedParameters;
    }

    public void setTakesUnlimitedParameters (boolean takesUnlimitedParameters) {
        this.takesUnlimitedParameters = takesUnlimitedParameters;
    }
    
    public ResourceBundle getErrorBundle() {
    	return errorBundle;
    }
    
    public IModelMap getModelMap () {
        return modelMap;
    }
    
    public void setModelMap (IModelMap modelMap) {
        this.modelMap = modelMap;
    }
    
    public String getExpression () {
        return expression;
    }
    
    public void setExpression (String expression) {
        this.expression = expression;
    }
    
    public List<String> getText () {
        return text;
    }
    
    public void setText (List<String> text) {
        this.text = text;
    }

}
