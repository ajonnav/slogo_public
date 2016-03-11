package model;

public interface IModelMap {
	
	IDisplayModel getDisplay();
	
	IHistoryModel getHistory();
	
	IVariableModel getVariable();
	
	ICommandsModel getCommands();
	
	void setDisplay(IDisplayModel displayModel);
	
	void setHistory(IHistoryModel historyModel);
	
	void setCommands(ICommandsModel commandsModel);
	
	void setVariable(IVariableModel historyModel);
	
}
