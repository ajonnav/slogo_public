package model;


public class StampModel implements IStampModel, ViewableStampModel{

    private String imageString;
    private double x;
    private double y;
    private double heading;

    public StampModel (String image, double x, double y, double heading) {
        this.imageString = image;
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    @Override
    public String getImageString () {
        return imageString;
    }

    @Override
    public double getPositionX () {
        return x;
    }

    @Override
    public double getPositionY () {
        return y;
    }

    @Override
    public double getHeading () {
        return heading;
    }
    
    @Override
    public IStampModel copyStampModel() {
        return new StampModel(this.imageString, this.x, this.y, this.heading);
    }
}