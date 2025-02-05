import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public abstract class Ghost {
    public abstract void update();
    public abstract void moveDirection(double xMove, double yMove, int direction);
    public abstract void moveBack();
    public abstract void level1Started(boolean lvl1Start);
    public abstract boolean isActive();
    public abstract void setActive(boolean active);
    public abstract boolean isFrenzied();
    public abstract void setFrenzy(boolean frenzied);
    public abstract int getPointsVal();
    public abstract Rectangle getBoundingBox();

}


