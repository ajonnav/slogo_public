package model;



public class StampModel {

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

    public String getImageString () {
        return imageString;
    }

    public double getPositionX () {
        return x;
    }

    public double getPositionY () {
        return y;
    }

    public double getHeading () {
        return heading;
    }
    
    public StampModel copyStampModel() {
        return new StampModel(this.imageString, this.x, this.y, this.heading);
    }
}
