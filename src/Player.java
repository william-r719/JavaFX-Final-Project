import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Player {

    // The player shape
    private Polygon playerTriangle;

    public Player() {

        // Create a smaller triangle player inside the Start room
        playerTriangle = new Polygon();

        playerTriangle.getPoints().addAll(
                165.0, 575.0,  // front point
                150.0, 605.0,  // bottom-left point
                180.0, 605.0   // bottom-right point
        );

        // Set player color
        playerTriangle.setFill(Color.ROYALBLUE);
    }

    public Polygon getPlayerTriangle() {
        return playerTriangle;
    }
}