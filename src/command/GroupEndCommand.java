package command;

import java.util.List;

import model.IModelMap;


public class GroupEndCommand extends Command {

    public GroupEndCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
    	super(modelMap, tokenNumber, text);
    }

    @Override
    public double execute () {
        return 0;
    }

}
