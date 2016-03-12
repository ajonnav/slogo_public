package model;

import java.util.Map;

public interface ViewableVariableModel {
    
    Map<String, Double> getImmutableVariableMap();
    
    void updateView();

}
