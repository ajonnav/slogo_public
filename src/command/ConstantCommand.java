package command;

public class ConstantCommand implements ICommand {

    public static final int numChildren = 0;
    private double constant;

    public ConstantCommand (double constant) {
        this.constant = constant;
    }

    @Override
    public double execute () {
        return evaluate();
    }

    @Override
    public double evaluate () {
        return constant;
    }

}
