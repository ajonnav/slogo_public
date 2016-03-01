package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class NaturalLogCommand extends Command {


    public NaturalLogCommand (Map<String, Observable> modelMap,List<String> text) {
        setNumChildren(1);
    }

    @Override
    public double execute () {
        return Math.log(getCommands().get(0).get(0).execute());
    }
}
