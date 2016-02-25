package display;

import addons.CBox;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class Splash extends Screen {
	
	private CBox languageCBox;

	@Override
	public void setUpScene() {
		// TODO Auto-generated method stub
		setScene(new Scene(getRoot(), 1000, 500, Color.WHITE));
		
		setLangBox();
		
		setButton();
	}
	
	private void setButton() {
		// TODO Auto-generated method stub
		
	}

	private void setLangBox(){
		languageCBox = new CBox();
		getRoot().getChildren().add(languageCBox.myCBox);
	}
	
	
	private void addBoxElem(){
		
	}
}
