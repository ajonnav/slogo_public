package model;

import java.util.Observable;

public abstract class IPenModel extends Observable {

	public abstract boolean getStatus ();
	
	public abstract void setStatus (boolean status);
	
	public abstract double getStyleIndex ();
	
	public abstract void setStyleIndex (double style);
	
	public abstract double[] getStyle ();
	
	public abstract double getColorIndex ();
	
	public abstract void setColorIndex (double colorIndex);
	
	public abstract double getSize ();
	
	public abstract void setSize (double size);
	
	public abstract void setColorString (String colorString);
	
	public abstract String getColorString ();
	
	public abstract IPenModel copyPenModel ();

}
