package command;

import java.util.List;
import model.IModelMap;


public class SetPaletteCommand extends Command {

    private IModelMap modelMap;

    public SetPaletteCommand (IModelMap modelMap, int tokenNumber, List<String> text) {
        super(modelMap, tokenNumber, text);
        setNumChildren(4);
    }

    @Override
    public double execute () {
        return modelMap.getDisplay().addToColorMap(new double[] { getCommands().get(0).get(0).execute(),
                                                     getCommands().get(1).get(0).execute(),
                                                     getCommands().get(2).get(0).execute(),
                                                     getCommands().get(3).get(0).execute()});
    }

}
