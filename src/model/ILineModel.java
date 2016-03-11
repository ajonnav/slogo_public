package model;

public interface ILineModel {

	public double getX1();
	
	public double getX2();
	
	public double getY1();
	
	public double getY2();
	
	public double getWidth();
	
	public String getColor();
	
	public double[] getStyle();
	
	public ILineModel copyLineModel();
	
}
