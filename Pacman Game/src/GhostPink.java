import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GhostPink extends Ghost{
    private final Image PINK_GHOST = new Image("res/ghostPink.png");
    private final Image FRENZY_GHOST = new Image("res/ghostFrenzy.png");
    private Point position;
    private Point prevPosition;
    private final static int POINTS_VAL = 30;
    private final static int LEFT = 1;
    private final static int RIGHT = 2;
    private final static int UP = 3;
    private final static int DOWN = 4;
    private final static double SPEED = 3;
    private final static Random rand = new Random();
    private int direction;
    private boolean lvl1Started;
    private boolean isFrenzied;
    private boolean isActive;

    public GhostPink(int startX, int startY) {
        this.position = new Point(startX, startY);
        this.isActive = true;
        this.lvl1Started = false;
        this.isFrenzied = false;
        chooseDirection();
    }

    @Override
    public void update() {
        if(lvl1Started && isActive && !isFrenzied) {
            PINK_GHOST.drawFromTopLeft(this.position.x, this.position.y);
            setPrevPosition();
            if (direction == UP) {
                moveDirection(0, SPEED, direction);
            } else if (direction == DOWN) {
                moveDirection(0, -SPEED, direction);
            } else if (direction == LEFT) {
                moveDirection(-SPEED, 0, direction);
            } else if (direction == RIGHT) {
                moveDirection(SPEED, 0, direction);
            }
        } else if(lvl1Started && isActive && isFrenzied) {
            setPrevPosition();
            if (direction == UP) {
                moveDirection(0, SPEED, direction);
            } else if (direction == DOWN) {
                moveDirection(0, -SPEED, direction);
            } else if (direction == LEFT) {
                moveDirection(-SPEED, 0, direction);
            } else if (direction == RIGHT) {
                moveDirection(SPEED, 0, direction);
            }
            FRENZY_GHOST.drawFromTopLeft(this.position.x, this.position.y);
        }
    }


    /**
     * Method to move the ghost based on its direction
     */
    @Override
    public void moveDirection(double xMove, double yMove, int direction) {
        double newX = position.x + xMove;
        double newY = position.y + yMove;
        this.position = new Point(newX, newY);
    }

    /**
     * Method to change the direction of pink ghost when it collides with a wall
     */
    @Override
    public void moveBack() {
        int[] directions = {UP, DOWN, LEFT, RIGHT};
        this.position = prevPosition;
        direction = directions[rand.nextInt(directions.length)];
    }

    /**
     * Method to randomly set the direction of the pink ghost at the start of level 1
     */
    private void chooseDirection() {
        int[] directions = {UP, DOWN, LEFT, RIGHT};
        direction = directions[rand.nextInt(directions.length)];
    }

    private void setPrevPosition() {
        this.prevPosition = new Point(position.x, position.y);
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
        return new Rectangle(position, PINK_GHOST.getWidth(), PINK_GHOST.getHeight());
    }

}
