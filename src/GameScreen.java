import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * The GameScreen class creates and controls the playable game screen.
 * It sets up the level, player, enemies, enemy projectiles, controls,
 * and the main game loop.
 *
 * @author William Rodriguez
 */
public class GameScreen {

    private final int SCENE_WIDTH = 1300;
    private final int SCENE_HEIGHT = 750;

    private Stage primaryStage;
    private Scene gameScene;

    // Wrapper centers and scales the game
    private StackPane screenRoot;

    // Game root holds all actual game objects
    private Group root;

    private Player player;
    private Level level;

    private ArrayList<Enemy> enemies;
    private ArrayList<EnemyProjectile> enemyProjectiles;

    private AnimationTimer gameLoop;

    // Stops the game from resetting or winning more than once
    private boolean gameOver;

    /**
     * Creates a new GameScreen object.
     * The constructor builds the playable screen, creates the level and player,
     * adds all red and green enemies, connects the controls, and starts the game loop.
     *
     * @param primaryStage the main window where the game screen is displayed
     */
    public GameScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        gameOver = false;

        // Get the user's screen size
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        double fullWidth = screenBounds.getWidth();
        double fullHeight = screenBounds.getHeight();

        // This root fills the full screen
        screenRoot = new StackPane();
        screenRoot.setStyle("-fx-background-color: white;");

        // This root holds the actual game objects
        root = new Group();

        // Scale the game to fit the full screen
        double scaleX = fullWidth / SCENE_WIDTH;
        double scaleY = fullHeight / SCENE_HEIGHT;
        double scale = Math.min(scaleX, scaleY);

        root.setScaleX(scale);
        root.setScaleY(scale);

        // Add the game root into the full screen wrapper
        screenRoot.getChildren().add(root);

        // Create the full screen scene
        gameScene = new Scene(screenRoot, fullWidth, fullHeight, Color.WHITE);

        /*
         * IMPORTANT:
         * Do not call primaryStage.setFullScreen(true) here.
         * Fullscreen is handled once in Main.java.
         * This prevents the annoying fullscreen message from appearing after resets.
         */

        // Create the level/maze
        level = new Level();

        // Add the maze to the screen first
        root.getChildren().add(level.getLevelGroup());

        // Create the player
        player = new Player();

        // Add the player triangle on top of the maze
        root.getChildren().add(player.getPlayerTriangle());

        // Create enemy lists
        enemies = new ArrayList<>();
        enemyProjectiles = new ArrayList<>();

        /*
         * RED ENEMIES
         * These enemies chase the player.
         */
        //enemies.add(new Enemy(60, 85, "RED"));
        //enemies.add(new Enemy(470, 95, "RED"));
        //enemies.add(new Enemy(185, 470, "RED"));
        //enemies.add(new Enemy(470, 600, "RED"));
        //enemies.add(new Enemy(900, 585, "RED"));
        //enemies.add(new Enemy(1170, 595, "RED"));
        //enemies.add(new Enemy(1160, 500, "RED"));

        /*
         * GREEN ENEMIES
         * These enemies stand still and shoot at the player.
         */
        //enemies.add(new Enemy(350, 85, "GREEN"));
        //enemies.add(new Enemy(590, 90, "GREEN"));
       // enemies.add(new Enemy(830, 100, "GREEN"));
        //enemies.add(new Enemy(825, 230, "GREEN"));
       // enemies.add(new Enemy(870, 390, "GREEN"));

        //enemies.add(new Enemy(260, 485, "GREEN"));
        //enemies.add(new Enemy(350, 450, "GREEN"));
        //enemies.add(new Enemy(350, 530, "GREEN"));
        //enemies.add(new Enemy(480, 460, "GREEN"));
        //enemies.add(new Enemy(550, 530, "GREEN"));
        //enemies.add(new Enemy(685, 520, "GREEN"));
        //enemies.add(new Enemy(840, 540, "GREEN"));

        //enemies.add(new Enemy(1030, 445, "GREEN"));
        //enemies.add(new Enemy(1025, 600, "GREEN"));
        enemies.add(new Enemy(1165, 320, "GREEN"));

        // Add all enemy shapes to the screen
        for (Enemy enemy : enemies) {
            root.getChildren().add(enemy.getEnemyCircle());
        }

        /*
         * Connect keyboard movement, mouse aim, shooting, wall collision,
         * enemy collision for player projectiles, and the secret P close button.
         */
        new Controls(gameScene, root, player.getPlayerTriangle(), level.getWalls(), enemies, primaryStage);

        // Start the game loop
        startGameLoop();
    }

    /**
     * Starts the main game loop.
     * The game loop updates enemies, enemy projectiles, collision checks,
     * win conditions, and reset conditions.
     */
    private void startGameLoop() {

        gameLoop = new AnimationTimer() {

            /**
             * Runs every frame while the game is active.
             * This method updates enemies, checks for player damage,
             * removes inactive projectiles, and checks if the player has won.
             *
             * @param now the current timestamp in nanoseconds
             */
            @Override
            public void handle(long now) {

                if (gameOver) {
                    return;
                }

                // If all enemies are dead, player wins
                if (enemies.isEmpty()) {
                    showWinAndReturnHome();
                    return;
                }

                // Update all enemies
                for (int i = enemies.size() - 1; i >= 0; i--) {

                    Enemy enemy = enemies.get(i);

                    enemy.update(player.getPlayerTriangle(), level.getWalls(), root, enemyProjectiles);

                    // If any enemy touches the player, reset the screen
                    if (enemy.getBounds().intersects(player.getPlayerTriangle().getBoundsInParent())) {
                        resetScreen();
                        return;
                    }
                }

                // Update enemy projectiles
                for (int i = enemyProjectiles.size() - 1; i >= 0; i--) {

                    EnemyProjectile projectile = enemyProjectiles.get(i);

                    projectile.update(level.getWalls(), player.getPlayerTriangle());

                    // If green projectile hits player, reset the screen
                    if (projectile.hitPlayer()) {
                        resetScreen();
                        return;
                    }

                    // Remove projectile if inactive
                    if (!projectile.isActive()) {
                        root.getChildren().remove(projectile.getProjectileCircle());
                        enemyProjectiles.remove(i);
                    }
                }

                // Check again in case the last enemy was killed during this frame
                if (enemies.isEmpty()) {
                    showWinAndReturnHome();
                }
            }
        };

        gameLoop.start();
    }

    /**
     * Shows the win screen and then returns the player to the main menu.
     * This method is called when all enemies have been defeated.
     */
    private void showWinAndReturnHome() {

        gameOver = true;
        gameLoop.stop();

        // Clear the screen
        root.getChildren().clear();

        // Create YOU WIN text
        Text winText = new Text("YOU WIN!");
        winText.setFont(Font.font("Verdana", 80));
        winText.setFill(Color.LIMEGREEN);

        // Center the text inside the game area
        winText.setX((SCENE_WIDTH - winText.getLayoutBounds().getWidth()) / 2);
        winText.setY(SCENE_HEIGHT / 2.0);

        root.getChildren().add(winText);

        // Wait 2 seconds, then return to the main menu
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> Main.showMainMenu(primaryStage));
        pause.play();
    }

    /**
     * Resets the playable screen after the player is hit.
     * This method creates a fresh GameScreen and displays it.
     */
    private void resetScreen() {

        if (gameOver) {
            return;
        }

        gameOver = true;

        // Stop the current game loop before resetting
        gameLoop.stop();

        // Create a fresh GameScreen
        GameScreen newGameScreen = new GameScreen(primaryStage);

        // Show the fresh screen
        newGameScreen.show();
    }

    /**
     * Displays the game scene on the main window.
     * It also requests focus so the keyboard controls work properly.
     */
    public void show() {

        // Put the game scene into the main window
        primaryStage.setScene(gameScene);

        /*
         * Do not force fullscreen here.
         * Main.java already handles fullscreen.
         */

        // Request focus so WASD controls work
        gameScene.getRoot().requestFocus();
    }
}