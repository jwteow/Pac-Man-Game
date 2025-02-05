import bagel.*;
import bagel.util.Point;

import java.util.Timer;
import java.util.TimerTask;

public class Pacman {
    private final static String PAC_CLOSE = "res/pac.png";
    private final static String PAC_OPEN = "res/pacOpen.png";
    private final static int MAX_LIVES = 3;
    private final static double MOVE_SPEED = 3;
    private final static int WIN_SCORE = 1210;

    private final static Life[] hearts = new Life[MAX_LIVES];
    private final static int LIVES_X = 900;
    private final static int LIVES_Y = 10;
    private final static int LIVES_X_OFFSET = 30;
    private final static int SCORE_FONT_SIZE = 20;
    private final Font SCORE_FONT = new Font("res/FSO8BITR.TTF", SCORE_FONT_SIZE);
    private final static int SCORE_X = 25;
    private final static int SCORE_Y = 25;
    private final static DrawOptions ROTATION = new DrawOptions();
    private final static double RIGHT = 0;
    private final static double DOWN = Math.PI/2;
    private final static double LEFT = Math.PI;
    private final static double UP = (3 * Math.PI)/2;

    private Point position;
    private Point spawnPoint;
    private Point prevPosition;
    private int lives;
    private int score;
    private Image currentImage;

    public int animationNum = 1;

    public Pacman(int startX, int startY) {
        this.position = new Point(startX, startY);
        this.lives = MAX_LIVES;
        this.currentImage = new Image(PAC_OPEN);
        this.spawnPoint = new Point(startX, startY);

        // Timer method to animate pacman opening and closing his mouth every 15 frames
        Timer animationTimer = new Timer();
        TimerTask pacAnimation = new TimerTask() {
            @Override
            public void run() {
                if (animationNum == 1) {
                    animationNum = 2;
                } else if (animationNum == 2) {
                    animationNum = 1;
                }
            }
        };
        animationTimer.scheduleAtFixedRate(pacAnimation, 0, 1000/4);
    }

    /**
     * Method to perform state update
     */
    public void update(Input input, ShadowPac gameObject) {
        if (input.isDown(Keys.UP)) {
            ROTATION.setRotation(UP);
            if (animationNum == 1) {
                this.currentImage = new Image(PAC_CLOSE);
            } else if (animationNum == 2) {
                this.currentImage = new Image(PAC_OPEN);
            }
            setPrevPosition();
            move(0, -MOVE_SPEED);
        } else if (input.isDown(Keys.DOWN)) {
            ROTATION.setRotation(DOWN);
            if (animationNum == 1) {
                this.currentImage = new Image(PAC_CLOSE);
            } else if (animationNum == 2) {
                this.currentImage = new Image(PAC_OPEN);
            }
            setPrevPosition();
            move(0, MOVE_SPEED);
        } else if (input.isDown(Keys.LEFT)) {
            ROTATION.setRotation(LEFT);
            if (animationNum == 1) {
                this.currentImage = new Image(PAC_CLOSE);
            } else if (animationNum == 2) {
                this.currentImage = new Image(PAC_OPEN);
            }
            setPrevPosition();
            move(-MOVE_SPEED, 0);
        } else if (input.isDown(Keys.RIGHT)) {
            ROTATION.setRotation(RIGHT);
            if (animationNum == 1) {
                this.currentImage = new Image(PAC_CLOSE);
            } else if (animationNum == 2) {
                this.currentImage = new Image(PAC_OPEN);
            }
            setPrevPosition();
            move(MOVE_SPEED, 0);
        }

        this.currentImage.drawFromTopLeft(position.x, position.y, ROTATION);
        gameObject.checkCollisions(this);
        fillLifeBar();
        renderLifeBar();
        renderScoreBar();
    }

    /**
     * Method to store Pacman's previous position
     */
    private void setPrevPosition() {
        this.prevPosition = new Point(position.x, position.y);
    }

    /**
     * Method to move Pacman
     */
    private void move(double xMove, double yMove) {
        double newX = position.x + xMove;
        double newY = position.y + yMove;
        this.position = new Point(newX, newY);
    }

    /**
     * Method that moves Pacman to previous position
     */
    public void moveBack() {
        this.position = prevPosition;
    }

    /**
     * Method to send Pacman back to the spawnpoint when it collides with a ghost
     */
    public void pacRespawn() {
        this.position = new Point(spawnPoint.x, spawnPoint.y);
    }

    /**
     * Method that creates the Life objects
     */
    private void fillLifeBar() {
        for (int i = 0; i < MAX_LIVES; i++) {
            hearts[i] = new Life(LIVES_X + (i * LIVES_X_OFFSET), LIVES_Y);
        }
    }

    /**
     * Method to render Life bar
     */
    private void renderLifeBar() {
        if(getCurrentLives() == MAX_LIVES) {
            for(Life current : hearts) {
                current.update();
            }
        } else if(getCurrentLives() == MAX_LIVES - 1) {
            for (int i = 0; i < MAX_LIVES - 1; i++) {
                hearts[i].update();
            }
        } else if(getCurrentLives() == MAX_LIVES - 2) {
            for (int i = 0; i < MAX_LIVES - 2; i++) {
                hearts[i].update();
            }
        }
    }

    /**
     * Method to check if Pacman has no more lives
     */
    public boolean isDead() {
        return lives <= 0;
    }

    /**
     * Method to render the score bar
     */
    private void renderScoreBar() {
        SCORE_FONT.drawString("SCORE " + getScore(), SCORE_X, SCORE_Y);
    }

    /**
     * Method to check if Pacman has collected all dots
     */
    public boolean ateAllDots() {
        return getScore() == WIN_SCORE;
    }

    public Point getPosition() {
        return position;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public int getMaxLives() {
        return MAX_LIVES;
    }

    public int getCurrentLives() {
        return lives;
    }

    public void setCurrentLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
