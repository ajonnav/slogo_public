package model;

public interface IModelMap {
	
	public DisplayModel getDisplay();
	
	public IHistoryModel getHistory();
	
	public IVariableModel getVariable();
	
	public ICommandsModel getCommands();
	
	public void setDisplay(DisplayModel displayModel);
	
	public void setHistory(IHistoryModel historyModel);
	
	public void setCommands(ICommandsModel commandsModel);
	
	public void setVariable(IVariableModel historyModel);
	
}
