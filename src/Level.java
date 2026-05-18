import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Level {

    private Group levelGroup;
    private ArrayList<Rectangle> walls;

    public Level() {
        levelGroup = new Group();
        walls = new ArrayList<>();

        createOuterWalls();
        createMazeWalls();
        createLabels();
    }

    private void createOuterWalls() {
        // Outer border of the maze
        addWall(80, 70, 1120, 3);    // top wall
        addWall(80, 620, 1120, 3);   // bottom wall
        addWall(80, 70, 3, 550);     // left wall
        addWall(1200, 70, 3, 550);   // right wall
    }

    private void createMazeWalls() {
        // Left side area
        addWall(135, 70, 3, 250);
        addWall(135, 405, 3, 215);
        addWall(80, 560, 110, 3);
        addWall(190, 150, 3, 205);
        addWall(190, 405, 3, 155);
        addWall(190, 150, 360, 3);

        // Upper middle area
        addWall(550, 70, 3, 80);
        addWall(550, 150, 180, 3);
        addWall(552, 110, 110, 3);
        addWall(765, 70, 3, 245);
        addWall(675, 150, 185, 3);
        addWall(650, 150, 3, 70);

        // Small short wall near the upper-left middle
        addWall(350, 120, 60, 3);

        // Middle left / middle walls
        addWall(295, 220, 130, 3);
        addWall(270, 315, 250, 3);
        addWall(410, 315, 3, 160);
        addWall(520, 315, 3, 235);
        addWall(375, 415, 55, 3);
        addWall(330, 405, 3, 215);

        // Lower middle area
        addWall(330, 535, 380, 3);
        addWall(390, 595, 135, 3);
        addWall(525, 570, 3, 50);
        addWall(600, 430, 3, 105);
        addWall(785, 535, 3, 85);
        addWall(785, 315, 3, 65);
        addWall(640, 315, 245, 3);

        // Right side upper area
        addWall(885, 70, 3, 245);
        addWall(930, 150, 145, 3);
        addWall(1075, 70, 3, 80);
        addWall(930, 150, 3, 260);

        // Right side vertical room
        addWall(990, 230, 3, 185);
        addWall(1075, 230, 3, 305);
        addWall(990, 230, 85, 3);
        addWall(990, 535, 210, 3);

        // Finish-side walls removed

        // Bottom right area
        addWall(930, 535, 3, 85);
        addWall(930, 590, 190, 3);
    }

    private void createLabels() {
        // Start label
        Text startText = new Text("Start");
        startText.setFont(Font.font("Verdana", 24));
        startText.setFill(Color.BLACK);
        startText.setX(145);
        startText.setY(595);

        // Finish label
        Text finishText = new Text("Finish");
        finishText.setFont(Font.font("Verdana", 24));
        finishText.setFill(Color.BLACK);
        finishText.setX(1140);
        finishText.setY(120);

        levelGroup.getChildren().add(startText);
        levelGroup.getChildren().add(finishText);
    }

    private void addWall(double x, double y, double width, double height) {
        Rectangle wall = new Rectangle(x, y, width, height);
        wall.setFill(Color.BLACK);

        // Add wall to the screen
        levelGroup.getChildren().add(wall);

        // Save wall for collision detection
        walls.add(wall);
    }

    public Group getLevelGroup() {
        return levelGroup;
    }

    public ArrayList<Rectangle> getWalls() {
        return walls;
    }
}