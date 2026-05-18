import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameScreen {

    private final int SCENE_WIDTH = 1300;
    private final int SCENE_HEIGHT = 750;

    private Stage primaryStage;
    private Scene gameScene;
    private Group root;

    private Player player;
    private Level level;

    public GameScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Create the root container for the playable screen
        root = new Group();

        // Create the game scene with a white background
        gameScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);

        // Create the level/maze
        level = new Level();

        // Add the maze to the screen first
        root.getChildren().add(level.getLevelGroup());

        // Create the player
        player = new Player();

        // Add the player triangle on top of the maze
        root.getChildren().add(player.getPlayerTriangle());

        // Connect keyboard movement, mouse aim, shooting, and wall collision
        new Controls(gameScene, root, player.getPlayerTriangle(), level.getWalls());
    }

    public void show() {
        // Put the game scene into the main window
        primaryStage.setScene(gameScene);

        // Request focus so WASD controls work
        gameScene.getRoot().requestFocus();
    }
}