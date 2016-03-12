package model;

import java.util.Map;
import java.util.Observable;

public abstract class IVariableModel extends Observable implements ViewableVariableModel {
	
	public abstract Double getVariable (String variable);
	
	public abstract void pushScope(Map<String, Double> variableMap);
	
	public abstract void popScope();
	
	public abstract Double setVariable (String variable, double value);
	
	public abstract void clearVariables();
	
	public abstract Map<String, Double> getImmutableVariableMap();
	
	public abstract void updateView();
	
}
