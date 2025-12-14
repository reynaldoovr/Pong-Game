package pong.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CollisionTest {
    
    // Decision/Condition Coverage Tests
    @Test
    public void testNoCollisionCompleteMiss() {
        Ball ball = new Ball(0, 0, 5);
        Paddle paddle = new Paddle(100, 100, 20, 60);
        
        assertFalse(ball.checkCollision(paddle), 
            "Ball completely missing paddle should not detect collision");
    }

    @Test
    public void testCollisionVerticalAlignedOnly() {
        Ball ball = new Ball(0, 120, 5);
        Paddle paddle = new Paddle(100, 100, 20, 60);
        
        assertFalse(ball.checkCollision(paddle), 
            "Only vertical alignment should not trigger collision");
    }

    @Test
    public void testCollisionHorizontalAlignedOnly() {
        Ball ball = new Ball(110, 0, 5);
        Paddle paddle = new Paddle(100, 100, 20, 60);
        
        assertFalse(ball.checkCollision(paddle), 
            "Only horizontal alignment should not trigger collision");
    }

    @Test
    public void testCollisionBothAligned() {
        Ball ball = new Ball(110, 120, 5);
        Paddle paddle = new Paddle(100, 100, 20, 60);
        
        assertTrue(ball.checkCollision(paddle), 
            "Both vertical and horizontal alignment should trigger collision");
    }

    // Path Coverage Tests
    @Test
    public void testCollisionEdgeCases() {
        Ball ball = new Ball(105, 105, 5);
        Paddle paddle = new Paddle(100, 100, 20, 60);
        
        assertTrue(ball.checkCollision(paddle), 
            "Ball touching paddle edge should detect collision");
        
        ball = new Ball(125, 165, 5);
        assertTrue(ball.checkCollision(paddle), 
            "Ball touching paddle corner should detect collision");
    }
}