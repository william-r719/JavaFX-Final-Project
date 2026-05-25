import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * The Enemy class represents an enemy in the game.
 * A red enemy chases the player, while a green enemy stands still
 * and shoots projectiles at the player when the player is close enough.
 *
 * @author William Rodriguez
 */
public class Enemy {

    // The enemy shape
    private Circle enemyCircle;

    // Enemy movement speed
    private double speed;

    // How close the player needs to be before enemy reacts
    private double detectionRange;

    // Type of enemy: "RED" chases, "GREEN" shoots
    private String enemyType;

    // Cooldown so green enemy does not shoot too fast
    private int shootCooldown;

    /**
     * Creates a new Enemy object at the given location.
     * The enemy type determines its color and behavior.
     * Red enemies chase the player, and green enemies shoot at the player.
     *
     * @param x the starting x-position of the enemy
     * @param y the starting y-position of the enemy
     * @param enemyType the type of enemy, either "RED" or "GREEN"
     */
    public Enemy(double x, double y, String enemyType) {

        this.enemyType = enemyType;

        enemyCircle = new Circle(12);

        // Red enemy chases the player
        if (enemyType.equals("RED")) {
            enemyCircle.setFill(Color.RED);
            speed = 1.5;
        }

        // Green enemy shoots at the player
        else if (enemyType.equals("GREEN")) {
            enemyCircle.setFill(Color.LIMEGREEN);
            speed = 0;
        }

        enemyCircle.setCenterX(x);
        enemyCircle.setCenterY(y);

        detectionRange = 300;
        shootCooldown = 0;
    }

    /**
     * Returns the enemy's circle shape so it can be added to the game screen.
     *
     * @return the Circle object representing the enemy
     */
    public Circle getEnemyCircle() {
        return enemyCircle;
    }

    /**
     * Updates the enemy's behavior during the game.
     * If the player is within detection range, red enemies move toward the player
     * and green enemies shoot at the player. This method is called repeatedly
     * by the game loop.
     *
     * @param playerTriangle the player's triangle shape
     * @param walls the list of wall rectangles used for collision
     * @param root the root group where projectiles are added
     * @param enemyProjectiles the list of enemy projectiles currently in the game
     */
    public void update(Polygon playerTriangle, ArrayList<Rectangle> walls,
                       Group root, ArrayList<EnemyProjectile> enemyProjectiles) {

        if (canSeePlayer(playerTriangle)) {

            if (enemyType.equals("RED")) {
                moveTowardPlayer(playerTriangle, walls);
            }

            else if (enemyType.equals("GREEN")) {
                shootAtPlayer(playerTriangle, root, enemyProjectiles);
            }
        }

        if (shootCooldown > 0) {
            shootCooldown--;
        }
    }

    /**
     * Checks whether the player is close enough for the enemy to react.
     *
     * @param playerTriangle the player's triangle shape
     * @return true if the player is within the enemy's detection range, otherwise false
     */
    private boolean canSeePlayer(Polygon playerTriangle) {

        double enemyX = enemyCircle.getCenterX();
        double enemyY = enemyCircle.getCenterY();

        double playerX = playerTriangle.getBoundsInParent().getCenterX();
        double playerY = playerTriangle.getBoundsInParent().getCenterY();

        double dx = playerX - enemyX;
        double dy = playerY - enemyY;

        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance <= detectionRange;
    }

    /**
     * Moves a red enemy toward the player.
     * If the enemy touches a wall after moving, it returns to its old position.
     *
     * @param playerTriangle the player's triangle shape
     * @param walls the list of wall rectangles used for collision
     */
    private void moveTowardPlayer(Polygon playerTriangle, ArrayList<Rectangle> walls) {

        double oldX = enemyCircle.getCenterX();
        double oldY = enemyCircle.getCenterY();

        double enemyX = enemyCircle.getCenterX();
        double enemyY = enemyCircle.getCenterY();

        double playerX = playerTriangle.getBoundsInParent().getCenterX();
        double playerY = playerTriangle.getBoundsInParent().getCenterY();

        double dx = playerX - enemyX;
        double dy = playerY - enemyY;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            enemyCircle.setCenterX(enemyX + (dx / distance) * speed);
            enemyCircle.setCenterY(enemyY + (dy / distance) * speed);
        }

        if (isTouchingWall(walls)) {
            enemyCircle.setCenterX(oldX);
            enemyCircle.setCenterY(oldY);
        }
    }

    /**
     * Allows a green enemy to shoot a projectile toward the player.
     * The enemy can only shoot when its cooldown has reached zero.
     *
     * @param playerTriangle the player's triangle shape
     * @param root the root group where the projectile is added
     * @param enemyProjectiles the list that stores enemy projectiles
     */
    private void shootAtPlayer(Polygon playerTriangle, Group root,
                               ArrayList<EnemyProjectile> enemyProjectiles) {

        // Only shoot if cooldown is finished
        if (shootCooldown <= 0) {

            double enemyX = enemyCircle.getCenterX();
            double enemyY = enemyCircle.getCenterY();

            double playerX = playerTriangle.getBoundsInParent().getCenterX();
            double playerY = playerTriangle.getBoundsInParent().getCenterY();

            EnemyProjectile projectile = new EnemyProjectile(enemyX, enemyY, playerX, playerY);

            enemyProjectiles.add(projectile);
            root.getChildren().add(projectile.getProjectileCircle());

            // Higher number = slower shooting
            shootCooldown = 90;
        }
    }

    /**
     * Checks if the enemy is touching any wall in the maze.
     *
     * @param walls the list of wall rectangles used for collision
     * @return true if the enemy is touching a wall, otherwise false
     */
    private boolean isTouchingWall(ArrayList<Rectangle> walls) {

        for (Rectangle wall : walls) {
            if (enemyCircle.getBoundsInParent().intersects(wall.getBoundsInParent())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the enemy's bounds for collision detection.
     *
     * @return the bounds of the enemy circle
     */
    public Bounds getBounds() {
        return enemyCircle.getBoundsInParent();
    }
}