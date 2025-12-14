package pong.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTests {
    @Test
    public void testBallConstructorContracts() {
        // Valid construction
        Ball ball = new Ball(100, 100, 10);
        
        // Negative radius should fail
        assertThrows(AssertionError.class, () -> new Ball(100, 100, -5));
    }

    @Test
    public void testPaddleConstructorContracts() {
        // Valid construction
        Paddle paddle = new Paddle(100, 100, 20, 60);
        
        // Invalid dimensions should fail
        assertThrows(AssertionError.class, () -> new Paddle(100, 100, -20, 60));
        assertThrows(AssertionError.class, () -> new Paddle(100, 100, 20, -60));
    }

    @Test
    public void testBallMoveContract() {
        Ball ball = new Ball(100, 100, 10);
        ball.setVelocityX(5);
        ball.setVelocityY(-3);
        ball.move();
        assertEquals(105, ball.getX());
        assertEquals(97, ball.getY());
    }

    @Test
    public void testPaddleMoveContract() {
        Paddle paddle = new Paddle(100, 100, 20, 60);
        paddle.setVelocity(5);
        paddle.move();
        assertEquals(105, paddle.getY());
    }

    @Test
    public void testCollisionContract() {
        Ball ball = new Ball(100, 100, 10);
        
        // Null paddle should fail contract
        assertThrows(AssertionError.class, () -> ball.checkCollision(null));
    }

    @Test
    public void testVelocityContracts() {
        Ball ball = new Ball(100, 100, 10);
        Paddle paddle = new Paddle(100, 100, 20, 60);

        // Invalid velocities should fail
        assertThrows(AssertionError.class, () -> ball.setVelocityX(Double.POSITIVE_INFINITY));
        assertThrows(AssertionError.class, () -> ball.setVelocityY(Double.NaN));
        assertThrows(AssertionError.class, () -> paddle.setVelocity(Double.POSITIVE_INFINITY));
    }
}