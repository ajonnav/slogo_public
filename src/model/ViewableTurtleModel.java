package model;

import java.util.List;
import java.util.Map;

public interface ViewableTurtleModel {
	
	double getLastDouble(List<Double> list);
	
	List<IStampModel> getStampList();
	
	List<ILineModel> getLineList();
	
	List<IStampModel> getStampList(int frameNumber);
	
	List<ILineModel> getLineList(int frameNumber);
	
	boolean isActive ();
	
	boolean isActive (int frameNumber);
	
	void setActive (boolean isActive);
	
	double getLineWidth ();
	
	String getImageString (int frameNumber);
	
	double getImageIndex ();
	
	double getPenColorIndex ();
	
	boolean isShowStatus();
	
	boolean isPenStatus ();
	
	IPenModel getPen (int frameNumber);
	
	IPenModel getPen ();
	
	double getHeading(int frameNumber);
	
	double getHeading ();
	
	double getPositionX (int frameNumber);
	
	double getPositionX ();
	
	double getPositionY (int frameNumber);
	
	double getPositionY ();
	
	double getShowStatus (int frameNumber);
	
	void setColorMap(Map<Double, String> colorMap);
	
	void setImageMap(Map<Double, String> imageMap);
	
	int getNumStamps();
	
	int getFrameNumber();
}
