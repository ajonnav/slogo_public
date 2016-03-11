package model;


public class PenModel extends IPenModel {

    private static final int SOLID = 0;
    private static final int DASHED = 1;
    private static final int DOTTED = 2;
    private boolean status;
    private double size;
    private double colorIndex;
    private double style;
    private String colorString;

    public PenModel (boolean status, double size, double colorIndex, double style) {
        this.status = status;
        this.size = size;
        this.colorIndex = colorIndex;
        this.style = style;
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
    public double[] getStyle () {
        if (Math.round(style) == SOLID) {
            return new double[] {};
        }
        else if (Math.round(style) == DASHED) {
            return new double[] { 10, 10 };
        }
        else if (Math.round(style) == DOTTED) {
            return new double[] { 4, 16 };
        }
        return new double[] {};
    }

    @Override
    public void setStyle (double style) {
        this.style = style;
        updateObservers();
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
        PenModel pen = new PenModel(this.status, this.size, this.colorIndex, this.style);
        pen.setColorString(this.colorString);
        return pen;
    }

}
