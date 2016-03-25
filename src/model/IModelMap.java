package model;

public interface IModelMap {
	
	AbstractDisplayModel getDisplay();
	
	AbstractHistoryModel getHistory();
	
	AbstractVariableModel getVariable();
	
	AbstractCommandsModel getCommands();
	
	void setDisplay(AbstractDisplayModel displayModel);
	
	void setHistory(AbstractHistoryModel historyModel);
	
	void setCommands(AbstractCommandsModel commandsModel);
	
	void setVariable(AbstractVariableModel historyModel);
	
}
