package command;

import java.util.List;

import model.IModelMap;

public class ConstantCommand extends Command {

    private double constant;

    public ConstantCommand (IModelMap modelMap, String expression, List<String> text) {
        super(modelMap, expression, text);
        this.constant = Double.parseDouble(text.get(0));
    }
    
    @Override
    public double execute () {
        return constant;
    }

}
