import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Timer;
import java.util.TimerTask;

public class GhostRed extends Ghost{
    private final Image RED_GHOST = new Image("res/ghostRed.png");
    private final Image FRENZY_GHOST = new Image("res/ghostFrenzy.png");
    private Point position;
    private final static int POINTS_VAL = 30;
    private final static int LEFT = 1;
    private final static int RIGHT = 2;
    private final static double SPEED = 1;
    private final static double FRENZY_SPEED_REDUCTION = 0.5;
    private int direction = RIGHT;
    private boolean lvl1Started;
    private boolean isActive;
    private boolean isFrenzied;

    public GhostRed(int startX, int startY) {
        this.position = new Point(startX, startY);
        this.isActive = true;
        this.lvl1Started = false;
        this.isFrenzied = false;
    }

    /**
     * Method to perform state update
     */
    @Override
    public void update() {
        if(!lvl1Started && isActive) {
            RED_GHOST.drawFromTopLeft(this.position.x, this.position.y);
        } else if(lvl1Started && isActive && !isFrenzied) {
            moveDirection(SPEED, 0, direction);
            RED_GHOST.drawFromTopLeft(this.position.x, this.position.y);
        } else if(lvl1Started && isActive && isFrenzied) {
            moveDirection(SPEED - FRENZY_SPEED_REDUCTION, 0, direction);
            FRENZY_GHOST.drawFromTopLeft(this.position.x, this.position.y);
        }
    }


    /**
     * Method to move the ghost based on its direction
     */
    @Override
    public void moveDirection(double xMove, double yMove, int direction) {
        double newX = position.x;
        if (direction == LEFT) {
            newX = position.x - xMove;
        } else if (direction == RIGHT) {
            newX = position.x + xMove;
        }
        this.position = new Point(newX, position.y);
    }

    /**
     * Method to change the direction of ghost when it collides with a wall
     */
    @Override
    public void moveBack() {
        if (direction == LEFT) {
            direction = RIGHT;
        } else if (direction == RIGHT) {
            direction = LEFT;
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
        return new Rectangle(position, RED_GHOST.getWidth(), RED_GHOST.getHeight());
    }
}

