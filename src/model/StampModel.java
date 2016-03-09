package model;

public class StampModel {

	private String imageString;
	private double x;
	private double y;
	
	public StampModel(String image, double x, double y) {
		this.imageString = image;
		this.x = x;
		this.y = y;
	}

	public String getImageString() {
		return imageString;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}
