package view;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
 
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.HistoryPaneModel;
 
public class HistoryPaneView implements IView{
	 
	public List<String> myHist;
	public VBox myVBox;
 	
 	public HistoryPaneView(VBox vb) {
 		myHist = new ArrayList<>();
 		myVBox = vb;
 	}
 
 	@Override
 	public void update(Observable o, Object arg) {
 		if(o instanceof HistoryPaneModel){
 			HistoryPaneModel hpm = (HistoryPaneModel) o;
 			myHist = hpm.getImmutableHistoryList();
 			for(String item : myHist) myVBox.getChildren().add(new Text(item));
 		}
 		
 	}
 
}