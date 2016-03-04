package model;

import javafx.scene.image.ImageView;

public class StampModel {

	private ImageView image;
	private double x;
	private double y;
	
	public StampModel(ImageView image, double x, double y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}

	public ImageView getImage() {
		return image;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}
