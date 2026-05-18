import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Projectile {

    private Circle bullet;

    private double speed = 8;
    private double directionX;
    private double directionY;

    public Projectile(Group root, double startX, double startY, double targetX, double targetY) {

        // Create the bullet at the player's position
        bullet = new Circle(startX, startY, 5);
        bullet.setFill(Color.RED);

        // Find the direction from the player to the mouse
        double distanceX = targetX - startX;
        double distanceY = targetY - startY;

        // Find the full distance to the target
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        // Normalize the direction so the bullet moves smoothly
        directionX = distanceX / distance;
        directionY = distanceY / distance;

        // Add the bullet to the screen
        root.getChildren().add(bullet);

        // Move the bullet every frame
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                bullet.setCenterX(bullet.getCenterX() + directionX * speed);
                bullet.setCenterY(bullet.getCenterY() + directionY * speed);
            }
        };

        timer.start();
    }
}
