package model;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class VariableModel extends Observable{
	
	private Map<String, Double> map;
	
	public VariableModel() {
		this.map = new HashMap<>();
	}
	
	public Double getVariable(String variable) {
		if(map.containsKey(variable)) {
			return map.get(variable);
		}
		else {
			return setVariable(variable, 0.0);
		}
	}
	
	public Double setVariable(String variable, double value) {
		map.put(variable, value);
		setChanged();
		notifyObservers();
		return value;
	}
	
	public void clearVariables() {
		map.clear();
		setChanged();
		notifyObservers();
	}
	
	public Map<String, Double> getImmutableVariableMap() {
		return Collections.unmodifiableMap(map);
	}
}
