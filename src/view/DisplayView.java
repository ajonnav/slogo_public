package view;
import java.util.List;
import java.util.Observable;
import addons.Features;
import constants.UIConstants;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
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
    private ComboBox<String> backgroundColorCombo;
    private ComboBox<String> penChange;
    private ComboBox<String> imageChange;
    private Features features;
    private Group imageViewGroup;
    private int frameNumber;

    public DisplayView (Group root) {
        this.features = new Features();
        this.backgroundCanvas = features.makeCanvas(UIConstants.CANVAS_X, UIConstants.BORDER_WIDTH,
                                                   UIConstants.CANVAS_SIZE, UIConstants.CANVAS_SIZE,
                                                   Color.GREEN);
        this.backgroundGC = backgroundCanvas.getGraphicsContext2D();
        this.imageViewGroup = new Group();
        this.backgroundColorCombo =
                features.makeColorPicker(UIConstants.BACKGROUND_PICK_X,
                                         UIConstants.ZERO,
                                         UIConstants.COLOR_SELECTOR_WIDTH,
                                         UIConstants.BORDER_WIDTH);
        root.getChildren().add(backgroundCanvas);
        root.getChildren().add(backgroundColorCombo);
        root.getChildren().add(imageViewGroup);
        frameNumber = 1;
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
            DisplayModel displayModel = (DisplayModel) o;
            features.updateComboBoxOptions(backgroundColorCombo, displayModel.getColorMap());
            String backgroundColorString = displayModel.getBackgroundColorIndex() + " " +
                                     displayModel.getBackgroundColor();
            backgroundColorCombo.setValue(backgroundColorString);
            backgroundGC.clearRect(0, 0, backgroundCanvas.getWidth(), backgroundCanvas.getHeight());
            imageViewGroup.getChildren().clear();
            drawBackgroundRectangle(Color.web(backgroundColorString.split(" ")[1]));
            
        }
    }
    
    public void updateStyles (DisplayModel displayModel) {
        features.updateComboBoxOptions(penChange, displayModel.getColorMap());
        features.updateComboBoxOptions(imageChange, displayModel.getImageMap());
    }
    
    public void drawTurtles(DisplayModel displayModel) {
    	for(int i = 0; i < displayModel.getFrame(frameNumber).size(); i++) {
    		ImageView image = new ImageView();
    		image.setImage(getImageFromString(displayModel.getFrame(frameNumber).get(i).getImageString()));
    		image.setFitHeight(50);
            image.setFitWidth(50);
    		image.setOpacity(displayModel.getFrame(frameNumber).get(i).getShowStatus());
	        image.setX(getDrawableX(displayModel.getFrame(frameNumber).get(i).getPositionX()));
	        image.setY(getDrawableY(displayModel.getFrame(frameNumber).get(i).getPositionY()));
	        image.setRotate(transformHeading(displayModel.getFrame(frameNumber).get(i).getHeading()));
	        imageViewGroup.getChildren().add(image);
	        for(int j = frameNumber; j<displayModel.getNumberOfFrames(); j++) {
	        	animate(image, displayModel.getFrame(j-1).get(i), displayModel.getFrame(j).get(i));
            }
	        
    	}
//        drawLines(t.getLineList());
//        drawStamps(t.getStampList());
    }
    
    public double getDrawableX(double x) {
    	return transformX(x) + UIConstants.INITIAL_X;
    }
    
    public double getDrawableY(double y) {
    	return transformY(y) + UIConstants.INITIAL_Y;
    }
    
    private void animate(ImageView image, TurtleModel prev, TurtleModel next) {
    	TranslateTransition tt = new TranslateTransition(Duration.seconds(3), image);
    	tt.setByX(prev.getPositionX() - next.getPositionX());
    	tt.setByY(prev.getPositionY() - next.getPositionY());
    	tt.setCycleCount(1);
    	tt.play();
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
