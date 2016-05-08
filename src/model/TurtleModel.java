package model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import exception.SLogoException;
import constants.UIConstants;


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
    private Set<ISyncableListModel> syncableSet = new HashSet<>();
    private ISyncableListModel<AbstractPenModel> penList = new SyncableListModel<>();
    private ISyncableListModel<Boolean> activeListModel = new SyncableListModel<>();
    private ISyncableListModel<Double> heading = new SyncableListModel<>();
    private ISyncableListModel<Double> positionX = new SyncableListModel<>();
    private ISyncableListModel<Double> positionY = new SyncableListModel<>();
    private ISyncableListModel<Boolean> showStatus = new SyncableListModel<>();
    private ISyncableListModel<Double> imageIndex = new SyncableListModel<>();
    private ISyncableListModel<List<ILineModel>> lines = new SyncableListModel<>();
    private ISyncableListModel<List<IStampModel>> stamps = new SyncableListModel<>();
    private ISyncableListModel<Integer> windowBehaviour = new SyncableListModel<>();
    List<String> newTurtleFieldList =
            new ArrayList<>(Arrays.asList(new String[] { "heading", "positionX", "positionY",
                                                         "showStatus", "imageIndex" }));
    private ResourceBundle errorBundle =
            ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + UIConstants.ERRORS);
    private ResourceBundle windowBundle =
            ResourceBundle.getBundle(UIConstants.DEFAULT_RESOURCE + UIConstants.WINDOW_BUNDLE);

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
            AbstractPenModel newPen =
                    new PenModel(true, initialPenSize, initialPenColorIndex, initialPenStyleIndex);
            newPen.setColorString(colorMap.get(newPen.getColorIndex()));
            penList.add(newPen);
            activeListModel.add(false);
            heading.add(turtleInitialHeading);
            showStatus.add(true);
            imageIndex.add(4.0);
            lines.add(new ArrayList<>());
            stamps.add(new ArrayList<>());
            windowBehaviour.add(UIConstants.DEFAULT_WINDOW_BEHAVIOUR);
            changeXY(turtleInitialX, turtleInitialY);
        }
    }

    public TurtleModel makeNewActiveTurtle () {
        TurtleModel newTurtle = new TurtleModel(this.turtleInitialX,
                                                this.turtleInitialY,
                                                this.turtleInitialHeading,
                                                this.imageMap,
                                                this.colorMap);
        setPenList(newTurtle);
        newTurtleFieldList.stream().forEach(item -> {
            try {
                Field field = getClass().getDeclaredField(item);
                Type t = field.getGenericType();
                field.set(newTurtle, new SyncableListModel((SyncableListModel) field.get(this)));
            }
            catch (Exception e) {
                throw new SLogoException(errorBundle.getString("ReflectionError"));
            }
        });
        setLineList(newTurtle);
        setStampList(newTurtle);
        newTurtle.addInitialStates(2);
        newTurtle.frameNumber = this.frameNumber;
        return newTurtle;
    }

    private void setStampList (TurtleModel newTurtle) {
        ISyncableListModel<List<IStampModel>> newStamps = new SyncableListModel<>();
        for (List<IStampModel> stampList : this.stamps) {
            newStamps.add(copyStampList(stampList));
        }
        newTurtle.stamps = newStamps;
        newTurtle.stamps.remove(newTurtle.stamps.size() - 1);
        newTurtle.stamps.remove(newTurtle.stamps.size() - 1);
    }

    private void setLineList (TurtleModel newTurtle) {
        ISyncableListModel<List<ILineModel>> newLines = new SyncableListModel<>();
        for (List<ILineModel> lineList : this.lines) {
            newLines.add(copyLineList(lineList));
        }
        newTurtle.lines = newLines;
        newTurtle.lines.remove(newTurtle.lines.size() - 1);
        newTurtle.lines.remove(newTurtle.lines.size() - 1);
    }

    private void setPenList (TurtleModel newTurtle) {
        ISyncableListModel<AbstractPenModel> newPen = new SyncableListModel<>();
        for (AbstractPenModel pen : this.penList.getList()) {
            newPen.add(pen.copyPenModel());
        }
        newTurtle.penList = newPen;
        newTurtle.penList.remove(newTurtle.penList.size() - 1);
        newTurtle.penList.remove(newTurtle.penList.size() - 1);
    }

    public void syncFrame () {
        frameNumber++;
        syncableSet.stream().forEach(s -> s.sync(frameNumber));
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
        AbstractPenModel lastPen = penList.get(penList.size() - 1);
        if (lastPen.getStatus()) {
            List<ILineModel> nextLM = copyLineList(getLineList(frameNumber - 1));
            nextLM.add(new LineModel(getPositionX(frameNumber - 2), getPositionY(frameNumber - 2),
                                     getPositionX(frameNumber - 1), getPositionY(frameNumber - 1),
                                     lastPen.getSize(), lastPen.getColorString(),
                                     lastPen.getStyle()));
            lines.add(nextLM);
        }
        changeXY(getPositionX(frameNumber - 1) +
                      distance[0] * Math.cos(Math.toRadians(getHeading(frameNumber - 1))), getPositionY(frameNumber - 1) +
                      distance[0] * Math.sin(Math.toRadians(getHeading(frameNumber - 1))));
        return distance[0];
    }

    private void changeXY(double newX, double newY) {
		String command = windowBundle.getString(windowBehaviour.get(windowBehaviour.size()-1).toString());
		try {
			this.getClass().getDeclaredMethod(command, double.class, double.class)
			.invoke(this, newX, newY);
		} 
		catch (Exception e) {
            throw new SLogoException(errorBundle.getString("ReflectionError"));
        }
	}
    
    private void changeWindow(double newX, double newY) {
    	positionX.add(newX);
    	positionY.add(newY);
    	
    }
    
    private void changeFence(double newX, double newY) {
    	if(Math.abs(newX)>UIConstants.CANVAS_SIZE/2) {
    		newX = Math.signum(newX)*UIConstants.CANVAS_SIZE/2;
    	}
    	if(Math.abs(newY)>UIConstants.CANVAS_SIZE/2) {
    		newY = Math.signum(newY)*UIConstants.CANVAS_SIZE/2;
    	}
    	changeWindow(newX, newY);
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
        heading.add(getHeading(frameNumber - 1) - degree[0]);
        return degree[0];
    }

    public double turnLeft (double[] degree) {
        degree[0] = -degree[0];
        return turnRight(degree);
    }

    public double penUp () {
        AbstractPenModel newPen = penList.get(penList.size() - 1).copyPenModel();
        newPen.setStatus(false);
        penList.add(newPen);
        return 0;
    }

    public double penDown () {
        AbstractPenModel newPen = penList.get(penList.size() - 1).copyPenModel();
        newPen.setStatus(true);
        penList.add(newPen);
        return 1;
    }

    public double stamp () {
        List<IStampModel> nextSM = copyStampList(getStampList(frameNumber - 1));
        nextSM.add(new StampModel(imageMap.get(getImageIndex()), getPositionX(frameNumber - 1),
                                  getPositionY(frameNumber - 1),
                                  getHeading(frameNumber - 1)));
        stamps.add(nextSM);
        return imageIndex.get(imageIndex.size() - 1);
    }
    
    public double fence() {
    	windowBehaviour.add(UIConstants.FENCE);
    	return UIConstants.FENCE;
    }
    
    public double window() {
    	windowBehaviour.add(UIConstants.WINDOW);
    	return UIConstants.WINDOW;
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
        double[] oldPos =
                new double[] { getPositionX(frameNumber - 1), getPositionY(frameNumber - 1) };
        changeXY(xy[0], xy[1]);
        AbstractPenModel lastPen = penList.get(penList.size() - 1);
//        if (lastPen.getStatus()) {
//            List<ILineModel> nextLM = copyLineList(getLineList(frameNumber - 1));
//            nextLM.add(new LineModel(oldPos[0], oldPos[1], getPositionX(frameNumber - 1),
//                                     getPositionY(frameNumber - 1),
//                                     lastPen.getSize(), lastPen.getColorString(),
//                                     lastPen.getStyle()));
//            lines.add(nextLM);
//        }
        return Math.sqrt(Math.pow((oldPos[0] - getPositionX(frameNumber - 1)), 2) +
                         Math.pow((oldPos[1] - getPositionY(frameNumber - 1)), 2));
    }

    public double setTowards (double[] xy) {
        double lastHeading = getHeading(frameNumber - 1);
        if (xy[0] == getPositionX(frameNumber - 1) && xy[1] == getPositionY(frameNumber - 1)) {
            return 0;
        }
        double rawDegrees = Math.toDegrees(Math.atan((xy[1] - getPositionY(frameNumber - 1)) /
                                                     (xy[0] - getPositionX(frameNumber - 1))));
        double newHeading =
                xy[0] - getPositionX(frameNumber - 1) >= 0 ? rawDegrees : rawDegrees - 180;
        heading.add(newHeading);
        double headingDiff = Math.abs(lastHeading - getHeading(frameNumber - 1));
        return headingDiff >= 180 ? 360 - headingDiff : headingDiff;
    }

    public double clearScreen () {
        clearStamps();
        lines.add(new ArrayList<>());
        double[] oldPos =
                new double[] { getPositionX(frameNumber - 1), getPositionY(frameNumber - 1) };
        changeXY(0.0, 0.0);
        return Math.sqrt(Math.pow((oldPos[0] - getPositionX(frameNumber - 1)), 2) +
                         Math.pow((oldPos[1] - getPositionY(frameNumber - 1)), 2));
    }

    public double clearStamps () {
        if (getNumStamps() > 0) {
            stamps.add(new ArrayList<>());
            return stamps.get(stamps.size() - 2).size();
        }
        return 0;
    }

    public double setPenColorIndex (double[] penColorIndex) {
        AbstractPenModel newPen = penList.get(penList.size() - 1).copyPenModel();
        newPen.setColorIndex(penColorIndex[0]);
        newPen.setColorString(colorMap.get(penColorIndex[0]));
        penList.add(newPen);
        return penColorIndex[0];
    }

    public double setPenStyleIndex (double[] penStyleIndex) {
        AbstractPenModel newPen = penList.get(penList.size() - 1).copyPenModel();
        newPen.setStyleIndex(penStyleIndex[0]);
        penList.add(newPen);
        return penStyleIndex[0];
    }

    public double setImageIndex (double[] imageIndex) {
        this.imageIndex.add(imageIndex[0]);
        return imageIndex[0];
    }

    public double setLineWidth (double[] lineWidth) {
        AbstractPenModel newPen = penList.get(penList.size() - 1).copyPenModel();
        newPen.setSize(lineWidth[0]);
        penList.add(newPen);
        return lineWidth[0];
    }

    public int getFrameNumber () {
        return frameNumber;
    }

    public int getNumStamps () {
        return getStampList(frameNumber - 1).size();
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

    public double getPositionY (int frameNumber) {
        return positionY.get(frameNumber);
    }

    public double getPositionX (int frameNumber) {
        return positionX.get(frameNumber);
    }

    public double getHeading (int frameNumber) {
        return heading.get(frameNumber);
    }

    public AbstractPenModel getPen (int frameNumber) {
        return penList.get(frameNumber);
    }

    public ViewablePenModel getViewablePen (int frameNumber) {
        return penList.get(frameNumber);
    }

    public boolean isPenStatus () {
        return penList.get(penList.size() - 1).getStatus();
    }

    public boolean isShowStatus () {
        return showStatus.get(showStatus.size() - 1);
    }

    public double getPenColorIndex () {
        return penList.get(penList.size() - 1).getColorIndex();
    }

    public double getImageIndex () {
        return imageIndex.get(imageIndex.size() - 1);
    }

    public String getImageString (int frameNumber) {
        return imageMap.get(imageIndex.get(frameNumber));
    }

    public double getLineWidth () {
        return penList.get(penList.size() - 1).getSize();
    }

    public void setActive (boolean isActive) {
        this.activeListModel.add(isActive);
    }

    public boolean isActive (int frameNumber) {
        return activeListModel.get(frameNumber);
    }

    public List<ILineModel> getLineList (int frameNumber) {
        return lines.get(frameNumber);
    }

    public List<ViewableLineModel> getViewableLineList (int frameNumber) {
        return lines.get(frameNumber).stream()
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

    @Override
    public List<ViewableLineModel> getViewableLineList () {
        getViewableLineList(lines.size() - 1);
        return null;
    }
}
