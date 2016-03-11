package model;

public interface IStampModel {

	String getImageString ();
	
	double getPositionX ();
	
	double getPositionY ();
	
	double getHeading ();
	
	IStampModel copyStampModel();
}
