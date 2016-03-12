package model;

import java.util.List;
import java.util.Map;

public interface ViewableTurtleModel {
	
	List<ViewableStampModel> getViewableStampList(int frameNumber);
	
	List<ViewableLineModel> getViewableLineList(int frameNumber);
	
	boolean isActive (int frameNumber);
	
	void setActive (boolean isActive);
	
	double getLineWidth ();
	
	String getImageString (int frameNumber);
	
	double getImageIndex ();
	
	double getPenColorIndex ();
	
	boolean isShowStatus();
	
	boolean isPenStatus ();
	
	ViewablePenModel getViewablePen (int frameNumber);
	
	double getHeading(int frameNumber);
	
	double getPositionX (int frameNumber);
	
	double getPositionY (int frameNumber);
	
	double getShowStatus (int frameNumber);
	
	void setColorMap(Map<Double, String> colorMap);
	
	void setImageMap(Map<Double, String> imageMap);
	
	int getNumStamps();
	
	int getFrameNumber();
	
	double setPenColorIndex (double[] penColorIndex);
	
	double setPenStyleIndex (double[] penStyleIndex);
	
	double setImageIndex (double[] imageIndex);

	List<ViewableLineModel> getViewableLineList();
}
