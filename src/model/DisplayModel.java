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

    public void setBackgroundColorIndex (double backgroundColorIndex) {
        this.backgroundColorIndex = backgroundColorIndex;
        updateView();
    }
    
    public Map<Double, String> getColorMap() {
        return colorMap;
    }
        
    public void updateView() {
        setChanged();
        notifyObservers();
    }

}
