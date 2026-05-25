import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * The Controls class handles player input during the game.
 * It allows the player to move with WASD, aim with the mouse,
 * shoot projectiles, and prevents the player from moving through walls.
 *
 * @author William Rodriguez
 */
public class Controls {

    // Movement speed for the player
    private final double SPEED = 5;

    /**
     * Creates a Controls object and connects keyboard and mouse controls
     * to the game scene.
     *
     * @param scene the game scene that listens for keyboard and mouse input
     * @param root the root group where player projectiles are added
     * @param playerTriangle the player's triangle shape
     * @param walls the list of wall rectangles used for collision detection
     * @param enemies the list of enemies that player projectiles can hit
     * @param primaryStage the main window used to close the game
     */
    public Controls(Scene scene, Group root, Polygon playerTriangle,
                    ArrayList<Rectangle> walls, ArrayList<Enemy> enemies,
                    Stage primaryStage) {

        // Listen for key presses on the game scene
        scene.setOnKeyPressed(event -> {

            // Secret button: press P to close the whole game
            if (event.getCode() == KeyCode.P) {
                primaryStage.close();
            }

            // Save the player's old position before moving
            double oldX = playerTriangle.getTranslateX();
            double oldY = playerTriangle.getTranslateY();

            // Move up when W is pressed
            if (event.getCode() == KeyCode.W) {
                playerTriangle.setTranslateY(playerTriangle.getTranslateY() - SPEED);
            }

            // Move down when S is pressed
            if (event.getCode() == KeyCode.S) {
                playerTriangle.setTranslateY(playerTriangle.getTranslateY() + SPEED);
            }

            // Move left when A is pressed
            if (event.getCode() == KeyCode.A) {
                playerTriangle.setTranslateX(playerTriangle.getTranslateX() - SPEED);
            }

            // Move right when D is pressed
            if (event.getCode() == KeyCode.D) {
                playerTriangle.setTranslateX(playerTriangle.getTranslateX() + SPEED);
            }

            // If the player touches a wall, move back to the old position
            if (isTouchingWall(playerTriangle, walls)) {
                playerTriangle.setTranslateX(oldX);
                playerTriangle.setTranslateY(oldY);
            }
        });

        // Make the triangle rotate toward the mouse
        scene.setOnMouseMoved(event -> {

            // Convert full screen mouse position into the scaled game root position
            Point2D mousePoint = root.sceneToLocal(event.getSceneX(), event.getSceneY());

            double playerCenterX = playerTriangle.getBoundsInParent().getCenterX();
            double playerCenterY = playerTriangle.getBoundsInParent().getCenterY();

            double mouseX = mousePoint.getX();
            double mouseY = mousePoint.getY();

            double angle = Math.atan2(mouseY - playerCenterY, mouseX - playerCenterX);
            double angleDegrees = Math.toDegrees(angle);

            playerTriangle.setRotate(angleDegrees + 90);
        });

        // Shoot a projectile when the mouse is clicked
        scene.setOnMouseClicked(event -> {

            // Convert full screen mouse position into the scaled game root position
            Point2D mousePoint = root.sceneToLocal(event.getSceneX(), event.getSceneY());

            double playerCenterX = playerTriangle.getBoundsInParent().getCenterX();
            double playerCenterY = playerTriangle.getBoundsInParent().getCenterY();

            double mouseX = mousePoint.getX();
            double mouseY = mousePoint.getY();

            // Projectile checks walls and enemies
            new Projectile(root, playerCenterX, playerCenterY, mouseX, mouseY, walls, enemies);
        });
    }

    /**
     * Checks whether the player is touching any wall.
     * This is used to stop the player from walking through the maze walls.
     *
     * @param playerTriangle the player's triangle shape
     * @param walls the list of wall rectangles used for collision detection
     * @return true if the player is touching a wall, otherwise false
     */
    private boolean isTouchingWall(Polygon playerTriangle, ArrayList<Rectangle> walls) {

        for (Rectangle wall : walls) {
            if (playerTriangle.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                return true;
            }
        }

        return false;
    }
}