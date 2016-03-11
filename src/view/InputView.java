package view;

import java.util.Observable;

import addons.Features;
import constants.UIConstants;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import parser.CommandParser;

public class InputView extends View{

	public TextArea inputText;
	public CommandParser myParser;
	public Features featureMaker;
	
	public InputView(CommandParser p, TextArea myIT) {
		myParser = p;
		featureMaker = new Features();
		getMyRoot().setLayoutX(UIConstants.RECT_W);
		getMyRoot().setLayoutY(UIConstants.LOWER_PANE_Y);
		inputText = myIT;
		inputText.setMinSize(UIConstants.LOWER_PANE_WIDTH, 120);
		inputText.setMaxSize(UIConstants.LOWER_PANE_WIDTH, 120);
		getMyBox().getChildren().add(inputText);
		Button inputButton = featureMaker.makeB("GO",
				event -> readInput());
		inputButton.setMinWidth(UIConstants.LOWER_PANE_WIDTH);
		getMyBox().getChildren().add(inputButton);
		
	}
	
	private void readInput() {
		myParser.parseText(inputText.getText());
		inputText.clear();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	public TextArea getMyTA(){
		return inputText;
	}
}
