package model;

import constants.UIConstants;

public class DisplayBehaviorHelper {
    
    public double checkFence (double num, double offset) {
        if (boundCheck(num, getUpperBound(offset), offset, -1)) {
            return getUpperBound(offset);
        }
        else if (boundCheck(num, getLowerBound(offset), offset, 1)) {
            return getLowerBound(offset);
        }
        return num;
    }

    public double getUpperBound (double offset) {
        return -UIConstants.SHEIGHT / 2 + offset / 2;
    }

    public double getLowerBound (double offset) {
        return UIConstants.SHEIGHT / 2 - offset / 2;
    }

    public boolean boundCheck (double num, double bound, double offset, int compare) {
        double compareTo = new Double(num).compareTo(new Double(bound));
        return compareTo == compare || compareTo == 0;
    }

    public boolean checkX (double x) {
        double offset = UIConstants.TURTLE_IMAGE_HEIGHT;
        return checkFence(x, offset) != getUpperBound(offset) &&
               checkFence(x, offset) != getLowerBound(offset);
    }

    public boolean checkY (double y) {
        double offset = UIConstants.TURTLE_IMAGE_WIDTH;
        return checkFence(y, offset) != getUpperBound(offset) &&
               checkFence(y, offset) != getLowerBound(offset);
    }
    
}
