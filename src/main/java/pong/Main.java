package pong;

import pong.controller.GameController;
import pong.model.GameModel;
import pong.model.GameMode;
import pong.view.GameView;
import pong.view.GameViewImpl;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.text.Font;

public class Main extends Application {
    private GameModel model;
    private Scene menuScene;
    private Scene gameScene;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createMenuScene();
        
        primaryStage.setTitle("Pong Game");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private void createMenuScene() {
        VBox menuBox = new VBox(20);  // 20 is spacing between elements
        menuBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("PONG");
        titleLabel.setFont(Font.font(48));

        Label authors = new Label("By Roman and Reynaldo");
        authors.setFont(Font.font(24));


        Label subtitleLabel = new Label("Select Game Mode");
        subtitleLabel.setFont(Font.font(24));

        Button classicButton = createModeButton("Classic Mode", GameMode.CLASSIC);
        Button speedUpButton = createModeButton("Speed Up Mode", GameMode.SPEED_UP);
        Button firstToFiveButton = createModeButton("First to 5", GameMode.FIRST_TO_FIVE);
        Button timeTrialButton = createModeButton("Time Trial", GameMode.TIME_TRIAL);

        menuBox.getChildren().addAll(
            titleLabel,
            authors,
            subtitleLabel,
            classicButton,
            speedUpButton,
            firstToFiveButton,
            timeTrialButton
        );

        menuScene = new Scene(menuBox, 800, 600);
    }

    private Button createModeButton(String text, GameMode mode) {
        Button button = new Button(text);
        button.setFont(Font.font(20));
        button.setPrefWidth(200);
        button.setOnAction(e -> startGame(mode));
        return button;
    }

    private void startGame(GameMode mode) {
        Canvas canvas = new Canvas(800, 600);
        model = new GameModel();
        model.setGameMode(mode);
        GameView view = new GameViewImpl(canvas, model);
        GameController controller = new GameController(model, view);

        StackPane root = new StackPane(canvas);
        gameScene = new Scene(root, 800, 600);

        // Handle keyboard input
        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    controller.setLeftPaddleVelocity(-5);
                    break;
                case S:
                    controller.setLeftPaddleVelocity(5);
                    break;
                case UP:
                    controller.setRightPaddleVelocity(-5);
                    break;
                case DOWN:
                    controller.setRightPaddleVelocity(5);
                    break;
                case ESCAPE:
                    returnToMenu();
                    break;
                case R:
                    model.resetGame();
                    break;
            }
        });

        gameScene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.S) {
                controller.setLeftPaddleVelocity(0);
            }
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                controller.setRightPaddleVelocity(0);
            }
        });

        // Game loop
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                controller.update();
                if (model.isGameOver()) {
                    stop();
                    returnToMenu();
                }
            }
        }.start();

        // Set initial ball velocity
        model.getBall().setVelocityX(5);
        model.getBall().setVelocityY(5);

        primaryStage.setScene(gameScene);
    }

    private void returnToMenu() {
        primaryStage.setScene(menuScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}