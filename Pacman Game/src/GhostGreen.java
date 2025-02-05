import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GhostGreen extends Ghost{
    private final Image GREEN_GHOST = new Image("res/ghostGreen.png");
    private final Image FRENZY_GHOST = new Image("res/ghostFrenzy.png");
    private Point position;
    private final static int POINTS_VAL = 30;
    private final static int LEFT = 1;
    private final static int RIGHT = 2;
    private final static int UP = 3;
    private final static int DOWN = 4;
    private final static int VERTICAL = 0;
    private final static int HORIZONTAL = 1;
    private final static double SPEED = 4;
    private final static double FRENZY_SPEED_REDUCTION = 0.5;
    private final static Random rand = new Random();
    private int direction;
    private boolean lvl1Started;
    private boolean isFrenzied;
    private boolean isActive;

    public GhostGreen(int startX, int startY) {
        this.position = new Point(startX, startY);
        this.isActive = true;
        this.lvl1Started = false;
        this.isFrenzied = false;
        chooseDirection();
    }

    @Override
    public void update() {
        if(lvl1Started && isActive && !isFrenzied) {
            moveDirection(SPEED, SPEED, direction);
            GREEN_GHOST.drawFromTopLeft(this.position.x, this.position.y);
        } else if(lvl1Started && isActive && isFrenzied) {
            moveDirection(SPEED - FRENZY_SPEED_REDUCTION, SPEED - FRENZY_SPEED_REDUCTION, direction);
            FRENZY_GHOST.drawFromTopLeft(this.position.x, this.position.y);
        }
    }


    /**
     * Method to move the ghost based on its direction
     */
    @Override
    public void moveDirection(double xMove, double yMove, int direction) {
        double newX = position.x;
        double newY = position.y;
        if (direction == LEFT) {
            newX = position.x - xMove;
        } else if (direction == RIGHT) {
            newX = position.x + xMove;
        } else if (direction == UP) {
            newY = position.y + yMove;
        } else if (direction == DOWN) {
            newY = position.y - yMove;
        }
        this.position = new Point(newX, newY);
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
        } else if (direction == LEFT) {
            direction = RIGHT;
        } else if (direction == RIGHT) {
            direction = LEFT;
        }
    }

    /**
     * Method to randomly choose whether the green ghost is moving vertically or horizontally
     */
    private void chooseDirection() {
        int[] startDirection = {VERTICAL, HORIZONTAL};
        if (startDirection[rand.nextInt(startDirection.length)] == VERTICAL) {
            direction = DOWN;
        } else if (startDirection[rand.nextInt(startDirection.length)] == HORIZONTAL) {
            direction = RIGHT;
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
        return new Rectangle(position, GREEN_GHOST.getWidth(), GREEN_GHOST.getHeight());
    }

}
