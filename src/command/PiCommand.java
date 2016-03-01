package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;


public class PiCommand extends Command {

    public PiCommand (Map<String, Observable> modelMap, List<String> text) {
        setNumChildren(0);
    }

    @Override
    public double execute () {
        return Math.PI;
    }
}
