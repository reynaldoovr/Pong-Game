package pong.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MockScoreBoardTest {
    
    private MockScoreBoard scoreBoard;
    
    @BeforeEach
    void setUp() {
        scoreBoard = new MockScoreBoard();
    }
    
    @Test
    void testScoreUpdates() {
        scoreBoard.updateScore(3, 2);
        assertTrue(scoreBoard.wasUpdateScoreCalled());
        assertEquals(3, scoreBoard.getLeftScore());
        assertEquals(2, scoreBoard.getRightScore());
    }
    
    @Test
    void testGameOver() {
        // No game over yet
        scoreBoard.updateScore(3, 4);
        assertFalse(scoreBoard.isGameOver());
        
        // Game over
        scoreBoard.updateScore(5, 4);
        assertTrue(scoreBoard.isGameOver());
        assertEquals("Left Player", scoreBoard.getWinner());
    }
    
    @Test
    void testReset() {
        scoreBoard.updateScore(3, 2);
        scoreBoard.reset();
        assertTrue(scoreBoard.wasResetCalled());
        assertEquals(0, scoreBoard.getLeftScore());
        assertEquals(0, scoreBoard.getRightScore());
    }
}