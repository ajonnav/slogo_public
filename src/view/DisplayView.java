package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import addons.Features;
import constants.UIConstants;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.DisplayModel;
import model.LineModel;
import model.StampModel;
import model.TurtleModel;

public class DisplayView implements IView {

	private Canvas backgroundCanvas;
	private GraphicsContext backgroundGC;
	private ComboBox<HBox> backgroundColorComboBox;
	private Features features;
	private List<Animation> animations;
	private Group imageViewGroup;
	private int lastExpressionFrameNumber;
	private List<ImageView> turtleViews;
	private Group dispRoot;
	private List<Rectangle> actives;

	public DisplayView(Group root) {
		dispRoot = root;
		this.features = new Features();
		this.backgroundCanvas = features.makeCanvas(UIConstants.CANVAS_X,
				UIConstants.BORDER_WIDTH, UIConstants.CANVAS_SIZE,
				UIConstants.CANVAS_SIZE, Color.GREEN);
		this.backgroundGC = backgroundCanvas.getGraphicsContext2D();
		animations = new ArrayList<>();
		turtleViews = new ArrayList<>();
		actives = new ArrayList<>();
		this.imageViewGroup = new Group();
		this.lastExpressionFrameNumber = 1;
		this.backgroundColorComboBox = features.makeColorPicker(
				UIConstants.BACKGROUND_PICK_X, UIConstants.ZERO,
				UIConstants.COLOR_SELECTOR_WIDTH, UIConstants.BORDER_WIDTH);
		root.getChildren().add(backgroundCanvas);
		root.getChildren().add(backgroundColorComboBox);
		root.getChildren().add(imageViewGroup);
	}

	public void setUpLayers() {
		this.backgroundCanvas = features.makeCanvas(UIConstants.CANVAS_X,
				UIConstants.BORDER_WIDTH, UIConstants.CANVAS_SIZE,
				UIConstants.CANVAS_SIZE, Color.TRANSPARENT);
		this.backgroundGC = backgroundCanvas.getGraphicsContext2D();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof DisplayModel) {
			DisplayModel displayModel = (DisplayModel) o;
			if (displayModel.isToAnimate()) {
				clearPastBorders(dispRoot);
				displayModel.setToAnimate(false);
				features.updateComboBoxOptions(backgroundColorComboBox,
						displayModel.getColorMap());
				String backgroundColorString = displayModel
						.getBackgroundColorIndex()
						+ " "
						+ displayModel.getBackgroundColor();
				// backgroundColorComboBox.setValue(backgroundColorString);
				backgroundGC.clearRect(0, 0, backgroundCanvas.getWidth(),
						backgroundCanvas.getHeight());
				drawBackgroundRectangle(Color.web(backgroundColorString
						.split(" ")[1]));
				animations.clear();
				drawTurtles(displayModel);
				SequentialTransition st = new SequentialTransition();
				for (Animation a : animations) {
					st.getChildren().add(a);
				}
				st.play();
				lastExpressionFrameNumber = displayModel.getTurtleList().get(0)
						.getFrameNumber();
				st.onFinishedProperty().set(e -> {
					drawActiveBorders(displayModel, dispRoot);
				});
			}
		}
	}

	public void clearPastBorders(Group root) {
		for (Rectangle n : actives){
			root.getChildren().remove(n);
		}
		actives.clear();
	}

	public void drawActiveBorders(DisplayModel disp, Group root) {
		for (TurtleModel turtle : disp.getTurtleList()) {
			if (turtle.isActive())
				prepBorder(turtle);
		}
		for (Rectangle n : actives) {
			root.getChildren().add(n);
		}
	}

	public void prepBorder(TurtleModel turtle) {
		Rectangle area = new Rectangle(getDrawableX(turtle.getPositionX()),
				getDrawableY(turtle.getPositionY()), UIConstants.TURTLE_SIZE,
				UIConstants.TURTLE_SIZE);
		area.setFill(Color.TRANSPARENT);
		area.setStroke(Color.BLACK);
		actives.add(area);
	}

	public void drawTurtles(DisplayModel displayModel) {
		for (int i = lastExpressionFrameNumber; i < displayModel
				.getTurtleList().get(0).getFrameNumber(); i++) {
			for (int j = turtleViews.size(); j < displayModel.getTurtleList()
					.size(); j++) {
				ImageView image = initImageView(displayModel.getTurtleList()
						.get(j), i);
				imageViewGroup.getChildren().add(image);
				turtleViews.add(image);
			}
			for (int j = 0; j < displayModel.getTurtleList().size(); j++) {
				animate(turtleViews.get(j),
						displayModel.getTurtleList().get(j), i - 1, i);
			}
		}
	}

	private void animate(ImageView image, TurtleModel turtle, int prev, int next) {
		double translationTime = turtle.getPositionX(prev) != turtle
				.getPositionX(next)
				|| turtle.getPositionY(prev) != turtle.getPositionY(next) ? 100
				: 1;
		double rotationTime = turtle.getHeading(prev) != turtle
				.getHeading(next) ? 100 : 1;
		TranslateTransition tt = new TranslateTransition(
				Duration.millis(translationTime), image);
		RotateTransition rt = new RotateTransition(
				Duration.millis(rotationTime), image);
		tt.setFromX(getDrawableX(turtle.getPositionX(prev)));
		tt.setFromY(getDrawableY(turtle.getPositionY(prev)));
		tt.setToX(getDrawableX(turtle.getPositionX(next)));
		tt.setToY(getDrawableY(turtle.getPositionY(next)));
		tt.setCycleCount(1);
		rt.setFromAngle(transformHeading(turtle.getHeading(prev)));
		rt.setToAngle(transformHeading(turtle.getHeading(next)));
		rt.setCycleCount(1);
		animations.add(tt);
		animations.add(rt);
	}

	public ImageView initImageView(TurtleModel t, int currFrame) {
		ImageView image = new ImageView();
		image.setImage(getImageFromString(t.getImageString(currFrame)));
		image.setFitHeight(UIConstants.TURTLE_SIZE);
		image.setFitWidth(UIConstants.TURTLE_SIZE);
		image.setOpacity(t.getShowStatus(currFrame));
		image.setX(0);
		image.setY(0);
		image.setRotate(transformHeading(t.getHeading(currFrame)));
		return image;
	}

	public double getDrawableX(double x) {
		return transformX(x) + UIConstants.INITIAL_X;
	}

	public double getDrawableY(double y) {
		return transformY(y) + UIConstants.INITIAL_Y;
	}

	public void drawLines(List<LineModel> list) {
		list.stream().forEach(
				l -> {
					backgroundGC.setLineDashes(l.getStyle());
					backgroundGC.setStroke(Color.web(l.getColor()));
					backgroundGC.setLineWidth(l.getWidth());
					backgroundGC.strokeLine(transformX(l.getX1()),
							transformY(l.getY1()), transformX(l.getX2()),
							transformY(l.getY2()));
				});
	}

	public void drawStamps(List<StampModel> list) {
		list.stream().forEach(
				s -> {
					Image image = getImageFromString(s.getImageString());
					drawRotatedImage(image,
							transformX(s.getPositionX()) - 50 / 2,
							transformY(s.getPositionY()) - 50 / 2,
							transformHeading(s.getHeading()));
				});
	}

	public void drawBackgroundRectangle(Color value) {
		backgroundGC.setFill(value);
		backgroundGC.fillRect(0, 0, backgroundCanvas.getWidth(),
				backgroundCanvas.getHeight());
	}

	private double transformX(double x) {
		return x + (double) UIConstants.CANVAS_SIZE / 2;
	}

	private double transformY(double y) {
		return -y + (double) UIConstants.CANVAS_SIZE / 2;
	}

	private double transformHeading(double heading) {
		return 90 - heading;
	}

	public Image getImageFromString(String image) {
		return new Image(getClass().getClassLoader().getResourceAsStream(image));
	}

	private void drawRotatedImage(Image image, double imageX, double imageY,
			double heading) {
		ImageView iv = new ImageView(image);
		iv.setRotate(heading);
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		Image rotatedImage = iv.snapshot(params, null);
		backgroundGC.drawImage(rotatedImage, imageX, imageY, 50, 50);
	}
}