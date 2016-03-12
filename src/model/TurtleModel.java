package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Set<SyncableListModel> syncableSet;
    private SyncableListModel<IPenModel> penList;
    private SyncableListModel<Boolean> activeListModel;
    private SyncableListModel<Double> heading;
    private SyncableListModel<Double> positionX;
    private SyncableListModel<Double> positionY;
    private SyncableListModel<Boolean> showStatus;
    private SyncableListModel<Double> imageIndex;
    private SyncableListModel<List<ILineModel>> lines;
    private SyncableListModel<List<IStampModel>> stamps;

    public TurtleModel (double turtleInitialX,
                        double turtleInitialY,
                        double turtleInitialHeading,
                        Map<Double, String> imageMap,
                        Map<Double, String> colorMap) {
        this.frameNumber = 2;
        this.turtleInitialHeading = turtleInitialHeading;
        this.turtleInitialX = turtleInitialX;
        this.turtleInitialY = turtleInitialY;
        this.imageMap = imageMap;
        this.colorMap = colorMap;
        syncableSet = new HashSet<>();
        this.penList = new SyncableListModel<>();
        this.activeListModel = new SyncableListModel<>();
        this.heading = new SyncableListModel<>();
        this.positionX = new SyncableListModel<>();
        this.positionY = new SyncableListModel<>();
        this.showStatus = new SyncableListModel<>();
        this.imageIndex = new SyncableListModel<>();
        this.lines = new SyncableListModel<>();
        this.stamps = new SyncableListModel<>();
        syncableSet.add(penList);
        syncableSet.add(activeListModel);
        syncableSet.add(heading);
        syncableSet.add(positionX);
        syncableSet.add(positionY);
        syncableSet.add(showStatus);
        syncableSet.add(imageIndex);
        syncableSet.add(lines);
        syncableSet.add(stamps);
        addInitialStates(frameNumber);
    }

    private void addInitialStates (int num) {
        for (int i = 0; i < num; i++) {
            IPenModel newPen =
                    new PenModel(true, initialPenSize, initialPenColorIndex, initialPenStyleIndex);
            newPen.setColorString(colorMap.get(newPen.getColorIndex()));
            penList.add(newPen);  
            activeListModel.add(false);
            heading.add(turtleInitialHeading);
            positionX.add(turtleInitialX);
            positionY.add(turtleInitialY);
            showStatus.add(true);
            imageIndex.add(4.0);
            lines.add(new ArrayList<>());
            stamps.add(new ArrayList<>());
        }
    }

    public TurtleModel makeNewActiveTurtle () {
        TurtleModel newTurtle = new TurtleModel(this.turtleInitialX,
                                                this.turtleInitialY,
                                                this.turtleInitialHeading,
                                                this.imageMap,
                                                this.colorMap);
        SyncableListModel<IPenModel> newPen = new SyncableListModel<>();
        for(IPenModel pen : this.penList.getList()) {
            newPen.add(pen.copyPenModel());
        }
        
        newTurtle.penList = newPen;
        newTurtle.penList.remove(newTurtle.penList.size() - 1);
        newTurtle.penList.remove(newTurtle.penList.size() - 1);
        newTurtle.heading = new SyncableListModel<>(heading);
        newTurtle.heading.remove(newTurtle.heading.size() - 1);
        newTurtle.heading.remove(newTurtle.heading.size() - 1);
        newTurtle.positionX = new SyncableListModel<>(positionX);
        newTurtle.positionX.remove(newTurtle.positionX.size() - 1);
        newTurtle.positionX.remove(newTurtle.positionX.size() - 1);
        newTurtle.positionY = new SyncableListModel<>(positionY);
        newTurtle.positionY.remove(newTurtle.positionY.size() - 1);
        newTurtle.positionY.remove(newTurtle.positionY.size() - 1);
        newTurtle.showStatus = new SyncableListModel<>(showStatus);
        newTurtle.showStatus.remove(newTurtle.showStatus.size() - 1);
        newTurtle.showStatus.remove(newTurtle.showStatus.size() - 1);
        newTurtle.imageIndex = new SyncableListModel<>(imageIndex);
        newTurtle.imageIndex.remove(newTurtle.imageIndex.size() - 1);
        newTurtle.imageIndex.remove(newTurtle.imageIndex.size() - 1);
        
        SyncableListModel<List<ILineModel>> newLines = new SyncableListModel<>();
        for(List<ILineModel> lineList : this.lines) {
            newLines.add(copyLineList(lineList));
        }
        newTurtle.lines = newLines;
        newTurtle.lines.remove(newTurtle.lines.size() - 1);
        newTurtle.lines.remove(newTurtle.lines.size() - 1);
        
        SyncableListModel<List<IStampModel>> newStamps = new SyncableListModel<>();
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

    public void syncFrame () {
        frameNumber++;
        syncableSet.stream().forEach(s->s.sync(frameNumber));
    }

    public List<ILineModel> copyLineList (List<ILineModel> lineList) {
        List<ILineModel> newLM = new ArrayList<>();
        for (ILineModel lm : lineList) {
            newLM.add(lm.copyLineModel());
        }
        return newLM;
    }

    public List<IStampModel> copyStampList (List<IStampModel> stampList) {
        List<IStampModel> newSM = new ArrayList<IStampModel>();
        for (IStampModel sm : stampList) {
            newSM.add(sm.copyStampModel());
        }
        return newSM;
    }

    public double forward (double[] distance) {
        IPenModel lastPen = penList.get(penList.size()-1);
        if (lastPen.getStatus()) {
            List<ILineModel> nextLM = copyLineList(getLineList());
            nextLM.add(new LineModel(getPositionX(), getPositionY(),
                                     getPositionX() + distance[0] *
                                                      Math.cos(Math.toRadians(getHeading())),
                                     getPositionY() + distance[0] *
                                                      Math.sin(Math.toRadians(getHeading())),
                                     lastPen.getSize(), lastPen.getColorString(),
                                     lastPen.getStyle()));
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
        IPenModel newPen = penList.get(penList.size()-1).copyPenModel();
        newPen.setStatus(false);
        penList.add(newPen);
        return 0;
    }

    public double penDown () {
        IPenModel newPen = penList.get(penList.size()-1).copyPenModel();
        newPen.setStatus(true);
        penList.add(newPen);
        return 1;
    }

    public double stamp () {
        List<IStampModel> nextSM = copyStampList(getStampList());
        nextSM.add(new StampModel(imageMap.get(getImageIndex()), getPositionX(), getPositionY(),
                                  getHeading()));
        stamps.add(nextSM);
        return imageIndex.get(imageIndex.size()-1);
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
        return setPosition(new double[] { 0, 0 });
    }

    public double setPosition (double[] xy) {
        double[] oldPos = new double[] { getPositionX(), getPositionY() };
        positionX.add(xy[0]);
        positionY.add(xy[1]);
        IPenModel lastPen = penList.get(penList.size()-1);
        if (lastPen.getStatus()) {
            List<ILineModel> nextLM = copyLineList(getLineList());
            nextLM.add(new LineModel(oldPos[0], oldPos[1], getPositionX(), getPositionY(),
                                     lastPen.getSize(), lastPen.getColorString(),
                                     lastPen.getStyle()));
            lines.add(nextLM);
        }
        return Math.sqrt(Math.pow((oldPos[0] - getPositionX()), 2) +
                         Math.pow((oldPos[1] - getPositionY()), 2));
    }

    public double setTowards (double[] xy) {
        double lastHeading = getHeading();
        if (xy[0] == getPositionX() && xy[1] == getPositionY()) {
            return 0;
        }
        double rawDegrees = Math.toDegrees(Math.atan((xy[1] - getPositionY()) /
                                                     (xy[0] - getPositionX())));
        double newHeading = xy[0] - getPositionX() >= 0 ? rawDegrees : rawDegrees - 180;
        heading.add(newHeading);
        double headingDiff = Math.abs(lastHeading - getHeading());
        return headingDiff >= 180 ? 360 - headingDiff : headingDiff;
    }

    public double clearScreen () {
        clearStamps();
        lines.add(new ArrayList<>());
        double[] oldPos = new double[] { getPositionX(), getPositionY() };
        positionX.add(0.0);
        positionY.add(0.0);
        return Math.sqrt(Math.pow((oldPos[0] - getPositionX()), 2) +
                         Math.pow((oldPos[1] - getPositionY()), 2));
    }

    public double clearStamps () {
        if (getNumStamps() > 0) {
            stamps.add(new ArrayList<>());
            return stamps.get(stamps.size() - 2).size();
        }
        return 0;
    }

    public double setPenColorIndex (double[] penColorIndex) {
        IPenModel newPen = penList.get(penList.size()-1).copyPenModel();
        newPen.setColorIndex(penColorIndex[0]);
        newPen.setColorString(colorMap.get(penColorIndex[0]));
        penList.add(newPen);
        return penColorIndex[0];
    }

    public double setPenStyleIndex (double[] penStyleIndex) {
        IPenModel newPen = penList.get(penList.size()-1).copyPenModel();
        newPen.setStyleIndex(penStyleIndex[0]);
        penList.add(newPen);
        return penStyleIndex[0];
    }

    public double setImageIndex (double[] imageIndex) {
        this.imageIndex.add(imageIndex[0]);
        return imageIndex[0];
    }

    public double setLineWidth (double[] lineWidth) {
        IPenModel newPen = penList.get(penList.size()-1).copyPenModel();
        newPen.setSize(lineWidth[0]);
        penList.add(newPen);
        return lineWidth[0];
    }

    public int getFrameNumber () {
        return frameNumber;
    }

    public int getNumStamps () {
        return getStampList().size();
    }

    public void setImageMap (Map<Double, String> imageMap) {
        this.imageMap = imageMap;
    }

    public void setColorMap (Map<Double, String> colorMap) {
        this.colorMap = colorMap;
    }

    public double getShowStatus (int frameNumber) {
        return showStatus.get(frameNumber) ? 1 : 0;
    }

    public double getPositionY () {
        return positionY.get(positionY.size()-1);
    }

    public double getPositionY (int frameNumber) {
        return positionY.get(frameNumber);
    }

    public double getPositionX () {
        return positionX.get(positionX.size()-1);
    }

    public double getPositionX (int frameNumber) {
        return positionX.get(frameNumber);
    }

    public double getHeading () {
        return heading.get(heading.size()-1);
    }

    public double getHeading (int frameNumber) {
        return heading.get(frameNumber);
    }

    public IPenModel getPen () {
        return penList.get(penList.size()-1);
    }

    public IPenModel getPen (int frameNumber) {
        return penList.get(frameNumber);
    }

    public ViewablePenModel getViewablePen (int frameNumber) {
        return penList.get(frameNumber);
    }

    public ViewablePenModel getViewablePen () {
        return penList.get(penList.size() - 1);
    }

    public boolean isPenStatus () {
        return penList.get(penList.size()-1).getStatus();
    }

    public boolean isShowStatus() {
        return showStatus.get(showStatus.size()-1);
    }

    public double getPenColorIndex () {
        return penList.get(penList.size()-1).getColorIndex();
    }

    public double getImageIndex () {
        return imageIndex.get(imageIndex.size()-1);
    }

    public String getImageString (int frameNumber) {
        return imageMap.get(imageIndex.get(frameNumber));
    }

    public double getLineWidth () {
        return penList.get(penList.size()-1).getSize();
    }

    public void setActive (boolean isActive) {
        this.activeListModel.add(isActive);
    }
    
    public boolean isActive (int frameNumber) {
        return activeListModel.get(frameNumber);
    }

    public boolean isActive () {
        return activeListModel.get(activeListModel.size()-1);
    }

    public List<ILineModel> getLineList (int frameNumber) {
        return lines.get(frameNumber);
    }

    public List<ViewableLineModel> getViewableLineList (int frameNumber) {
        return lines.get(frameNumber).stream()
                .map(t -> (ViewableLineModel) t)
                .collect(Collectors.toList());
    }
    
    public List<ViewableLineModel> getViewableLineList () {
        return lines.get(lines.size()-1).stream()
                .map(t -> (ViewableLineModel) t)
                .collect(Collectors.toList());
    }

    public List<IStampModel> getStampList (int frameNumber) {
        return stamps.get(frameNumber);
    }
    
    public List<ViewableStampModel> getViewableStampList (int frameNumber) {
        return stamps.get(frameNumber).stream()
                .map(t -> (ViewableStampModel) t)
                .collect(Collectors.toList());
    }
    
    public List<ViewableStampModel> getViewableStampList () {
        return stamps.get(stamps.size()-1).stream()
                .map(t -> (ViewableStampModel) t)
                .collect(Collectors.toList());
    }

    public List<ILineModel> getLineList () {
        return lines.get(lines.size() - 1);
    }

    public List<IStampModel> getStampList () {
        return stamps.get(stamps.size() - 1);
    }

    public double getLastDouble (List<Double> list) {
        return list.get(list.size() - 1).doubleValue();
    }

    public boolean getLastBoolean (List<Boolean> list) {
        return list.get(list.size() - 1).booleanValue();
    }
}
