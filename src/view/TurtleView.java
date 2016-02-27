package view;
import java.util.Observable;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.TurtleModel;

public class TurtleView implements IView{
	
	private ImageView image;
	private GraphicsContext GC;
	private Color myColor;
	
	public TurtleView(ImageView image, Group root, GraphicsContext GC, Color penColor) {
		this.image = image;
		this.image.setFitHeight(50);
		this.image.setFitWidth(50);
		root.getChildren().add(this.image);
		this.myColor = penColor;
		this.GC = GC;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof TurtleModel) {
			TurtleModel turtleModel = (TurtleModel) o;
			if(turtleModel.getPenStatus()) {
				GC.strokeLine(image.getX() + image.getFitWidth()/2, image.getY() + image.getFitHeight()/2, 
						turtleModel.getPositionX(), turtleModel.getPositionY());
			}
			image.setX(turtleModel.getPositionX() - image.getFitWidth()/2);
			image.setY(turtleModel.getPositionY() - image.getFitHeight()/2);
			image.setRotate(turtleModel.getHeading()-270);
		}
	}
	
	public String getX(){
		return Double.toString(image.getX());
	}
	
	public String getY(){
		return Double.toString(image.getY());
	}

}
