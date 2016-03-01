package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import command.Command;

public class CommandsModel extends Observable {

    private Map<String, List<Command>> nameToCommandsMap;
    private Map<String, List<Command>> nameToVariablesMap;

    public CommandsModel () {
        this.nameToCommandsMap = new HashMap<String, List<Command>>();
        this.nameToVariablesMap = new HashMap<String, List<Command>>();
    }

    public List<Command> getCommands (String key) {
        return nameToCommandsMap.containsKey(key) ? nameToCommandsMap.get(key) : null;
    }
    
    public List<Command> getVariables (String key) {
        return nameToVariablesMap.containsKey(key) ? nameToVariablesMap.get(key) : null;
    }

    public void setCommands (String key, List<Command> commands) {
        nameToCommandsMap.put(key, commands);
        setChanged();
        notifyObservers();
    }
    
    public void setVariables(String key, List<Command> variables) {
        nameToVariablesMap.put(key, variables);
    }

    public void clearCommands () {
        nameToCommandsMap.clear();
        nameToVariablesMap.clear();
        setChanged();
        notifyObservers();
    }
    
    public void printMap() {
        for(String s : nameToCommandsMap.keySet()) {
            System.out.println(s);
        }
    }
    
    public Map<String, List<Command>> getImmutableCommandsMap () {
        return Collections.unmodifiableMap(nameToCommandsMap);
    }

}
