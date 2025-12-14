package pong.model;

public class Ball {
    private double x;
    private double y;
    private double radius;
    private double velocityX;
    private double velocityY;
    
    /**
     * @param x initial x position
     * @param y initial y position
     * @param radius ball radius
     * @throws IllegalArgumentException if radius <= 0
     */
    public Ball(double x, double y, double radius) {
        assert radius > 0 : "Ball radius must be positive";
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.velocityX = 0.0;
        this.velocityY = 0.0;
        assertInvariants();
    }
    
    private void assertInvariants() {
        assert radius > 0 : "Ball radius must remain positive";
        assert Double.isFinite(x) && Double.isFinite(y) : "Ball position must be finite";
        assert Double.isFinite(velocityX) && Double.isFinite(velocityY) : "Ball velocity must be finite";
    }
    
    /**
     * Updates ball position based on current velocity
     * @ensures position changes by velocity values
     */
    public void move() {
        double oldX = x;
        double oldY = y;
        
        x += velocityX;
        y += velocityY;
        
        assert x == oldX + velocityX : "X position must change by velocityX";
        assert y == oldY + velocityY : "Y position must change by velocityY";
        assertInvariants();
    }
    
    /**
     * Reverses horizontal direction
     * @ensures velocityX becomes its negative
     */
    public void reverseX() {
        double oldVelocityX = velocityX;
        velocityX = -velocityX;
        assert velocityX == -oldVelocityX : "velocityX must be negated";
        assertInvariants();
    }
    
    /**
     * Reverses vertical direction
     * @ensures velocityY becomes its negative
     */
    public void reverseY() {
        double oldVelocityY = velocityY;
        velocityY = -velocityY;
        assert velocityY == -oldVelocityY : "velocityY must be negated";
        assertInvariants();
    }
    
    /**
     * Checks collision with paddle
     * @param paddle the paddle to check collision with
     * @requires paddle != null
     * @return true if ball collides with paddle
     */
    public boolean checkCollision(Paddle paddle) {
        assert paddle != null : "Paddle cannot be null";
        
        boolean isVerticallyAligned = (y + radius >= paddle.getY() && 
                                     y - radius <= paddle.getY() + paddle.getHeight());
        boolean isHorizontallyAligned = (x + radius >= paddle.getX() && 
                                       x - radius <= paddle.getX() + paddle.getWidth());
        
        boolean result = isVerticallyAligned && isHorizontallyAligned;
        assertInvariants();
        return result;
    }
    
    // Getters and setters with contracts
    public double getX() {
        assertInvariants();
        return x;
    }
    
    public double getY() {
        assertInvariants();
        return y;
    }
    
    public double getRadius() {
        assertInvariants();
        return radius;
    }
    
    public double getVelocityX() {
        assertInvariants();
        return velocityX;
    }
    
    public double getVelocityY() {
        assertInvariants();
        return velocityY;
    }
    
    /**
     * @param velocityX new horizontal velocity
     * @requires velocityX must be finite
     */
    public void setVelocityX(double velocityX) {
        assert Double.isFinite(velocityX) : "Velocity must be finite";
        this.velocityX = velocityX;
        assertInvariants();
    }
    
    /**
     * @param velocityY new vertical velocity
     * @requires velocityY must be finite
     */
    public void setVelocityY(double velocityY) {
        assert Double.isFinite(velocityY) : "Velocity must be finite";
        this.velocityY = velocityY;
        assertInvariants();
    }
}