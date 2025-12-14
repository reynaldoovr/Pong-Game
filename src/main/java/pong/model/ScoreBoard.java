package pong.model;

public interface ScoreBoard {
    void updateScore(int leftScore, int rightScore);
    int getLeftScore();
    int getRightScore();
    boolean isGameOver();
    String getWinner();
    void reset();
}
