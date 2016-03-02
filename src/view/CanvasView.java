package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//not used for now, just an option
public class CanvasView extends Canvas{
	
	private Canvas canvas;
	
	public CanvasView(int xLoc, int yLoc, int width, int height, Color color){
		setSize(width, height);
		setColor(xLoc, yLoc, width, height, color);
	}
	
	private void setSize(int width, int height){
		canvas = new Canvas(width, height);
	}
	
	public void setColor(int xLoc, int yLoc, int width, int height, Color color){
		GraphicsContext GC = canvas.getGraphicsContext2D();
		GC.setFill(color);
		GC.fillRect(xLoc,yLoc,width,height);
	}
	
	public void clearCanvas(){
		
	}
	
	public void update(){
		
	}
}
