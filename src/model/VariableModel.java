package model;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class VariableModel extends Observable{
	
	private Map<String, Double> variableMap;
	
	public VariableModel() {
		this.variableMap = new HashMap<>();
	}
	
	public Double getVariable(String variable) {
		if(variableMap.containsKey(variable)) {
			return variableMap.get(variable);
		}
		else {
			return setVariable(variable, 0.0);
		}
	}
	
	public Double setVariable(String variable, double value) {
		variableMap.put(variable, value);
		setChanged();
		notifyObservers();
		return value;
	}
	
	public void clearVariables() {
		variableMap.clear();
		setChanged();
		notifyObservers();
	}
	
	public Map<String, Double> getImmutableVariableMap() {
		return Collections.unmodifiableMap(variableMap);
	}
}
