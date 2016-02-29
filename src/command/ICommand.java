package command;

public interface ICommand {
	
	double execute();
	
	double evaluate();
	
	public int getNumChildren();
}
