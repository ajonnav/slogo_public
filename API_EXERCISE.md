# API Critique:

## public abstract class Cell

  	public Cell(int x, int y, int[] neighboringX, int[] neighboringY, int currentState, IEdgeCase edgeType, Map<Integer, String> colorMap,  Map<String, Double> parametersMap)
  	
	public Cell() 
	
	public int getPPValue(int index) -- Should be refactored to be only in Wator
	
	public void setPPValue(int index, int value) -- Should be refactored to be only in Wator
	
	public List<Cell> getNeighbors(Cell[][] cellGrid, int[] neighboringX, int[] neighboringY) -- Internal API
	
    public abstract void updateCell() -- Internal API
    
    public Color getColor() -- External API
    
    public void updateNextState() -- External API
    
    public void setNextState(int state) -- External API
    
    public void setGrid(Cell[][] cellGrid) -- External
    
    public void setState(int state) -- External
    
    public int getState() -- External
    
    public Cell[][] getCellGrid() -- External
    
    public int[] getXNeighbors() -- Internal
    
    public int[] getYNeighbors() -- Internal
    
    public void setX(int x) -- Internal
    
    public void setY(int y) -- Internal
    
    public int getX() -- Internal
    
    public int getY() -- Internal
    
    public void printf(Cell[][] grid) -- Should not be here
    
	public void copyGridToNextGrid(Cell[][] cellGrid) -- Internal
	
	public int getNextState() -- Internal
	
	public void setNextCellGrid(Cell[][] nextCellGrid) -- Internal
	
	public Cell[][] getNextCellGrid() -- Internal

## public class SegCell extends Cell implements Cloneable

  	public SegCell(int x, int y, int[] neighboringX, int[] neighboringY, int state, IEdgeCase edgeType, Map<Integer, String> colorMap, HashMap<String, Double> parametersMap) 
  	
	public SegCell() 
	
	public void updateCell() 
	
	public boolean isHappy() -- Should not be part of API

## public class SimulationModel 

  	public SimulationModel() 
  	
	public Cell[][] normalLoad() -- This should be private
	
	public Cell[][] randomLoad() -- This should be private
	
	public Cell[][] load(int[][] gridStates) -- External
	
	public ResourceBundle getErrorResources() -- This should be private
	
	public Cell[][] toggleCell(int x, int y) -- External
	
	public boolean hasLoaded() -- Internal
	
    public Cell[][] step() -- External
    
    public int[][] getGridStates() -- Internal
    
    public int numElements() -- Should be private
    
    public Map<Integer, Integer> getStateCounts() -- External
    
    public boolean getDataFromXMLFile(File file) -- External
    
    public String getTitle() -- External
    
	public Map<Integer, String> getColorMap() -- External
	

## public class SimulationView
      	
      					public void handle(MouseEvent e) 
      					
    public Scene getScene () -- External
    
