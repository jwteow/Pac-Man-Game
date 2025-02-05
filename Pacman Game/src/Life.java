import bagel.Image;
import bagel.util.Point;

public class Life {
    private final Image LIFE = new Image("res/heart.png");
    private final Point position;

    public Life(int startX, int startY) {
        this.position = new Point(startX, startY);
    }

    /**
     * Method to perform state update
     */
    public void update() {
        LIFE.drawFromTopLeft(this.position.x, this.position.y);
    }
}
