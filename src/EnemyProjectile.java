import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * The EnemyProjectile class represents a projectile fired by a green enemy.
 * The projectile travels toward the player's position and becomes inactive
 * if it hits a wall or the player.
 *
 * @author William Rodriguez
 */
public class EnemyProjectile {

    // The projectile shape
    private Circle projectileCircle;

    // Projectile movement direction
    private double directionX;
    private double directionY;

    // Projectile speed
    private double speed;

    // Whether the projectile is still active
    private boolean active;

    // True if this projectile hit the player
    private boolean hitPlayer;

    /**
     * Creates a new enemy projectile.
     * The projectile starts at the enemy's position and moves toward
     * the player's position at the time it was fired.
     *
     * @param startX the starting x-position of the projectile
     * @param startY the starting y-position of the projectile
     * @param targetX the x-position the projectile is aimed toward
     * @param targetY the y-position the projectile is aimed toward
     */
    public EnemyProjectile(double startX, double startY, double targetX, double targetY) {

        projectileCircle = new Circle(5);
        projectileCircle.setFill(Color.GREEN);

        projectileCircle.setCenterX(startX);
        projectileCircle.setCenterY(startY);

        speed = 4;
        active = true;
        hitPlayer = false;

        double dx = targetX - startX;
        double dy = targetY - startY;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            directionX = dx / distance;
            directionY = dy / distance;
        }
    }

    /**
     * Updates the projectile's position.
     * The projectile moves in its saved direction and becomes inactive
     * if it touches a wall or hits the player.
     *
     * @param walls the list of walls used for collision detection
     * @param playerTriangle the player's triangle shape
     */
    public void update(ArrayList<Rectangle> walls, Polygon playerTriangle) {

        projectileCircle.setCenterX(projectileCircle.getCenterX() + directionX * speed);
        projectileCircle.setCenterY(projectileCircle.getCenterY() + directionY * speed);

        // Disappear if it hits a wall
        for (Rectangle wall : walls) {
            if (projectileCircle.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                active = false;
            }
        }

        // Disappear and mark hit if it hits the player
        if (projectileCircle.getBoundsInParent().intersects(playerTriangle.getBoundsInParent())) {
            active = false;
            hitPlayer = true;
        }
    }

    /**
     * Returns the projectile's circle shape so it can be displayed on the screen.
     *
     * @return the Circle object representing the enemy projectile
     */
    public Circle getProjectileCircle() {
        return projectileCircle;
    }

    /**
     * Checks whether the projectile is still active.
     *
     * @return true if the projectile is still active, otherwise false
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Checks whether the projectile has hit the player.
     *
     * @return true if the projectile hit the player, otherwise false
     */
    public boolean hitPlayer() {
        return hitPlayer;
    }
}