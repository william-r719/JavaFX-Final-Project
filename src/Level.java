import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * The Level class creates and stores the maze layout for the game.
 * It contains the wall rectangles used for collision and the text labels
 * shown on the playable screen.
 *
 * @author William Rodriguez
 */
public class Level {

    // Group that holds the walls and text for the level
    private Group levelGroup;

    // List of walls used for collision
    private ArrayList<Rectangle> walls;

    /**
     * Creates a new Level object.
     * The constructor initializes the level group, creates the wall list,
     * builds the maze walls, and adds the level labels.
     */
    public Level() {
        levelGroup = new Group();
        walls = new ArrayList<>();

        createWalls();
        createLabels();
    }

    /**
     * Creates all of the wall rectangles for the maze.
     * Each wall is added to both the level group and the wall list
     * so it can be displayed and used for collision detection.
     */
    private void createWalls() {

        // Wall thickness
        int wallSize = 5;

        /*
         * OUTER WALLS
         */
        addWall(20, 30, 1220, wallSize);     // top wall
        addWall(20, 30, wallSize, 600);      // left wall
        addWall(20, 625, 1220, wallSize);    // bottom wall
        addWall(1240, 30, wallSize, 600);    // right wall

        /*
         * LEFT SIDE WALLS
         */
        addWall(80, 30, wallSize, 270);      // upper left vertical wall
        addWall(140, 115, wallSize, 220);    // left inner vertical wall
        addWall(140, 115, 390, wallSize);    // top-left room horizontal wall
        addWall(140, 390, wallSize, 170);    // left lower vertical wall
        addWall(140, 560, 150, wallSize);    // lower-left horizontal wall

        /*
         * CENTER LEFT WALLS
         */
        addWall(290, 390, wallSize, 235);    // vertical wall above start area
        addWall(290, 560, 420, wallSize);    // lower middle horizontal wall
        addWall(380, 390, wallSize, 170);    // middle vertical wall
        addWall(380, 290, 270, wallSize);    // middle horizontal wall

        /*
         * TOP MIDDLE WALLS
         */
        addWall(530, 30, wallSize, 165);     // top middle vertical wall
        addWall(650, 115, wallSize, 80);     // short vertical hanging wall

        /*
         * CENTER RIGHT WALLS
         */
        addWall(770, 30, wallSize, 265);     // upper center-right vertical wall
        addWall(630, 290, 270, wallSize);    // center-right horizontal wall
        addWall(900, 115, wallSize, 180);    // right side vertical wall
        addWall(790, 290, wallSize, 70);     // small vertical under middle wall

        /*
         * RIGHT SIDE WALLS
         */
        addWall(950, 115, 160, wallSize);    // upper-right horizontal wall
        addWall(950, 115, wallSize, 280);    // upper-right vertical wall

        addWall(1020, 205, wallSize, 330);   // finish room left wall
        addWall(1110, 205, wallSize, 330);   // finish room right wall

        // REMOVED highlighted wall under triangle:
        // addWall(1020, 205, 90, wallSize);

        addWall(1020, 535, 220, wallSize);   // finish room bottom wall

        addWall(950, 535, wallSize, 90);     // bottom right vertical wall

        /*
         * BOTTOM MIDDLE / RIGHT WALLS
         */
        addWall(590, 445, wallSize, 115);    // middle lower vertical wall
        addWall(790, 535, wallSize, 90);     // lower center-right vertical wall
    }

    /**
     * Creates and adds text labels to the level.
     * Currently, this method adds the Start label near the player's
     * starting position.
     */
    private void createLabels() {

        // Start label
        Text startText = new Text("Start");
        startText.setFont(Font.font("Verdana", 32));
        startText.setFill(Color.BLACK);
        startText.setX(90);
        startText.setY(600);

        levelGroup.getChildren().add(startText);
    }

    /**
     * Creates a wall rectangle and adds it to the level.
     * The wall is added to the screen through levelGroup and also stored
     * in the walls list so other classes can check collision against it.
     *
     * @param x the x-position of the wall
     * @param y the y-position of the wall
     * @param width the width of the wall
     * @param height the height of the wall
     */
    private void addWall(double x, double y, double width, double height) {
        Rectangle wall = new Rectangle(x, y, width, height);
        wall.setFill(Color.BLACK);

        walls.add(wall);
        levelGroup.getChildren().add(wall);
    }

    /**
     * Returns the group that contains all visible level objects.
     *
     * @return the group containing the maze walls and labels
     */
    public Group getLevelGroup() {
        return levelGroup;
    }

    /**
     * Returns the list of wall rectangles used for collision detection.
     *
     * @return the list of walls in the level
     */
    public ArrayList<Rectangle> getWalls() {
        return walls;
    }
}