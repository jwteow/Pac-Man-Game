import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Timer;
import java.util.TimerTask;

public class GhostBlue extends Ghost{
    private final Image BLUE_GHOST = new Image("res/ghostBlue.png");
    private final Image FRENZY_GHOST = new Image("res/ghostFrenzy.png");
    private Point position;
    private final static int POINTS_VAL = 30;
    private final static int UP = 3;
    private final static int DOWN = 4;
    private final static double SPEED = 2;
    private final static double FRENZY_SPEED_REDUCTION = 0.5;
    private int direction = DOWN;
    private boolean lvl1Started;
    private boolean isFrenzied;
    private boolean isActive;

    public GhostBlue(int startX, int startY) {
        this.position = new Point(startX, startY);
        this.isActive = true;
        this.lvl1Started = false;
        this.isFrenzied = false;
    }

    @Override
    public void update() {
        if(lvl1Started && isActive && !isFrenzied) {
            moveDirection(SPEED, SPEED, direction);
            BLUE_GHOST.drawFromTopLeft(this.position.x, this.position.y);
        } else if(lvl1Started && isActive && isFrenzied) {
            moveDirection(0, SPEED - FRENZY_SPEED_REDUCTION, direction);
            FRENZY_GHOST.drawFromTopLeft(this.position.x, this.position.y);
        }
    }


    /**
     * Method to move the ghost based on its direction
     */
    @Override
    public void moveDirection(double xMove, double yMove, int direction) {
        double newY = position.y;
        if (direction == UP) {
            newY = position.y + yMove;
        } else if (direction == DOWN) {
            newY = position.y - yMove;
        }
        this.position = new Point(position.x, newY);
    }

    /**
     * Method to change the direction of ghost when it collides with a wall
     */
    @Override
    public void moveBack() {
        if (direction == UP) {
            direction = DOWN;
        } else if (direction == DOWN) {
            direction = UP;
        }
    }

    /**
     * Method to check if level 1 has started
     */
    public void level1Started(boolean lvl1Start) {
        lvl1Started = lvl1Start;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean isFrenzied() {
        return isFrenzied;
    }

    @Override
    public void setFrenzy(boolean frenzied) {
        isFrenzied = frenzied;
    }

    public int getPointsVal() {
        return POINTS_VAL;
    }

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(position, BLUE_GHOST.getWidth(), BLUE_GHOST.getHeight());
    }
}