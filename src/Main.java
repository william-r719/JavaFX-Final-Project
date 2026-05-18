import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    // Scene size constants
    private final int SCENE_WIDTH = 1176;
    private final int SCENE_HEIGHT = 664;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Root container for the main menu
        Group root = new Group();

        // Main menu scene with a black background
        Scene mainMenuScene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, Color.BLACK);

        // Set up the main window
        primaryStage.setTitle("Alien CrashOut");
        primaryStage.setResizable(false);

        // Create the game title
        Text titleText = new Text("Alien Crash-Out");
        titleText.setFont(Font.font("Verdana", 50));
        titleText.setFill(Color.LIMEGREEN);

        // Center the title horizontally and place it near the top
        titleText.setX((SCENE_WIDTH - titleText.getLayoutBounds().getWidth()) / 2);
        titleText.setY(100);

        // Create the Play button
        Button playButton = new Button("Play");
        playButton.setPrefWidth(120);
        playButton.setPrefHeight(40);
        playButton.setLayoutX((SCENE_WIDTH - 120) / 2);
        playButton.setLayoutY(300);

        // Create the Quit button
        Button quitButton = new Button("Quit");
        quitButton.setPrefWidth(120);
        quitButton.setPrefHeight(40);
        quitButton.setLayoutX((SCENE_WIDTH - 120) / 2);
        quitButton.setLayoutY(360);

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