package command;

import java.util.List;
import java.util.Map;
import java.util.Observable;

public class ConstantCommand extends Command {

    private double constant;

    public ConstantCommand (Map<String, Observable> modelMap, List<String> text) {
        this.constant = Double.parseDouble(text.get(0));
    }
    
    @Override
    public double execute () {
        return constant;
    }

}
