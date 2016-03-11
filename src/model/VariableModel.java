package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class VariableModel extends IVariableModel{

    private Stack<HashMap<String, Double>> variableMap;

    public VariableModel () {
        this.variableMap = new Stack<HashMap<String, Double>>();
        this.variableMap.push(new HashMap<String, Double>());
    }

    public Double getVariable (String variable) {
        return variableMap.peek().containsKey(variable) ? variableMap.peek().get(variable) : setVariable(variable, 0.0);
    }
    
    public void pushScope(Map<String, Double> variableMap) {
        this.variableMap.push((HashMap<String, Double>) variableMap);
        updateView();
    }
    
    public void popScope() {
        this.variableMap.pop();
        updateView();
    }
    
    public Double setVariable (String variable, double value) {
        variableMap.peek().put(variable, value);
        updateView();
        return value;
    }

    public void clearVariables () {
        variableMap.clear();
        updateView();
    }
    
    public Map<String, Double> getImmutableVariableMap () {
        return Collections.unmodifiableMap(variableMap.peek());
    }
    
    public void updateView() {
        setChanged();
        notifyObservers();
    }
}
