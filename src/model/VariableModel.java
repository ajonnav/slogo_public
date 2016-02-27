package model;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class VariableModel extends Observable{
	
	private Map<String, Double> map;
	
	public VariableModel() {
		this.map = new HashMap<String, Double>();
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
		return value;
	}
	
	public void printMap() {
	    for(String s : map.keySet()) {
	        System.out.print(s + " " + map.get(s));
	    }
	}
}
