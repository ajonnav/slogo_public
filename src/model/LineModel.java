package model;

public class LineModel {
	
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private double size;
	private double color;
	private double[] style;
	
	public LineModel(double x1, double y1, double x2, double y2, double size, double color, double[] style) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.color = color;
		this.size = size;
		this.style = style;
	}
	
	public double getX1() {
		return x1;
	}
	
	public double getX2() {
		return x2;
	}
	
	public double getY1() {
		return y1;
	}
	
	public double getY2() {
		return y2;
	}
	
	public double getSize() {
		return size;
	}
	
	public double getColor() {
		return color;
	}
	
	public double[] getStyle() {
		return style;
	}
	
}
