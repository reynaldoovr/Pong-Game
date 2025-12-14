package pong.view;

public interface GameView {
    void updateBallPosition(double x, double y);
    void updatePaddlePositions(double leftY, double rightY);
    void updateScore(int leftScore, int rightScore);
    void render();
}