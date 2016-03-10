package model;

public interface IModelMap {
	public IDisplayModel getDisplay();
	
	public IHistoryModel getHistory();
	
	public IVariableModel getVariable();
	
	public ICommandsModel getCommands();
	
	public void setDisplay(IDisplayModel);
	
	public void setHistory(IHistoryModel);
	
	public void setCommands(ICommandsModel);
	
	public void setVariable(IVariableModel);
}
