package pong.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {
    
    private GameModel gameModel;
    private MockScoreBoard mockScoreBoard;
    
    @BeforeEach
    void setUp() {
        mockScoreBoard = new MockScoreBoard();
        gameModel = new GameModel(mockScoreBoard);
    }
    
    @Test
    void testScoreIncrementsWhenBallPassesPaddle() {
        // Move ball past right paddle
        Ball ball = gameModel.getBall();
        ball.setVelocityX(1000); // High velocity to ensure it passes paddle
        gameModel.update();
        
        assertEquals(1, mockScoreBoard.getLeftScore());
        assertTrue(mockScoreBoard.wasUpdateScoreCalled());
    }
    
    @Test
    void testGameEndsAtMaxScore() {
        // Simulate reaching max score
        mockScoreBoard.updateScore(5, 3);
        gameModel.update();
        
        assertTrue(gameModel.isGameOver());
        assertEquals("Left Player", gameModel.getWinner());
    }
    
    @Test
    void testGameReset() {
        mockScoreBoard.updateScore(3, 2);
        gameModel.resetGame();
        
        assertTrue(mockScoreBoard.wasResetCalled());
        assertEquals(0, gameModel.getLeftScore());
        assertEquals(0, gameModel.getRightScore());
    }
}