package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TurtleModel implements ViewableTurtleModel {
    
	private double initialPenSize = 1;
	private double initialPenColorIndex = 1;
	private double initialPenStyleIndex = 1;
    private double turtleInitialX;
    private double turtleInitialY;
    private double turtleInitialHeading;
    private int frameNumber;
    private Map<Double, String> imageMap;
    private Map<Double, String> colorMap;
    private List<IPenModel> pen = new ArrayList<>();
    private List<Boolean> isActive = new ArrayList<>();
    private List<Double> heading = new ArrayList<>();
    private List<Double> positionX = new ArrayList<>();
    private List<Double> positionY = new ArrayList<>();
    private List<Boolean> showStatus = new ArrayList<>();
    private List<Double> imageIndex = new ArrayList<>();
    private List<List<ILineModel>> lines = new ArrayList<>();
    private List<List<IStampModel>> stamps = new ArrayList<>();

    public TurtleModel (double turtleInitialX, double turtleInitialY, double turtleInitialHeading, 
                        Map<Double, String> imageMap, Map<Double, String> colorMap) {
        this.frameNumber = 2;
        this.turtleInitialHeading = turtleInitialHeading;
        this.turtleInitialX = turtleInitialX;
        this.turtleInitialY = turtleInitialY;
        this.imageMap = imageMap;
        this.colorMap = colorMap;
        addInitialStates(frameNumber);
    }
    
    private void addInitialStates(int num) {
        for(int i = 0; i < num; i++) {
            IPenModel newPen = new PenModel(true, initialPenSize, initialPenColorIndex, initialPenStyleIndex);
            newPen.setColorString(colorMap.get(newPen.getColorIndex()));
            pen.add(newPen);  
            isActive.add(false);
            heading.add(turtleInitialHeading);
            positionX.add(turtleInitialX);
            positionY.add(turtleInitialY);
            showStatus.add(true);
            imageIndex.add(4.0);
            lines.add(new ArrayList<>());
            stamps.add(new ArrayList<>());
        }
    }
    
    public TurtleModel makeNewActiveTurtle() {
        TurtleModel newTurtle = new TurtleModel(this.turtleInitialX, 
                                                this.turtleInitialY, 
                                                this.turtleInitialHeading, 
                                                this.imageMap, 
                                                this.colorMap);
        List<IPenModel> newPen = new ArrayList<>();
        for(IPenModel pen : this.pen) {
            newPen.add(pen.copyPenModel());
        }
        newTurtle.pen = newPen;
        newTurtle.pen.remove(newTurtle.pen.size() - 1);
        newTurtle.pen.remove(newTurtle.pen.size() - 1);
        newTurtle.heading = new ArrayList<>(heading);
        newTurtle.heading.remove(newTurtle.heading.size() - 1);
        newTurtle.heading.remove(newTurtle.heading.size() - 1);
        newTurtle.positionX = new ArrayList<>(positionX);
        newTurtle.positionX.remove(newTurtle.positionX.size() - 1);
        newTurtle.positionX.remove(newTurtle.positionX.size() - 1);
        newTurtle.positionY = new ArrayList<>(positionY);
        newTurtle.positionY.remove(newTurtle.positionY.size() - 1);
        newTurtle.positionY.remove(newTurtle.positionY.size() - 1);
        newTurtle.showStatus = new ArrayList<>(showStatus);
        newTurtle.showStatus.remove(newTurtle.showStatus.size() - 1);
        newTurtle.showStatus.remove(newTurtle.showStatus.size() - 1);
        newTurtle.imageIndex = new ArrayList<>(imageIndex);
        newTurtle.imageIndex.remove(newTurtle.imageIndex.size() - 1);
        newTurtle.imageIndex.remove(newTurtle.imageIndex.size() - 1);
        List<List<ILineModel>> newLines = new ArrayList<>();
        for(List<ILineModel> lineList : this.lines) {
            newLines.add(copyLineList(lineList));
        }
        newTurtle.lines = newLines;
        newTurtle.lines.remove(newTurtle.lines.size() - 1);
        newTurtle.lines.remove(newTurtle.lines.size() - 1);
        List<List<IStampModel>> newStamps = new ArrayList<>();
        for(List<IStampModel> stampList : this.stamps) {
            newStamps.add(copyStampList(stampList));
        }
        newTurtle.stamps = newStamps;
        newTurtle.stamps.remove(newTurtle.stamps.size() - 1);
        newTurtle.stamps.remove(newTurtle.stamps.size() - 1);
        newTurtle.addInitialStates(2);
        newTurtle.frameNumber = this.frameNumber;
        return newTurtle;
    }
    
    public void syncFrame() {
        frameNumber++;
        
        if(pen.size() != frameNumber) {
            pen.add(getPen().copyPenModel());
        }
        if(heading.size() != frameNumber) {
            heading.add(getHeading());
        }
        if(positionX.size() != frameNumber) {
            positionX.add(getPositionX());
        }
        if(positionY.size() != frameNumber) {
            positionY.add(getPositionY());
        }
        if(showStatus.size() != frameNumber) {
            showStatus.add(isShowStatus());
        }
        if(imageIndex.size() != frameNumber) {
            imageIndex.add(getImageIndex());
        }
        if(lines.size() != frameNumber) {
            lines.add(copyLineList(getLineList()));
        }
        if(stamps.size() != frameNumber) {
            stamps.add(copyStampList(getStampList()));
        }
        if(isActive.size() != frameNumber) {
            isActive.add(isActive());
        }
    }
    
    public List<ILineModel> copyLineList(List<ILineModel> lineList) {
        List<ILineModel> newLM = new ArrayList<>();
        for(ILineModel lm : lineList) {
            newLM.add(lm.copyLineModel());
        }
        return newLM;
    }
    
    public List<IStampModel> copyStampList(List<IStampModel> stampList) {
        List<IStampModel> newSM = new ArrayList<IStampModel>();
        for(IStampModel sm : stampList) {
            newSM.add(sm.copyStampModel());
        }
        return newSM;
    }

    public double forward (double[] distance) {
        IPenModel lastPen = getPen ();
        if(lastPen.getStatus()) {
                List<ILineModel> nextLM = copyLineList(getLineList());
                nextLM.add(new LineModel(getPositionX(), getPositionY(), 
                                getPositionX()  + distance[0] * Math.cos(Math.toRadians(getHeading())), 
                                getPositionY() + distance[0] * Math.sin(Math.toRadians(getHeading())),
                                lastPen.getSize(), lastPen.getColorString(), lastPen.getStyle()));
                lines.add(nextLM);
        }
        positionX.add(getPositionX() + distance[0] * Math.cos(Math.toRadians(getHeading())));
        positionY.add(getPositionY() + distance[0] * Math.sin(Math.toRadians(getHeading())));
        return distance[0];
    }
    
    public double backward (double[] distance) {
        distance[0] = -distance[0];
        return forward(distance);
    }

    public double setHeading (double[] degree) {
        heading.add(degree[0]);
        return degree[0];
    }

    public double turnRight (double[] degree) {
        heading.add(getHeading() - degree[0]);
        return degree[0];
    }

    public double turnLeft (double[] degree) {
        degree[0] = -degree[0];
        return turnRight(degree);
    }

    public double penUp () {
        IPenModel newPen = pen.get(pen.size()-1).copyPenModel();
        newPen.setStatus(false);
        pen.add(newPen);
        return 0;
    }
    
    public double penDown () {
        IPenModel newPen = pen.get(pen.size()-1).copyPenModel();
        newPen.setStatus(true);
        pen.add(newPen);
        return 1;
    }
    
    public double stamp () {
        List<IStampModel> nextSM = copyStampList(getStampList());
        nextSM.add(new StampModel(imageMap.get(getImageIndex()), getPositionX(), getPositionY(), getHeading()));
        stamps.add(nextSM);
        return getLastDouble(imageIndex);
    }
    
    public double show () {
        showStatus.add(true);
        return 1;
    }
    
    public double hide () {
        showStatus.add(false);
        return 0;
    }
    
    public double home () {
        return setPosition(new double[] {0, 0});
    }
    
    public double setPosition(double[] xy) {
        double[] oldPos = new double[]{getPositionX(), getPositionY()};
        positionX.add(xy[0]);
        positionY.add(xy[1]);
        IPenModel lastPen = getPen ();
        if(lastPen.getStatus()) {
                List<ILineModel> nextLM = copyLineList(getLineList());
                nextLM.add(new LineModel(oldPos[0], oldPos[1], getPositionX(), getPositionY(),
                                lastPen.getSize(), lastPen.getColorString(), lastPen.getStyle()));
                lines.add(nextLM);
        }
        return Math.sqrt(Math.pow((oldPos[0] - getPositionX()), 2) +
                         Math.pow((oldPos[1] - getPositionY()), 2));
    }
    
    public double setTowards(double[] xy) {
        double lastHeading = getHeading();
        if(xy[0] == getPositionX() && xy[1] == getPositionY()) {
            return 0;
        }
        double rawDegrees = Math.toDegrees(Math.atan((xy[1] - getPositionY()) / 
                                                     (xy[0] - getPositionX())));
        double newHeading = xy[0] - getPositionX() >= 0 ? rawDegrees : rawDegrees - 180;
        heading.add(newHeading);
        double headingDiff = Math.abs(lastHeading - getHeading());
        return headingDiff >= 180 ? 360 - headingDiff : headingDiff;
    }
   
    public double clearScreen() {
        clearStamps();
        lines.add(new ArrayList<>());
        double[] oldPos = new double[]{getPositionX(), getPositionY()};
        positionX.add(0.0);
        positionY.add(0.0);
        return Math.sqrt(Math.pow((oldPos[0] - getPositionX()), 2) +
                         Math.pow((oldPos[1] - getPositionY()), 2));
    }
    
    public double clearStamps() {
        if(getNumStamps() > 0) {
            stamps.add(new ArrayList<>());
            return stamps.get(stamps.size()-2).size();
        }
        return 0;
    }
        
    public double setPenColorIndex (double[] penColorIndex) {
        IPenModel newPen = getPen().copyPenModel();
        newPen.setColorIndex(penColorIndex[0]);
        newPen.setColorString(colorMap.get(penColorIndex[0]));
        pen.add(newPen);
        return penColorIndex[0];
    }
    
    public double setPenStyleIndex (double[] penStyleIndex) {
        IPenModel newPen = getPen().copyPenModel();
        newPen.setStyleIndex(penStyleIndex[0]);
        pen.add(newPen);
        return penStyleIndex[0];
    }
    
    public double setImageIndex (double[] imageIndex) {
        this.imageIndex.add(imageIndex[0]);
        return imageIndex[0];
    }

    public double setLineWidth (double[] lineWidth) {
        IPenModel newPen = getPen().copyPenModel();
        newPen.setSize(lineWidth[0]);
        pen.add(newPen);
        return lineWidth[0];
    }
    
    public int getFrameNumber() {
        return frameNumber;
    }
    
    public int getNumStamps() {
        return getStampList().size();
    }   
    
    public void setImageMap(Map<Double, String> imageMap) {
        this.imageMap = imageMap;
    }
    
    public void setColorMap(Map<Double, String> colorMap) {
        this.colorMap = colorMap;
    }
    
    public double getShowStatus (int frameNumber) {
        return showStatus.get(frameNumber) ? 1 : 0;
    }
      
    public double getPositionY () {
        return getLastDouble(positionY);
    }
    
    public double getPositionY (int frameNumber) {
        return positionY.get(frameNumber);
    }
    
    public double getPositionX () {
        return getLastDouble(positionX);
    }
    
    public double getPositionX (int frameNumber) {
        return positionX.get(frameNumber);
    }
    
    public double getHeading () {
        return getLastDouble(heading);
    }
    
    public double getHeading(int frameNumber) {
        return heading.get(frameNumber);
    }
    
    public IPenModel getPen () {
        return pen.get(pen.size()-1);
    }
    
    public IPenModel getPen (int frameNumber) {
        return pen.get(frameNumber);
    }

    public boolean isPenStatus () {
        return getPen().getStatus();
    }

    public boolean isShowStatus() {
        return getLastBoolean(showStatus);
    }

    public double getPenColorIndex () {
        return getPen().getColorIndex();
    }
    
    public double getImageIndex () {
        return getLastDouble(imageIndex);
    }
    
    public String getImageString (int frameNumber) {
        return imageMap.get(imageIndex.get(frameNumber));
    }
    
    public double getLineWidth () {
        return getPen().getSize();
    }
    
    public void setActive (boolean isActive) {
        this.isActive.add(isActive);
    }  
    
    public boolean isActive (int frameNumber) {
        return isActive.get(frameNumber);
    }
    
    public boolean isActive () {
        return getLastBoolean(isActive);
    }
    
    public List<ILineModel> getLineList(int frameNumber) {
        return lines.get(frameNumber);
    }
    
    public List<IStampModel> getStampList(int frameNumber) {
        return stamps.get(frameNumber);
    }
    
    public List<ILineModel> getLineList() {
        return lines.get(lines.size()-1);
    }
    
    public List<IStampModel> getStampList() {
        return stamps.get(stamps.size()-1);
    }    
    
    public double getLastDouble(List<Double> list) {
        return list.get(list.size() - 1).doubleValue();
    }
    
    public boolean getLastBoolean(List<Boolean> list) {
        return list.get(list.size() - 1).booleanValue();
    }
}