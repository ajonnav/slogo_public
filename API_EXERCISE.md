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
    
# Simplified Description of API's

## Visualization:

Internal - User interface, set up of simulation

External - Getting values/properties from the simulation

## Configuration:

Internal - Reading in the XML file and setting up the object that holds the XML values

External - Retreiving values from the object that holds the parsed XML values

## Simulation

Internal -- Changing the rules of the simulation

External -- Broadcasting the current state of the simulation


## Architecture

Slogo Architecture Design

1. When does parsing need to take place and what does it need to start properly?

Upon pressing the enter button on the user interface, the view's event handler will call a method to parse the input in the text field and so long as some value is typed into the field, it will be parsed.

2. What is the result of parsing and who receives it?

Once the parser has received the input stream, it will read the commands by the SLogo language and pass the results to another class to perform the appropriate actions.

3. When are errors detected and how are they reported?

Whenever the user input is pressed on a blank entry or if the parser cannot decipher the input, then the program will cancel the input and display what went wrong to the user. 

4. What do commands know, when do they know it, and how do they get it?

Commands each know how to take in their input values from the backend reader once the reader calls them, once the inputs are received, then the command method will process the input and return the the corresponding action which will be passed to the backend command processor to pass the result to the front end.

5. How is the GUI updated after a command has completed execution?

Once the command returns, the resulting action will be passed to the backend processor which will
perform the action in the front end of the program.
