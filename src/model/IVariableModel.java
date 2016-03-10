package model;

import java.util.Map;

public interface IVariableModel {
	
	public Double getVariable (String variable);
	
	public void pushScope(Map<String, Double> variableMap);
	
	public void popScope();
	
	public Double setVariable (String variable, double value);
	
	public void clearVariables();
	
	public Map<String, Double> getImmutableVariableMap();
	
	public void updateView();
	
}
