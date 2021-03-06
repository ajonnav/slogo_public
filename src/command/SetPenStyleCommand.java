package command;

import java.util.Arrays;
import java.util.List;

import model.IModelMap;

public class SetPenStyleCommand extends Command{

	
	public SetPenStyleCommand(IModelMap modelMap, int tokenNumber, List<String> text) {
	        super(modelMap, tokenNumber, text);
		setNumChildren(1);
	}

	@Override
	public double execute() {
		return getModelMap().getDisplay().TurtleAction("setPenStyleIndex", Arrays.asList(getCommands().get(0).get(0)));
	}

}
