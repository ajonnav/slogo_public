package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import command.Command;

public class CommandsModel extends AbstractCommandsModel {

	private Map<String, List<Command>> nameToCommandsMap;
    private Map<String, List<Command>> nameToVariablesMap;

    public CommandsModel () {
        this.nameToCommandsMap = new HashMap<>();
        this.nameToVariablesMap = new HashMap<>();
    }

    @Override
    public List<Command> getCommands (String key) {
        return nameToCommandsMap.containsKey(key) ? nameToCommandsMap.get(key) : null;
    }
    
    @Override
    public List<Command> getVariables (String key) {
        return nameToVariablesMap.containsKey(key) ? nameToVariablesMap.get(key) : null;
    }

    @Override
    public void setCommands (String key, List<Command> commands) {
        nameToCommandsMap.put(key, commands);
        setChanged();
        notifyObservers();
    }
    
    @Override
    public void setVariables(String key, List<Command> variables) {
        nameToVariablesMap.put(key, variables);
        setChanged();
        notifyObservers();
    }

    @Override
    public void clearCommands () {
        nameToCommandsMap.clear();
        nameToVariablesMap.clear();
        setChanged();
        notifyObservers();
    }
    
    @Override
    public Map<String, List<Command>> getImmutableCommandsMap () {
        return Collections.unmodifiableMap(nameToCommandsMap);
    }
    
    @Override
    public Map<String, List<Command>> getImmutableVariablesMap () {
        return Collections.unmodifiableMap(nameToVariablesMap);
    }

    @Override 
    public void updateView() {
        setChanged();
        notifyObservers();
    }
}
