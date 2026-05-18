import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Controls {

    // Movement speed for the player
    private final double SPEED = 5;

    public Controls(Scene scene, Group root, Polygon playerTriangle, ArrayList<Rectangle> walls) {

        // Listen for key presses on the game scene
        scene.setOnKeyPressed(event -> {

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

            // Get the center of the triangle
            double playerCenterX = playerTriangle.getBoundsInParent().getCenterX();
            double playerCenterY = playerTriangle.getBoundsInParent().getCenterY();

            // Get the mouse position
            double mouseX = event.getX();
            double mouseY = event.getY();

            // Find the angle from the player to the mouse
            double angle = Math.atan2(mouseY - playerCenterY, mouseX - playerCenterX);

            // Convert angle from radians to degrees
            double angleDegrees = Math.toDegrees(angle);

            // Rotate the triangle toward the mouse
            playerTriangle.setRotate(angleDegrees + 90);
        });

        // Shoot a projectile when the mouse is clicked
        scene.setOnMouseClicked(event -> {

            // Get the center of the player
            double playerCenterX = playerTriangle.getBoundsInParent().getCenterX();
            double playerCenterY = playerTriangle.getBoundsInParent().getCenterY();

            // Get the mouse click position
            double mouseX = event.getX();
            double mouseY = event.getY();

            // Create a projectile that travels toward the mouse click
            new Projectile(root, playerCenterX, playerCenterY, mouseX, mouseY);
        });
    }

    private boolean isTouchingWall(Polygon playerTriangle, ArrayList<Rectangle> walls) {

        for (Rectangle wall : walls) {
            if (playerTriangle.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                return true;
            }
        }

        return false;
    }
}