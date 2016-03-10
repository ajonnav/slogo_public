package view;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import addons.Features;
import constants.UIConstants;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.DisplayModel;
import model.LineModel;
import model.StampModel;
import model.TurtleModel;


public class DisplayView implements IView {

    private Canvas backgroundCanvas;
    private GraphicsContext backgroundGC;
    private ComboBox<String> backgroundColorComboBox;
    private ComboBox<String> penChange;
    private ComboBox<String> imageChange;
    private Features features;
    private Group imageViewGroup;
    private int frameNumber;
    private List<Animation> animationList;

    public DisplayView (Group root) {
        this.features = new Features();
        this.backgroundCanvas = features.makeCanvas(UIConstants.CANVAS_X, UIConstants.BORDER_WIDTH,
                                                   UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE,
                                                   Color.GREEN);
        this.backgroundGC = backgroundCanvas.getGraphicsContext2D();
        this.imageViewGroup = new Group();
        this.backgroundColorComboBox =
                features.makeColorPicker(1100,
                                         550,
                                         UIConstants.COLOR_SELECTOR_WIDTH,
                                         UIConstants.BORDER_WIDTH);
        root.getChildren().add(backgroundCanvas);
        root.getChildren().add(backgroundColorComboBox);
        root.getChildren().add(imageViewGroup);
        animationList = new ArrayList<>();
        frameNumber = 0;
    }

    public void setUpLayers () {
        this.backgroundCanvas = features.makeCanvas(UIConstants.CANVAS_X, UIConstants.BORDER_WIDTH,
                                             UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE,
                                             Color.TRANSPARENT);
        this.backgroundGC = backgroundCanvas.getGraphicsContext2D();
    }

    public void setUpPickers () {
        this.penChange =
                features.makeColorPicker(UIConstants.PEN_PICK_X, UIConstants.ZERO,
                                UIConstants.COLOR_SELECTOR_WIDTH, UIConstants.BORDER_WIDTH);
        this.imageChange =
                features.makeColorPicker(UIConstants.IMAGE_SELECT_X, UIConstants.ZERO,
                                UIConstants.IMAGE_SELECT_WIDTH, UIConstants.BORDER_WIDTH);
    }
    
    @Override
    public void update (Observable o, Object arg) {
        if (o instanceof DisplayModel) {
        	animationList.clear();
            DisplayModel displayModel = (DisplayModel) o;
            features.updateComboBoxOptions(backgroundColorComboBox, displayModel.getColorMap());
            String backgroundColorString = displayModel.getBackgroundColorIndex() + " " +
                                     displayModel.getBackgroundColor();
            backgroundColorComboBox.setValue(backgroundColorString);
            backgroundGC.clearRect(0, 0, backgroundCanvas.getWidth(), backgroundCanvas.getHeight());
            drawBackgroundRectangle(Color.web(backgroundColorString.split(" ")[1]));
            if(displayModel.getNumFrames()>0) {
	            drawTurtles(displayModel);
	            SequentialTransition s = new SequentialTransition((Transition[]) animationList.toArray(new Transition[animationList.size()]));
	            s.setAutoReverse(false);
	            s.setCycleCount(animationList.size());
	            s.play();
            }
        }
    }
    
    public void updateStyles (DisplayModel displayModel) {
        features.updateComboBoxOptions(penChange, displayModel.getColorMap());
        features.updateComboBoxOptions(imageChange, displayModel.getImageMap());
    }
    
    public void drawTurtles(DisplayModel displayModel) {
/*    	for(int i=frameNumber; i<displayModel.getNumFrames()-1; i++) {
    		animateFrames(displayModel.getFrame(i), displayModel.getFrame(i+1));
    		frameNumber++;
    	}*/
    	displayModel.getFrame(displayModel.getNumFrames()-1).stream().forEach( t->
    	{
    		drawLines(t.getLineList());
    		drawStamps(t.getStampList());
    		Image image = getImageFromString(t.getImageString());
    		drawRotatedImage(image, transformX(t.getPositionX()) - 50/2,
    				 transformY(t.getPositionY()) - 50/2,
    				 transformHeading(t.getHeading()));
    		//drawLines(t.getLineList());
    		//drawStamps(t.getStampList());
    	});
    				
        
    }
    
    public void animateFrames(List<TurtleModel> prev, List<TurtleModel> next) {
    	if(prev.size()>next.size()) {
    		imageViewGroup.getChildren()
    			.removeIf(image->imageViewGroup.getChildren()
    							.indexOf(image) > next.size());
    	}
    	if(imageViewGroup.getChildren().size()<next.size()) {
    		for(int i=imageViewGroup.getChildren().size();i<next.size();i++) {
    			ImageView image = new ImageView();
        		image.setImage(getImageFromString(next.get(i).getImageString()));
        		image.setFitHeight(50);
                image.setFitWidth(50);
        		image.setOpacity(next.get(i).getShowStatus());
    	        image.setX(getDrawableX(next.get(i).getPositionX()));
    	        image.setY(getDrawableY(next.get(i).getPositionY()));
    	        image.setRotate(transformHeading(next.get(i).getHeading()));
    	        imageViewGroup.getChildren().add(image);
    		}
    	}
    	for(int i=0; i<prev.size(); i++) {
    		animate(imageViewGroup.getChildren().get(i), prev.get(i), next.get(i));
    	}
    }
    
    public double getDrawableX(double x) {
    	return transformX(x) + UIConstants.INITIAL_X;
    }
    
    public double getDrawableY(double y) {
    	return transformY(y) + UIConstants.INITIAL_Y;
    }
    
    private void animate(Node image, TurtleModel prev, TurtleModel next) {
    	TranslateTransition tt = new TranslateTransition(Duration.seconds(1), image);
    	tt.setByX(next.getPositionX() - prev.getPositionX());
    	tt.setByY(prev.getPositionY() - next.getPositionY());
    	tt.setAutoReverse(true);
    	tt.setCycleCount(1);
    	animationList.add(tt);
    }
    
    public void drawLines(List<LineModel> list) {
    	list.stream().forEach(l->
    	{
    		backgroundGC.setLineDashes(l.getStyle());
    		backgroundGC.setStroke(Color.web(l.getColor()));
    		backgroundGC.setLineWidth(l.getWidth());
    		backgroundGC.strokeLine(transformX(l.getX1()), transformY(l.getY1()),
                    transformX(l.getX2()), transformY(l.getY2()));
    	});
    }

	public void drawStamps(List<StampModel> list) {
		list.stream().forEach(s->
		{
			Image image = getImageFromString(s.getImageString());
	        drawRotatedImage(image, transformX(s.getPositionX()) - 50/2,
	        		transformY(s.getPositionY()) - 50/2,
	        		transformHeading(s.getHeading()));
		});
	}

    public void drawBackgroundRectangle (Color value) {
        backgroundGC.setFill(value);
        backgroundGC.fillRect(0, 0, backgroundCanvas.getWidth(), backgroundCanvas.getHeight());
    }
    
    private double transformX (double x) {
        return x + (double) UIConstants.CANVAS_SIZE / 2;
    }

    private double transformY (double y) {
        return -y + (double) UIConstants.CANVAS_SIZE / 2;
    }

    private double transformHeading (double heading) {
        return 90 - heading;
    }
    
    public Image getImageFromString (String image) {
        return new Image(getClass().getClassLoader().getResourceAsStream(image));
    }
    
    private void drawRotatedImage(Image image, double imageX, double imageY, double heading) {
    	ImageView iv = new ImageView(image);
    	iv.setRotate(heading);
    	SnapshotParameters params = new SnapshotParameters();
    	params.setFill(Color.TRANSPARENT);
    	Image rotatedImage = iv.snapshot(params, null);
    	backgroundGC.drawImage(rotatedImage, imageX, imageY, 50, 50);
    }

}
