// This entire file is part of my masterpiece
// Michael Kuryshev

/**
 * Unlike my last masterpiece, I believe this masterpiece best displays what I have learned from SLogo. Featuring 
 * use of observables for updating the view based on backend changes, lambda expressions eliminate all but one for 
 * loop and simplify expressions, task assignments going from basic to extension implementation, this code continues
 * good practices learned from previous assignments and extends them up to where we are now. While I was almost 
 * certain this would be my masterpiece as we were recoding it on Thursday before submission, I did review each method 
 * for 10-15 minutes (except update() ) and besides removing a hard coded String, reformatting the lines, including
 * Override annotations, changing where the cast of an object occurs, and removing what was essentially duplicated 
 * code, I believe that this is the best point to which I can write/edit code at this moment.
 * 
 * Going piece by piece
 * The constructor -- We do create several variables which are accessible across the class which I would normally be
 * opposed to, but as we create a specific instance of them per view, we avoid conflict of changing values across 
 * incorrect workspaces and a user would have to create entirely new methods to change the state of the canvas or
 * animation enough that it breaks the program. That said, the program calls other classes and uses constants to set 
 * up the state of the animation and canvas view based the standard set up. If someone were to change the size of the 
 * canvas, a method would have to be extracted (or change the parameters passed in), but this change would most likely 
 * require a change of the entire layout per our design and so it is best left alone as is by itself.
 * 
 * update -- This method takes the Observables which run our ability to have the front and back ends interact. One change 
 * I made for this was to move the if statement from animatedisplay() to update() such that animated display is only 
 * called if the program is set to animate. Otherwise, animateddisplay() would call on every graphic update even if 
 * the boolean for animation is set to false.
 * 
 * updateBackground() -- this with backgroundChange() that is well closed off to take in the state of the program's 
 * canvas' current size, produce a new modified canvas of new color but same size with the new color added to the 
 * color combo box.
 * 
 * animateDisplay() -- This method drives the set up and conclusion of animation for each input. We applied a 
 * sequential transition as recommended by our TA Rubens to play out the steps of the animation set up in drawObjects()
 * and were able to keep the main turtle in focus and call from other classes. 
 * 
 * drawObjects() -- this method bothered me the most for revisions as I feel there are better ways to rewrite, but I am
 * at my limit with this method. The objects path for animation is set up and any animation is produced here for the 
 * animationModel. I disliked having to run the same calls on both stampAnims and turtleAnims but as the two are different
 * classes with different methods and parameters, we were stuck three commands twice each. In addition, running the for 
 * loop with a lambda expression stumped me how to keep the value of i updating, but that said, this method does what it 
 * says by  setting up the model and view, and then adding all of the animation when animateDisplay() calls on it.
 * 
 * As a whole, I do believe this code well represents what I've learned so far in this class. The edits across the 
 * class are very specific with simple design impacts, but this was code was focused on originally to be my masterpiece
 * and so it was good from the original submission. I know room for improvement remains, but as we proceed on towards 
 * Vooga, I believe that working to focus on design will best impact my code for future development.
 * 
 * Thanks as always for taking the time to analyze my work and I look forward to your feedback.
 */

package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import addons.Features;
import animation.StampAnimation;
import animation.TurtleAnimation;
import constants.UIConstants;
import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.ViewableDisplayModel;

public class DisplayView implements IView {

	private Canvas backgroundCanvas;
	private GraphicsContext backgroundGC;
	private ComboBox<HBox> backgroundColorComboBox;
	private TurtleAnimation turtleAnim;
	private StampAnimation stampAnim;
	private Features features = new Features();
	private List<Animation> animations = new ArrayList<>();
	private int lastExpressionFrameNumber = 1;
	private ResourceBundle myBundle;

	public DisplayView(Group root) {
		myBundle = ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE
				+ UIConstants.SCREEN_LANG);
		this.backgroundCanvas = features.makeCanvas(UIConstants.CANVAS_X,
				UIConstants.BORDER_WIDTH, UIConstants.CANVAS_SIZE,
				UIConstants.CANVAS_SIZE, Color.GREEN);
		this.backgroundColorComboBox = features.makeColorPicker(
				UIConstants.BG_COMBO_X, UIConstants.BG_COMBO_Y,
				UIConstants.COLOR_SELECTOR_WIDTH, UIConstants.BORDER_WIDTH);
		this.backgroundGC = backgroundCanvas.getGraphicsContext2D();
		root.getChildren().addAll(backgroundCanvas, backgroundColorComboBox);
		this.turtleAnim = new TurtleAnimation(root);
		this.stampAnim = new StampAnimation(root);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof ViewableDisplayModel) {
			ViewableDisplayModel displayModel = (ViewableDisplayModel) o;
			updateBackground(displayModel);
			if (displayModel.isToAnimate()) {
				animateDisplay(displayModel, displayModel.getAnimationSpeed());
			}
		}
	}

	@Override
	public void updateBackground(ViewableDisplayModel displayModel) {
		features.updateComboBoxOptions(backgroundColorComboBox,
				displayModel.getColorMap());
		backgroundGC.clearRect(0, 0, backgroundCanvas.getWidth(),
				backgroundCanvas.getHeight());
		backgroundGC.setFill(Color.web(displayModel.getColorMap().get(
				displayModel.getBackgroundColorIndex())));
		backgroundGC.fillRect(0, 0, backgroundCanvas.getWidth(),
				backgroundCanvas.getHeight());
		backgroundColorComboBox.setPromptText(myBundle
				.getString("BackgroundColor")
				+ displayModel.getBackgroundColorIndex());
		backgroundColorComboBox.setOnAction(e -> backgroundChange(displayModel,
				backgroundColorComboBox.getValue()));
	}

	@Override
	public void backgroundChange(ViewableDisplayModel displayModel, HBox box) {
		Text t = (Text) box.getChildren().get(1);
		Double index = (double) Integer.parseInt(t.getText());
		displayModel.setBackgroundColorIndex(index);
	}

	@Override
	public void animateDisplay(ViewableDisplayModel displayModel, int speed) {
			displayModel.setToAnimate(false);
			animations.clear();
			drawObjects(displayModel, speed);
			SequentialTransition st = new SequentialTransition();
			animations.stream().sequential()
					.collect(Collectors.toCollection(() -> st.getChildren()));
			st.play();
			lastExpressionFrameNumber = displayModel.getViewableTurtleList()
					.get(0).getFrameNumber();
	}

	@Override
	public void drawObjects(ViewableDisplayModel displayModel, int speed) {
		turtleAnim.setDisplayModel(displayModel);
		stampAnim.setDisplayModel(displayModel);
		for (int i = lastExpressionFrameNumber; i < displayModel
				.getViewableTurtleList().get(0).getFrameNumber(); i++) {
			turtleAnim.setUpView(i, speed);
			stampAnim.setUpView(i, speed);
			animations.addAll(turtleAnim.generateAnimations(i, speed));
			animations.addAll(stampAnim.generateAnimations(i, speed));
		}
	}
}