package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;


public class VariableModel extends Observable {

    private Map<String, Double> variableMap;

    public VariableModel () {
        this.variableMap = new HashMap<String, Double>();
    }

    public Double getVariable (String variable) {
        return variableMap.containsKey(variable) ? variableMap.get(variable) : setVariable(variable, 0.0);
    }

    public Double setVariable (String variable, double value) {
        System.out.println("updated variable");
        variableMap.put(variable, value);
        setChanged();
        notifyObservers();
        return value;
    }

    public void clearVariables () {
        variableMap.clear();
        setChanged();
        notifyObservers();
    }
    
    public void printMap() {
        for(String s : variableMap.keySet()) {
            System.out.println(s + " " + variableMap.get(s));
        }
    }
    
    public Map<String, Double> getImmutableVariableMap () {
        return Collections.unmodifiableMap(variableMap);
    }

}
