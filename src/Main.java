import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * The Main class starts the Alien Crash-Out game.
 * It creates the main menu screen and allows the player to either start
 * the game or quit the program.
 *
 * @author William Rodriguez
 */
public class Main extends Application {

    /**
     * The main method launches the JavaFX application.
     *
     * @param args command-line arguments that are not used in this program
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application by showing the main menu.
     *
     * @param primaryStage the main window used to display the game
     */
    @Override
    public void start(Stage primaryStage) {
        showMainMenu(primaryStage);
    }

    /**
     * Creates and displays the main menu screen.
     * The main menu includes the game title, a Play button, and a Quit button.
     * Pressing Play opens the playable game screen, while pressing Quit closes
     * the application.
     *
     * @param primaryStage the main window where the main menu scene is displayed
     */
    public static void showMainMenu(Stage primaryStage) {

        // Get the user's screen size
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        double sceneWidth = screenBounds.getWidth();
        double sceneHeight = screenBounds.getHeight();

        // Root container for the main menu
        Group root = new Group();

        // Main menu scene with a black background
        Scene mainMenuScene = new Scene(root, sceneWidth, sceneHeight, Color.BLACK);

        // Set up the main window
        primaryStage.setTitle("Alien CrashOut");
        primaryStage.setResizable(false);

        /*
         * Fullscreen setup.
         * The exit hint is hidden so the annoying "Press ESC" message does not show.
         * The if statement prevents fullscreen from being forced again and again.
         */
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        if (!primaryStage.isFullScreen()) {
            primaryStage.setFullScreen(true);
        }

        // Create the game title
        Text titleText = new Text("Maze Game");
        titleText.setFont(Font.font("Verdana", 70));
        titleText.setFill(Color.LIMEGREEN);

        // Center the title horizontally and place it near the top
        titleText.setX((sceneWidth - titleText.getLayoutBounds().getWidth()) / 2);
        titleText.setY(sceneHeight * 0.20);

        // Create the Play button
        Button playButton = new Button("Play");
        playButton.setPrefWidth(160);
        playButton.setPrefHeight(50);
        playButton.setLayoutX((sceneWidth - 160) / 2);
        playButton.setLayoutY(sceneHeight * 0.45);

        // Create the Quit button
        Button quitButton = new Button("Quit");
        quitButton.setPrefWidth(160);
        quitButton.setPrefHeight(50);
        quitButton.setLayoutX((sceneWidth - 160) / 2);
        quitButton.setLayoutY(sceneHeight * 0.53);

        // Load the first playable screen when Play is clicked
        playButton.setOnAction(event -> {
            GameScreen gameScreen = new GameScreen(primaryStage);
            gameScreen.show();
        });

        // Close the program when Quit is clicked
        quitButton.setOnAction(event -> primaryStage.close());

        // Add all menu objects to the screen
        root.getChildren().add(titleText);
        root.getChildren().add(playButton);
        root.getChildren().add(quitButton);

        // Display the main menu
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }
}