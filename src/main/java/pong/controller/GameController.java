package pong.controller;

import pong.model.GameModel;
import pong.view.GameView;

public class GameController {
    private GameModel model;
    private GameView view;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }

    public void update() {
        // Update game state
        model.update();

        // Update view with new state
        view.updateBallPosition(
            model.getBall().getX(),
            model.getBall().getY()
        );
        
        view.updatePaddlePositions(
            model.getLeftPaddle().getY(),
            model.getRightPaddle().getY()
        );
        
        view.updateScore(
            model.getLeftScore(),
            model.getRightScore()
        );
        
        view.render();
    }

    public void setLeftPaddleVelocity(double velocity) {
        model.getLeftPaddle().setVelocity(velocity);
    }

    public void setRightPaddleVelocity(double velocity) {
        model.getRightPaddle().setVelocity(velocity);
    }
}