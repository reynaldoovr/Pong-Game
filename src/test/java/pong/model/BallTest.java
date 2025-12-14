package pong.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Black Box Testing for Ball class
 * 
 * Constructor Ball(double x, double y, double radius)
 * Equivalent Partitions:
 * - x: any double value (valid game screen coordinates)
 * - y: any double value (valid game screen coordinates)
 * - radius: positive double values (valid ball size)
 * 
 * Boundary Values:
 * - x: 0.0 (left screen border), screen width (right border)
 * - y: 0.0 (top screen border), screen height (bottom border)
 * - radius: minimum size (1.0), maximum reasonable size (50.0)
 */
public class BallTest {
    
    // Equivalent partition tests for constructor
    @Test
    public void testBallInitializationNormalValues() {
        // Testing with normal values within valid partitions
        double x = 100.0;
        double y = 100.0;
        double radius = 10.0;

        Ball ball = new Ball(x, y, radius);

        assertEquals(x, ball.getX(), "Ball X position should match constructor argument");
        assertEquals(y, ball.getY(), "Ball Y position should match constructor argument");
        assertEquals(radius, ball.getRadius(), "Ball radius should match constructor argument");
    }

    // Boundary value tests for constructor
    @Test
    public void testBallInitializationBoundaryValues() {
        // Testing boundary values for screen coordinates
        Ball ballAtOrigin = new Ball(0.0, 0.0, 10.0);
        assertEquals(0.0, ballAtOrigin.getX(), "Ball should accept minimum X coordinate");
        assertEquals(0.0, ballAtOrigin.getY(), "Ball should accept minimum Y coordinate");

        // Testing boundary values for radius
        Ball ballMinRadius = new Ball(100.0, 100.0, 1.0);
        assertEquals(1.0, ballMinRadius.getRadius(), "Ball should accept minimum radius");
    }

    /**
     * Movement Tests
     * 
     * Method: void move()
     * Equivalent Partitions for velocity:
     * - Positive velocity (moving right/down)
     * - Negative velocity (moving left/up)
     * - Zero velocity (stationary)
     */
    @Test
    public void testBallMovementPositiveVelocity() {
        Ball ball = new Ball(100.0, 100.0, 10.0);
        ball.setVelocityX(5.0);
        ball.setVelocityY(5.0);
        
        ball.move();
        
        assertEquals(105.0, ball.getX(), "Ball should move right with positive X velocity");
        assertEquals(105.0, ball.getY(), "Ball should move down with positive Y velocity");
    }

    @Test
    public void testBallMovementNegativeVelocity() {
        Ball ball = new Ball(100.0, 100.0, 10.0);
        ball.setVelocityX(-5.0);
        ball.setVelocityY(-5.0);
        
        ball.move();
        
        assertEquals(95.0, ball.getX(), "Ball should move left with negative X velocity");
        assertEquals(95.0, ball.getY(), "Ball should move up with negative Y velocity");
    }

    @Test
    public void testBallMovementZeroVelocity() {
        Ball ball = new Ball(100.0, 100.0, 10.0);
        ball.setVelocityX(0.0);
        ball.setVelocityY(0.0);
        
        ball.move();
        
        assertEquals(100.0, ball.getX(), "Ball should not move with zero X velocity");
        assertEquals(100.0, ball.getY(), "Ball should not move with zero Y velocity");
    }

    /**
     * Direction Reversal Tests
     * 
     * Methods: void reverseX(), void reverseY()
     * Equivalent Partitions for current velocity:
     * - Positive velocity becomes negative
     * - Negative velocity becomes positive
     * - Zero velocity remains zero
     */
    @Test
    public void testReverseDirectionFromPositive() {
        Ball ball = new Ball(100.0, 100.0, 10.0);
        ball.setVelocityX(5.0);
        ball.setVelocityY(5.0);
        
        ball.reverseX();
        ball.reverseY();
        
        assertEquals(-5.0, ball.getVelocityX(), "Positive X velocity should become negative");
        assertEquals(-5.0, ball.getVelocityY(), "Positive Y velocity should become negative");
    }

    @Test
    public void testReverseDirectionFromNegative() {
        Ball ball = new Ball(100.0, 100.0, 10.0);
        ball.setVelocityX(-5.0);
        ball.setVelocityY(-5.0);
        
        ball.reverseX();
        ball.reverseY();
        
        assertEquals(5.0, ball.getVelocityX(), "Negative X velocity should become positive");
        assertEquals(5.0, ball.getVelocityY(), "Negative Y velocity should become positive");
    }
}