package view;
import java.util.Observable;

import constants.UIConstants;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.TurtleModel;

public class TurtleView implements IView{
	
	private ImageView image;
	private GraphicsContext GC;
	private Color myColor;
	
	public TurtleView(ImageView image, Group root, GraphicsContext GC, Color c) {
		this.image = image;
		this.image.setFitHeight(50);
		this.image.setFitWidth(50);
		this.image.setX(transformX(0));
		this.image.setY(transformY(0));
		root.getChildren().add(this.image);
		this.myColor = c;
		this.GC = GC;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof TurtleModel) {			
			TurtleModel turtleModel = (TurtleModel) o;
			GC.setStroke(myColor);
			if(turtleModel.getPenStatus()) {
				GC.strokeLine(image.getX(), image.getY(), 
						transformX(turtleModel.getPositionX()), transformY(turtleModel.getPositionY()));
			}
			image.setOpacity(Boolean.compare(turtleModel.getShowStatus(), false));
			image.setX(transformX(turtleModel.getPositionX()));
			image.setY(transformY(turtleModel.getPositionY()));
			image.setRotate(transformHeading(turtleModel.getHeading()));
		}
	}
	
	public GraphicsContext getGC(){
		return GC;
	}
	
	public String getX(){
		return Double.toString(getImage().getX());
	}
	
	public String getY(){
		return Double.toString(getImage().getY());
	}
	
	public void changeImage(ImageView i){
		image = i;
	}
	public void setColor(Color v){
		myColor = v;
	}
	
	private double transformX(double x) {
		return x + (double)UIConstants.CANVAS_SIZE/2;
	}
	
	private double transformY(double y) {
		return -y + (double)UIConstants.CANVAS_SIZE/2;
	}

	private double transformHeading(double heading) {
		return 90 - heading;
	}

	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}
}
