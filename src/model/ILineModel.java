package model;

interface ILineModel {

	double getX1();
	
	double getX2();
	
	double getY1();
	
	double getY2();
	
	double getWidth();
	
	String getColor();
	
	double[] getStyle();
	
	ILineModel copyLineModel();
	
}
