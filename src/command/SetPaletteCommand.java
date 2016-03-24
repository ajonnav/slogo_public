package command;

import java.util.List;
import model.IModelMap;


public class SetPaletteCommand extends Command {


    public SetPaletteCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        setNumChildren(4);
    }

    @Override
    public double execute () {
        return getModelMap().getDisplay().addToColorMap(new double[] { getCommands().get(0).get(0).execute(),
                                                     getCommands().get(1).get(0).execute(),
                                                     getCommands().get(2).get(0).execute(),
                                                     getCommands().get(3).get(0).execute()});
    }

}
