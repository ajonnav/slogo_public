package model;

public class PenModel {

	private static final int SOLID = 0;
	private static final int DASHED = 1;
	private static final int DOTTED = 2;
	
	private boolean status;
	private double size;
	private double color;
	private double style;
	
	public PenModel(boolean status, double size, double color, double style) {
		this.status = status;
		this.size = size;
		this.color = color;
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
			return new double[] {20};
		}
		else if(Math.round(style) == DASHED) {
			return new double[] {10, 10};
		}
		else if(Math.round(style) == DOTTED) {
			return new double[] {4, 16};
		}
		return new double [] {20};
	}
	public void setStyle(double style) {
		this.style = style;
	}
	public double getColor() {
		return color;
	}
	public void setColor(double color) {
		this.color = color;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}

}
