// This entire file is part of my masterpiece.
// Michael Kuryshev

/**
 * Finding this interface to be entirely empty upon project completion, I felt compelled to
 * insert actual methods for the DisplayView to implement. Originally, this class merely extended 
 * Observer and that alone was why this class was implemented. This interface now bears a real 
 * purpose and can conveniently be used with any future view classes requiring animation, drawing, 
 * or updates. That said, the reason other classes did not implement this interface was because they 
 * already had code to implement scrollView which focused on statistic display instead of 
 * drawings. That said, if we chose to include everything in a single interface (doubtful though), 
 * we would just have to add the scrollView methods into this interface and either abstract 
 * implementing classes if we choose to not use every method in the interface or to include one line 
 * empty overrides to avoid issues. As a whole, adding methods to this class creates a contract 
 * between classes that improves readability and allows for calling various methods cross class.
 */

package view;

import java.util.Observable;
import java.util.Observer;
import javafx.scene.layout.HBox;
import model.ViewableDisplayModel;

interface IView extends Observer{
	
	void update(Observable o, Object arg);
	
	void updateBackground (ViewableDisplayModel displayModel);
	
	void backgroundChange (ViewableDisplayModel displayModel, HBox box);
	
	void animateDisplay (ViewableDisplayModel displayModel, int speed);
	
	void drawObjects (ViewableDisplayModel displayModel, int speed);
}
