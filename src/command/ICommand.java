package command;

public interface ICommand {
         
	public double execute();
	
	public double evaluate();
	
	public int getNumChildren();
}
