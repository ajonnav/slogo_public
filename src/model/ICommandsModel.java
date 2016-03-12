package model;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import command.Command;

public abstract class ICommandsModel extends Observable implements ViewableCommandsModel{
	
	public abstract List<Command> getCommands (String key);
	
	public abstract void setCommands (String key, List<Command> commands);
	
	public abstract List<Command> getVariables (String key);
	
	public abstract void setVariables(String key, List<Command> variables);
	
	public abstract void clearCommands ();
	
	public abstract Map<String, List<Command>> getImmutableCommandsMap ();
	
	public abstract Map<String, List<Command>> getImmutableVariablesMap ();
	
	public abstract void updateView ();
}
