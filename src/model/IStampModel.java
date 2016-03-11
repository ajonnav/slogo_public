package model;

public interface IStampModel {

	public String getImageString ();
	
	public double getPositionX ();
	
	public double getPositionY ();
	
	public double getHeading ();
	
	public IStampModel copyStampModel();
}
