package view;
import java.util.Observable;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import model.TurtleModel;

public class TurtleView implements IView{
	
	private ImageView image;
	private GraphicsContext GC;
	
	public TurtleView(ImageView image, Group root, GraphicsContext GC) {
		this.image = image;
		this.image.setFitHeight(50);
		this.image.setFitWidth(50);
		root.getChildren().add(this.image);
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
