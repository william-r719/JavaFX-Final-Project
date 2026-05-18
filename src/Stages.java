/*
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Stages  {

    // public static void main(String[] args){
    //     launch(args);
    // }

    // @Override
    public void start(Stage primaryStage) throws Exception {

        //Stage stage = new Stage();
        Group root = new Group();

        Scene scene = new Scene(root, 1176, 664, Color.BLACK);

        // Add icon image of png in src folder down below
        //Image icon = new Image("");
        // Method down below will actually show icon (little top left corner picture)
        //primaryStage.getIcons().add(icon);

        primaryStage.setTitle("Alien CrashOut");

        //primaryStage.setWidth(1176);
        //primaryStage.setHeight(664);

        primaryStage.setResizable(false);

        Text text = new Text();
        text.setText("Alien Crash-Out");
        text.setFont(Font.font("Verdana",50));
        text.setX((1176 - text.getLayoutBounds().getWidth()) / 2); // sets title to top middle
        text.setY(100);

        // StackPane version for centering text:
        //StackPane.setAlignment(text, Pos.TOP_CENTER);
        //text.setTranslateY(50);

        text.setFill(Color.LIMEGREEN);

        // TODO : add comment description for the following methods
        //primaryStage.setX();
        //primaryStage.setY();
        //primaryStage.setFullScreenExitHint("YOU CAN'T ESCAPE unless you press q");
        //primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));

        Rectangle rectangle = new Rectangle();
        rectangle.setX(800);
        rectangle.setY(100);
        rectangle.setWidth(188);
        rectangle.setHeight(269);
        rectangle.setFill(Color.WHITE);
        rectangle.setStrokeWidth(5);
        rectangle.setStroke(Color.BLACK);

        Circle circle = new Circle();
        circle.setCenterX(962);
        circle.setCenterY(191);
        circle.setRadius(50);
        circle.setFill(Color.GREEN);

        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                800.0, 369.0,   // bottom-left of door
                988.0, 369.0,   // bottom-right of door
                894.0, 560.0    // point below the center of the door
        );
        triangle.setFill(Color.LIGHTYELLOW);
        triangle.setOpacity(0.6);

        // ---------------- OLD TRIANGLE PRACTICE ----------------
        //Polygon triangle = new Polygon();
        //triangle.getPoints().setAll(
        //        200.0, 200.0,
        //        300.0, 300.0,
        //        200.0, 300.0
        //);
        //triangle.setFill(Color.YELLOW);

        // ---------------- OLD WAY ----------------
        // This shows the full circle, even the part outside the door.
        //root.getChildren().add(circle);

        // ---------------- NEW WAY ----------------
        // Put the circle inside its own group.
        Group alienGroup = new Group(circle);

        // Create a clipping rectangle the same size as the door.
        Rectangle clipRect = new Rectangle();
        clipRect.setX(rectangle.getX());
        clipRect.setY(rectangle.getY());
        clipRect.setWidth(rectangle.getWidth());
        clipRect.setHeight(rectangle.getHeight());

        // Apply the clip so only the part of the circle inside the door shows.
        alienGroup.setClip(clipRect);

        // Creating the Play button.
        Button playButton = new Button("Play");
        playButton.setLayoutX((1176 - 120) / 2);
        playButton.setLayoutY(300);
        playButton.setPrefWidth(120);
        playButton.setPrefHeight(40);

        // Creating the Quit button.
        Button quitButton = new Button("Quit");
        quitButton.setLayoutX((1176 - 120) / 2);
        quitButton.setLayoutY(360);
        quitButton.setPrefWidth(120);
        quitButton.setPrefHeight(40);

        // Makes the Quit button close the game window.
        quitButton.setOnAction(event -> primaryStage.close());

        // ---------------- OLD ADDING OBJECTS PRACTICE ----------------
        //root.getChildren().add(triangle);
        //root.getChildren().add(rectangle);
        //root.getChildren().add(circle);
        //root.getChildren().add(text);

        // Add objects in order from back to front.
        // The first object added appears behind the later objects.
        root.getChildren().add(triangle);      // light behind everything
        root.getChildren().add(rectangle);     // door
        root.getChildren().add(alienGroup);    // clipped alien
        root.getChildren().add(text);          // title text
        root.getChildren().add(playButton);    // play button
        root.getChildren().add(quitButton);    // quit button

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
*/