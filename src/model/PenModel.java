package model;


public class PenModel extends IPenModel {

    private static final int SOLID = 0;
    private static final int DASHED = 1;
    private static final int DOTTED = 2;
    private boolean status;
    private double size;
    private double colorIndex;
    private double styleIndex;
    private String colorString;

    public PenModel (boolean status, double size, double colorIndex, double styleIndex) {
        this.status = status;
        this.size = size;
        this.colorIndex = colorIndex;
        this.styleIndex = styleIndex;
    }

    @Override
    public boolean getStatus () {
        return status;
    }

    @Override
    public void setStatus (boolean status) {
        this.status = status;
    }

    @Override
    public double getStyleIndex () {
        return styleIndex;
    }

    @Override
    public void setStyleIndex (double style) {
        this.styleIndex = style;
        updateObservers();
    }
    
    public double[] getStyle() {
    	if (Math.round(styleIndex) == SOLID) {
            return new double[] {};
        }
        else if (Math.round(styleIndex) == DASHED) {
            return new double[] { 10, 10 };
        }
        else if (Math.round(styleIndex) == DOTTED) {
            return new double[] { 1, 4 };
        }
        return new double[] {};
    }

    @Override
    public double getColorIndex () {
        return colorIndex;
    }

    @Override
    public void setColorIndex (double colorIndex) {
        this.colorIndex = colorIndex;
        updateObservers();
    }

    @Override
    public double getSize () {
        return size;
    }

    @Override
    public void setSize (double size) {
        this.size = size;
        updateObservers();
    }

    @Override
    public void setColorString (String colorString) {
        this.colorString = colorString;
    }

    @Override
    public String getColorString () {
        return colorString;
    }
    
    private void updateObservers () {
        setChanged();
        notifyObservers();
    }
    
    @Override
    public IPenModel copyPenModel () {
        PenModel pen = new PenModel(this.status, this.size, this.colorIndex, this.styleIndex);
        pen.setColorString(this.colorString);
        return pen;
    }

}
