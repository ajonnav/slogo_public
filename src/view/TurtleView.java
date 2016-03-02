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
	
	public TurtleView(ImageView image, Group root, GraphicsContext GC, Color c) {

		this.setImage(image);
		this.getImage().setFitHeight(50);
		this.getImage().setFitWidth(50);
		root.getChildren().add(this.getImage());
		this.myColor = c;
		this.GC = GC;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof TurtleModel) {
			TurtleModel turtleModel = (TurtleModel) o;
			GC.setStroke(myColor);
			if(turtleModel.getPenStatus()) {
				GC.strokeLine(getImage().getX() + getImage().getFitWidth()/2, getImage().getY() + getImage().getFitHeight()/2, 
						turtleModel.getPositionX(), turtleModel.getPositionY());
			}
			if(turtleModel.getShowStatus()) {
				getImage().setOpacity(1);
			}
			else {
				getImage().setOpacity(0);
			}
			getImage().setX(turtleModel.getPositionX() - getImage().getFitWidth()/2);
			getImage().setY(turtleModel.getPositionY() - getImage().getFitHeight()/2);
			getImage().setRotate(turtleModel.getHeading()-270);
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
	
	public void setColor(Color v){
		myColor = v;
	}

	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}

}
