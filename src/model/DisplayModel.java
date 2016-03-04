package model;

import java.util.Map;
import java.util.Observable;

public class DisplayModel extends Observable{
    
    private double backgroundColorIndex;
    private Map<Double, String> colorMap;
    
    public DisplayModel(Map<Double, String> colorMap) {
        this.colorMap = colorMap;
        backgroundColorIndex = 3;
        setChanged();
    }
    
    public double getBackgroundColorIndex () {
        return backgroundColorIndex;
    }

    public double setBackgroundColorIndex (double backgroundColorIndex) {
        this.backgroundColorIndex = backgroundColorIndex;
        updateView();
        return backgroundColorIndex;
    }
    
    public Map<Double, String> getColorMap() {
        return colorMap;
    }
    
    public void setColorMap(Map<Double, String> colorMap) {
        this.colorMap = colorMap;
        updateView();
    }
        
    public void updateView() {
        setChanged();
        notifyObservers();
    }

}
