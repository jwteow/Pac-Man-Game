import bagel.*;
import bagel.util.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * SWEN20003 Project 2, Semester 1, 2023
 *
 * Please enter your name below
 * @author Justin Teow
 */
public class ShadowPac extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW PAC";
    private final static String LVL0_FILE = "res/level0.csv";
    private final static String LVL1_FILE = "res/level1.csv";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

    private final static int MESSAGE_FONT_SIZE = 65;
    private final static int INSTRUCTION_FONT_SIZE = 24;
    private final static int TITLE_X = 260;
    private final static int TITLE_Y = 250;
    private final static int INST_X_OFFSET = 60;
    private final static int INST_Y_OFFSET = 190;
    private final Font MESSAGE_FONT = new Font("res/FSO8BITR.TTF", MESSAGE_FONT_SIZE);
    private final Font INSTRUCTION_FONT = new Font("res/FSO8BITR.TTF", INSTRUCTION_FONT_SIZE);
    private final static String INSTRUCTION_LVL0_MESSAGE = "PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE";
    private final static String INSTRUCTION_LVL1_MESSAGE = "PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE\n" +
            "EAT THE PELLET TO ATTACK";
    private final static String LOSE_MESSAGE = "GAME OVER!";
    private final static String WIN_MESSAGE = "WELL DONE!";
    private final static int LVL1_WIN_SCORE = 800;
    private final static int GHOST_0_ARRAY_SIZE = 4;
    private final static int GHOST_1_ARRAY_SIZE = 4;
    private final static int WALL_0_ARRAY_SIZE = 145;
    private final static int WALL_1_ARRAY_SIZE = 151;
    private final static int DOT_0_ARRAY_SIZE = 121;
    private final static int DOT_1_ARRAY_SIZE = 106;
    private final static int CHERRY_ARRAY_SIZE = 3;
    private final static int PELLET_ARRAY_SIZE = 1;
    private final static Ghost[] ghosts0 = new Ghost[GHOST_0_ARRAY_SIZE];
    private final static Ghost[] ghosts1 = new Ghost[GHOST_1_ARRAY_SIZE];
    private final static Wall[] walls0 = new Wall[WALL_0_ARRAY_SIZE];
    private final static Wall[] walls1 = new Wall[WALL_1_ARRAY_SIZE];
    private final static Dot[] dots0 = new Dot[DOT_0_ARRAY_SIZE];
    private final static Dot[] dots1 = new Dot[DOT_1_ARRAY_SIZE];
    private final static Cherry[] cherries = new Cherry[CHERRY_ARRAY_SIZE];
    private final static Pellet[] pellets = new Pellet[PELLET_ARRAY_SIZE];
    private Pacman pacman;
    private boolean lvl0Start;
    private boolean lvl1Start;
    private boolean gameOver;
    private boolean lvl0Win;
    private boolean lvl1Win;


    public ShadowPac(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        readCSV();
        lvl0Start = false;
        lvl0Win = false;
        lvl1Start = false;
        lvl1Win = false;
    }

    /**
     * Method used to read file and create objects
     */
    private void readCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LVL0_FILE))) {
            String line;
            int currentGhostCount = 0;
            int currentWallCount = 0;
            int currentDotCount = 0;

            while((line = reader.readLine()) != null) {
                String[] sections = line.split(",");
                switch (sections[0]) {
                    case "Player":
                        pacman = new Pacman(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                    case "Ghost":
                        ghosts0[currentGhostCount] = new GhostRed(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentGhostCount++;
                        break;
                    case "Wall":
                        walls0[currentWallCount] = new Wall(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentWallCount++;
                        break;
                    case "Dot":
                        dots0[currentDotCount] = new Dot(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        currentDotCount++;
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(LVL1_FILE))) {
            String line;
            int currentGhostCount = 0;
            int currentWallCount = 0;
            int currentDotCount = 0;
            int currentCherryCount = 0;
            int currentPelletCount = 0;

            while((line = reader.readLine()) != null) {
                String[] sections = line.split(",");
                switch (sections[0]) {
                    case "Player":
                        pacman = new Pacman(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                    case "GhostRed":
                        ghosts1[currentGhostCount] = new GhostRed(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentGhostCount++;
                        break;
                    case "GhostBlue":
                        ghosts1[currentGhostCount] = new GhostBlue(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentGhostCount++;
                        break;
                    case "GhostGreen":
                        ghosts1[currentGhostCount] = new GhostGreen(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentGhostCount++;
                        break;
                    case "GhostPink":
                        ghosts1[currentGhostCount] = new GhostPink(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentGhostCount++;
                        break;
                    case "Cherry":
                        cherries[currentCherryCount] = new Cherry(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentCherryCount++;
                        break;
                    case "Pellet":
                        pellets[currentPelletCount] = new Pellet(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentPelletCount++;
                        break;
                    case "Wall":
                        walls1[currentWallCount] = new Wall(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentWallCount++;
                        break;
                    case "Dot":
                        dots1[currentDotCount] = new Dot(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        currentDotCount++;
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowPac game = new ShadowPac();
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        if (!lvl0Start) {
            BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
            drawLvl0StartScreen();
            if (input.wasPressed(Keys.SPACE)) {
                lvl0Start = true;
            }
        }

        // level 0 has started
        if (lvl0Start && !gameOver && !lvl0Win && !lvl1Start) {
            BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

            for(Ghost current: ghosts0) {
                current.update();
            }
            for(Wall current: walls0) {
                current.update();
            }
            for(Dot current: dots0) {
                current.update();
            }
            pacman.update(input, this);

            if(pacman.isDead()) {
                gameOver = true;
            }
            if(pacman.ateAllDots()) {
                lvl0Win = true;
            }
        }

        if(lvl0Win) {
            BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
            drawLvl1StartScreen();
            if (input.wasPressed(Keys.SPACE)) {
                lvl1Start = true;
                lvl0Start = false;
                pacman.pacRespawn();
                pacman.setCurrentLives(pacman.getMaxLives());
                pacman.setScore(0);
                for(Ghost current: ghosts1) {
                    current.level1Started(lvl1Start);
                }
            }
        }

        // level 1 has started
        if (lvl1Start && lvl0Win && !gameOver) {
            BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

            for(Ghost current: ghosts1) {
                current.update();
            }
            for(Cherry current: cherries) {
                current.update();
            }
            for(Pellet current: pellets) {
                current.update();
            }
            for(Wall current: walls1) {
                current.update();
            }
            for(Dot current: dots1) {
                current.update();
            }
            pacman.update(input, this);

            if(pacman.isDead()) {
                gameOver = true;
            }

            if(pacman.getScore() == LVL1_WIN_SCORE) {
                lvl1Win = true;
            }
        }

        if (gameOver) {
            BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            drawMessage(LOSE_MESSAGE);
        } else if (lvl1Win) {
            BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
            drawMessage(WIN_MESSAGE);
        }
    }

    /**
     * Method to check for collisions between pacman and other objects, and performs respective actions.
     */
    public void checkCollisions(Pacman pacman) {
        Rectangle pacBox =  new Rectangle(pacman.getPosition(), pacman.getCurrentImage().getWidth(),
                pacman.getCurrentImage().getHeight());

        // level 0 entities
        if(lvl0Start) {
            for (Ghost current : ghosts0) {
                Rectangle ghostBox = current.getBoundingBox();
                if (pacBox.intersects(ghostBox)) {
                    pacman.pacRespawn();
                    pacman.setCurrentLives(pacman.getCurrentLives() - 1);
                }
            }
            for (Wall current : walls0) {
                Rectangle wallBox = current.getBoundingBox();
                if (pacBox.intersects(wallBox)) {
                    pacman.moveBack();
                }
            }
            for (Dot current : dots0) {
                Rectangle dotBox = current.getBoundingBox();
                if (current.isActive() && pacBox.intersects(dotBox)) {
                    pacman.setScore(pacman.getScore() + current.getPointsVal());
                    current.setActive(false);
                }
            }
        }

        // level 1 entities
        if(lvl1Start) {
            for (Ghost current : ghosts1) {
                Rectangle ghostBox = current.getBoundingBox();
                if (pacBox.intersects(ghostBox) && !current.isFrenzied() && current.isActive()) {
                    pacman.pacRespawn();
                    pacman.setCurrentLives((pacman.getCurrentLives()) - 1);
                } else if (pacBox.intersects(ghostBox) && current.isFrenzied() && current.isActive()) {
                    pacman.setScore(pacman.getScore() + current.getPointsVal());
                    current.setActive(false);
                }
                for (Wall ghostWall : walls1) {
                    Rectangle wallBox = ghostWall.getBoundingBox();
                    if (ghostBox.intersects(wallBox)) {
                        current.moveBack();
                    }
                }
            }
            for (Wall current : walls1) {
                Rectangle wallBox = current.getBoundingBox();
                if (pacBox.intersects(wallBox)) {
                    pacman.moveBack();
                }
            }
            for (Dot current : dots1) {
                Rectangle dotBox = current.getBoundingBox();
                if (current.isActive() && pacBox.intersects(dotBox)) {
                    pacman.setScore(pacman.getScore() + current.getPointsVal());
                    current.setActive(false);
                }
            }
            for (Cherry current : cherries) {
                Rectangle cherryBox = current.getBoundingBox();
                if (current.isActive() && pacBox.intersects(cherryBox)) {
                    pacman.setScore(pacman.getScore() + current.getPointsVal());
                    current.setActive(false);
                }
            }
            for (Pellet current : pellets) {
                Rectangle pelletBox = current.getBoundingBox();
                if (current.isActive() && pacBox.intersects(pelletBox)) {
                    current.setActive(false);
                    for (Ghost ghost : ghosts1) {
                        ghost.setFrenzy(true);
                    }
                    // Timer method to stop the frenzy mode
                    Timer frenzyTimer = new Timer();
                    TimerTask stopFrenzy = new TimerTask() {
                        @Override
                        public void run() {
                            for (Ghost ghost : ghosts1) {
                                ghost.setFrenzy(false);
                                ghost.setActive(true);
                            }
                        }
                    };

                    frenzyTimer.schedule(stopFrenzy, 1000000/60);
                }
            }
        }
    }

    /**
     * Method used to draw the level 0 start screen title and instructions
     */
    private void drawLvl0StartScreen() {
        MESSAGE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
        INSTRUCTION_FONT.drawString(INSTRUCTION_LVL0_MESSAGE, TITLE_X + INST_X_OFFSET, TITLE_Y + INST_Y_OFFSET);
    }

    /**
     * Method used to draw the level 1 start screen
     */
    private void drawLvl1StartScreen() {
        INSTRUCTION_FONT.drawString(INSTRUCTION_LVL1_MESSAGE, TITLE_X + INST_X_OFFSET, TITLE_Y + INST_Y_OFFSET);
    }

    /**
     * Method used to draw end screen messages
     */
    private void drawMessage(String message) {
        MESSAGE_FONT.drawString(message, (Window.getWidth()/2.0 - (MESSAGE_FONT.getWidth(message)/2.0)),
                (Window.getHeight()/2.0 + (MESSAGE_FONT_SIZE/2.0)));
    }


}
