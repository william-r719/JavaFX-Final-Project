import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * The Projectile class represents a projectile fired by the player.
 * The projectile travels toward the mouse click location and disappears
 * if it hits a wall, hits an enemy, or leaves the screen.
 *
 * @author William Rodriguez
 */
public class Projectile {

    // The projectile shape
    private Circle projectileCircle;

    // Projectile direction
    private double directionX;
    private double directionY;

    // Projectile speed
    private double speed;

    /**
     * Creates a new player projectile.
     * The projectile starts at the player's position and moves toward
     * the location where the mouse was clicked.
     *
     * @param root the root group where the projectile is displayed
     * @param startX the starting x-position of the projectile
     * @param startY the starting y-position of the projectile
     * @param targetX the x-position the projectile is aimed toward
     * @param targetY the y-position the projectile is aimed toward
     * @param walls the list of walls used for collision detection
     * @param enemies the list of enemies that can be hit by the projectile
     */
    public Projectile(Group root, double startX, double startY,
                      double targetX, double targetY,
                      ArrayList<Rectangle> walls, ArrayList<Enemy> enemies) {

        // Create the projectile
        projectileCircle = new Circle(5);
        projectileCircle.setFill(Color.BLACK);

        // Set starting position
        projectileCircle.setCenterX(startX);
        projectileCircle.setCenterY(startY);

        // Set projectile speed
        speed = 8;

        // Find direction from player to mouse click
        double dx = targetX - startX;
        double dy = targetY - startY;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            directionX = dx / distance;
            directionY = dy / distance;
        }

        // Add projectile to the screen
        root.getChildren().add(projectileCircle);

        // Start moving the projectile
        moveProjectile(root, walls, enemies);
    }

    /**
     * Moves the projectile using an AnimationTimer.
     * The projectile is removed if it hits a wall, hits an enemy,
     * or travels off the screen.
     *
     * @param root the root group containing the projectile and enemies
     * @param walls the list of walls used for collision detection
     * @param enemies the list of enemies that can be destroyed
     */
    private void moveProjectile(Group root, ArrayList<Rectangle> walls, ArrayList<Enemy> enemies) {

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // Move projectile
                projectileCircle.setCenterX(projectileCircle.getCenterX() + directionX * speed);
                projectileCircle.setCenterY(projectileCircle.getCenterY() + directionY * speed);

                // If projectile hits a wall, remove it
                if (isTouchingWall(walls)) {
                    root.getChildren().remove(projectileCircle);
                    stop();
                    return;
                }

                // If projectile hits an enemy, remove the enemy and the projectile
                for (int i = enemies.size() - 1; i >= 0; i--) {

                    Enemy enemy = enemies.get(i);

                    if (projectileCircle.getBoundsInParent().intersects(enemy.getBounds())) {

                        // Remove enemy from the screen
                        root.getChildren().remove(enemy.getEnemyCircle());

                        // Remove enemy from the enemy list
                        enemies.remove(i);

                        // Remove projectile from the screen
                        root.getChildren().remove(projectileCircle);

                        // Stop the projectile animation
                        stop();
                        return;
                    }
                }

                // Remove projectile if it goes off screen
                if (projectileCircle.getCenterX() < 0 ||
                        projectileCircle.getCenterX() > 1300 ||
                        projectileCircle.getCenterY() < 0 ||
                        projectileCircle.getCenterY() > 750) {

                    root.getChildren().remove(projectileCircle);
                    stop();
                }
            }
        };

        timer.start();
    }

    /**
     * Checks whether the projectile is touching any wall.
     *
     * @param walls the list of wall rectangles used for collision detection
     * @return true if the projectile touches a wall, otherwise false
     */
    private boolean isTouchingWall(ArrayList<Rectangle> walls) {

        for (Rectangle wall : walls) {
            if (projectileCircle.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                return true;
            }
        }

        return false;
    }
}
