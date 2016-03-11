package model;

interface IModelMap {
	
	DisplayModel getDisplay();
	
	IHistoryModel getHistory();
	
	IVariableModel getVariable();
	
	ICommandsModel getCommands();
	
	void setDisplay(DisplayModel displayModel);
	
	void setHistory(IHistoryModel historyModel);
	
	void setCommands(ICommandsModel commandsModel);
	
	void setVariable(IVariableModel historyModel);
	
}
