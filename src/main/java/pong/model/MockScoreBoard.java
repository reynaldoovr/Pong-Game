package pong.model;

public class MockScoreBoard implements ScoreBoard {
    private int leftScore = 0;
    private int rightScore = 0;
    private boolean updateScoreCalled = false;
    private boolean resetCalled = false;
    private static final int WINNING_SCORE = 5;

    @Override
    public void updateScore(int leftScore, int rightScore) {
        this.leftScore = leftScore;
        this.rightScore = rightScore;
        this.updateScoreCalled = true;
    }

    @Override
    public int getLeftScore() {
        return leftScore;
    }

    @Override
    public int getRightScore() {
        return rightScore;
    }

    @Override
    public boolean isGameOver() {
        return leftScore >= WINNING_SCORE || rightScore >= WINNING_SCORE;
    }

    @Override
    public String getWinner() {
        if (!isGameOver()) return "Game in progress";
        return leftScore >= WINNING_SCORE ? "Left Player" : "Right Player";
    }

    @Override
    public void reset() {
        leftScore = 0;
        rightScore = 0;
        resetCalled = true;
    }

    // Métodos específicos del mock para verificación
    public boolean wasUpdateScoreCalled() {
        return updateScoreCalled;
    }

    public boolean wasResetCalled() {
        return resetCalled;
    }
}