package command;

import java.util.List;

import exception.SLogoSyntaxException;
import model.ModelMap;



public class DoTimesCommand extends Command {

    private ModelMap modelMap;

    public DoTimesCommand (ModelMap modelMap, List<String> text) {
        setNumChildren(2);
        this.modelMap = modelMap;
    }

    @Override
    public double execute () {
        double lastValue = 0;
        if(getCommands().get(0).size()!=2) {
        	throw new SLogoSyntaxException("Wrong number of arguments");
        }
        if(!getCommands().get(0).get(0).getCommandName().equals("VariableCommand")) {
        	throw new SLogoSyntaxException("No Variable");
        }
        
        for (double i = 1; i <= getCommands().get(0).get(1).execute(); i++) {
            modelMap.getVariable()
            .setVariable(((VariableCommand) getCommands().get(0).get(0)).getName(), i);
            lastValue = loopExecute(getCommands().get(1));
        }
        return lastValue;
    }

}
