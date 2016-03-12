package command;

import java.util.List;
import model.IModelMap;
import command.Command;


public class SetAnimationSpeedCommand extends Command {

    public SetAnimationSpeedCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay()
                .setAnimationSpeed(new double[] { getCommands().get(0).get(0).execute() });
    }

}
