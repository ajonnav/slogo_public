package model;

import java.io.Serializable;
import java.util.Observable;

public class PenModel extends Observable implements Serializable {

	private static final long serialVersionUID = 2420490101474962510L;
	private static final int SOLID = 0;
	private static final int DASHED = 1;
	private static final int DOTTED = 2;
	
	private boolean status;
	private double size;
	private double colorIndex;
	private double style;
	private String colorString;
	
	public PenModel(boolean status, double size, double colorIndex, double style) {
		this.status = status;
		this.size = size;
		this.colorIndex = colorIndex;
		this.style = style;
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public double[] getStyle() {
		if(Math.round(style) == SOLID) {
			return new double[] {};
		}
		else if(Math.round(style) == DASHED) {
			return new double[] {10, 10};
		}
		else if(Math.round(style) == DOTTED) {
			return new double[] {4, 16};
		}
		return new double [] {};
	}
	
	public void setStyle(double style) {
		this.style = style;
		updateObservers();
	}
	
	public double getColorIndex() {
		return colorIndex;
	}
	
	public void setColorIndex(double colorIndex) {
		this.colorIndex = colorIndex;
		updateObservers();
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
		updateObservers();
	}
	
	public void setColorString(String colorString) {
		this.colorString = colorString;
	}
	
	public String getColorString() {
		return colorString;
	}
	
	private void updateObservers() {
		setChanged();
		notifyObservers();
	}
	
	public PenModel copyPenModel () {
		PenModel pen = new PenModel(this.status, this.size, this.colorIndex, this.style);
		pen.colorString = this.colorString;
		return pen;
	}

}
