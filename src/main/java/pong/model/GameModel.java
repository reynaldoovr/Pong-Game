package pong.model;

public class GameModel {
    private Ball ball;
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private ScoreBoard scoreBoard;
    private GameLogger logger;
    private GameMode gameMode;
    private double speedMultiplier = 1.0;
    private long gameStartTime;
    private static final int TIME_TRIAL_DURATION = 60; // seconds
    private static final int POINTS_TO_WIN = 5;
    public static final double SCREEN_WIDTH = 800;
    public static final double SCREEN_HEIGHT = 600;

    public GameModel() {
        // Initialize ball at center
        ball = new Ball(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 10);
        
        // Initialize paddles
        leftPaddle = new Paddle(50, SCREEN_HEIGHT/2 - 50, 20, 100);
        rightPaddle = new Paddle(SCREEN_WIDTH - 70, SCREEN_HEIGHT/2 - 50, 20, 100);
        
        // Initialize scoreBoard with mock (for now)
        this.scoreBoard = new MockScoreBoard();
        this.logger = null;  // Logger is optional in default constructor
        this.gameMode = GameMode.CLASSIC;
    }

    public GameModel(ScoreBoard scoreBoard, GameLogger logger) {
        // Initialize ball at center
        ball = new Ball(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 10);
        
        // Initialize paddles
        leftPaddle = new Paddle(50, SCREEN_HEIGHT/2 - 50, 20, 100);
        rightPaddle = new Paddle(SCREEN_WIDTH - 70, SCREEN_HEIGHT/2 - 50, 20, 100);
        
        this.scoreBoard = scoreBoard;
        this.logger = logger;
        this.gameMode = GameMode.CLASSIC;
        if (logger != null) {
            logger.logGameStart();
        }
    }

    public GameModel(ScoreBoard scoreBoard) {
        // Initialize ball at center
        ball = new Ball(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 10);
        
        // Initialize paddles
        leftPaddle = new Paddle(50, SCREEN_HEIGHT/2 - 50, 20, 100);
        rightPaddle = new Paddle(SCREEN_WIDTH - 70, SCREEN_HEIGHT/2 - 50, 20, 100);
        
        this.scoreBoard = scoreBoard;
        this.logger = null;  // No logger in this constructor
        this.gameMode = GameMode.CLASSIC;
    }

    public void update() {
        if (!isGameOver()) {
            movePaddles();
            moveBall();
            checkCollisions();
            checkScore();
        }
    }

    private void movePaddles() {
        leftPaddle.move();
        rightPaddle.move();
        
        // Keep paddles within screen bounds
        constrainPaddle(leftPaddle);
        constrainPaddle(rightPaddle);
    }

    private void constrainPaddle(Paddle paddle) {
        if (paddle.getY() < 0) {
            paddle.setVelocity(0);
            // Set Y to 0 if paddle goes above screen
        }
        if (paddle.getY() + paddle.getHeight() > SCREEN_HEIGHT) {
            paddle.setVelocity(0);
            // Set Y to keep paddle within screen bounds
        }
    }

    private void moveBall() {
        ball.move();
    }

    private void checkCollisions() {
        // Check paddle collisions
        if (ball.checkCollision(leftPaddle)) {
            ball.reverseX();
            if (gameMode == GameMode.SPEED_UP) {
                speedMultiplier *= 1.1;
                ball.setVelocityX(ball.getVelocityX() * 1.1);
                ball.setVelocityY(ball.getVelocityY() * 1.1);
            }
            if (logger != null) {
                logger.logCollision("leftPaddle");
            }
        } else if (ball.checkCollision(rightPaddle)) {
            ball.reverseX();
            if (gameMode == GameMode.SPEED_UP) {
                speedMultiplier *= 1.1;
                ball.setVelocityX(ball.getVelocityX() * 1.1);
                ball.setVelocityY(ball.getVelocityY() * 1.1);
            }
            if (logger != null) {
                logger.logCollision("rightPaddle");
            }
        }
        
        // Check top and bottom wall collisions
        if (ball.getY() - ball.getRadius() <= 0 || 
            ball.getY() + ball.getRadius() >= SCREEN_HEIGHT) {
            ball.reverseY();
            if (logger != null) {
                logger.logCollision("wall");
            }
        }
    }

    private void checkScore() {
        // Ball passed left paddle
        if (ball.getX() - ball.getRadius() <= 0) {
            scoreBoard.updateScore(scoreBoard.getLeftScore(), scoreBoard.getRightScore() + 1);
            if (logger != null) {
                logger.logScore("right", scoreBoard.getRightScore());
            }
            resetBall();
            if (isGameOver() && logger != null) {
                logger.logGameEnd(scoreBoard.getWinner());
            }
        }
        // Ball passed right paddle
        else if (ball.getX() + ball.getRadius() >= SCREEN_WIDTH) {
            scoreBoard.updateScore(scoreBoard.getLeftScore() + 1, scoreBoard.getRightScore());
            if (logger != null) {
                logger.logScore("left", scoreBoard.getLeftScore());
            }
            resetBall();
            if (isGameOver() && logger != null) {
                logger.logGameEnd(scoreBoard.getWinner());
            }
        }
    }

    private void resetBall() {
        ball = new Ball(SCREEN_WIDTH/2, SCREEN_HEIGHT/2, 10);
        ball.setVelocityX(5 * speedMultiplier);
        ball.setVelocityY(5 * speedMultiplier);
    }

    public void resetGame() {
        scoreBoard.reset();
        speedMultiplier = 1.0;
        if (gameMode == GameMode.TIME_TRIAL) {
            gameStartTime = System.currentTimeMillis();
        }
        resetBall();
    }

    public void setGameMode(GameMode mode) {
        this.gameMode = mode;
        resetGame();
        if (mode == GameMode.TIME_TRIAL) {
            gameStartTime = System.currentTimeMillis();
        }
    }

    public int getRemainingTime() {
        if (gameMode != GameMode.TIME_TRIAL) return 0;
        int elapsed = (int)(System.currentTimeMillis() - gameStartTime) / 1000;
        return Math.max(0, TIME_TRIAL_DURATION - elapsed);
    }

    public boolean isGameOver() {
        switch (gameMode) {
            case FIRST_TO_FIVE:
                return scoreBoard.getLeftScore() >= POINTS_TO_WIN || 
                       scoreBoard.getRightScore() >= POINTS_TO_WIN;
            case TIME_TRIAL:
                return (System.currentTimeMillis() - gameStartTime) / 1000 >= TIME_TRIAL_DURATION;
            default:
                return scoreBoard.isGameOver();
        }
    }

    // Getters
    public Ball getBall() { return ball; }
    public Paddle getLeftPaddle() { return leftPaddle; }
    public Paddle getRightPaddle() { return rightPaddle; }
    public int getLeftScore() { return scoreBoard.getLeftScore(); }
    public int getRightScore() { return scoreBoard.getRightScore(); }
    public String getWinner() { return scoreBoard.getWinner(); }
    public GameMode getGameMode() { return gameMode; }
    public double getSpeedMultiplier() { return speedMultiplier; }
}