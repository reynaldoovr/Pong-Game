package pong.model;

public class Paddle {
    private double x;
    private double y;
    private double width;
    private double height;
    private double velocity;

    /**
     * @param x initial x position
     * @param y initial y position
     * @param width paddle width
     * @param height paddle height
     * @throws IllegalArgumentException if width or height <= 0
     */
    public Paddle(double x, double y, double width, double height) {
        assert width > 0 : "Paddle width must be positive";
        assert height > 0 : "Paddle height must be positive";
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = 0.0;
        assertInvariants();
    }

    private void assertInvariants() {
        assert width > 0 : "Paddle width must remain positive";
        assert height > 0 : "Paddle height must remain positive";
        assert Double.isFinite(x) && Double.isFinite(y) : "Paddle position must be finite";
        assert Double.isFinite(velocity) : "Paddle velocity must be finite";
    }

    /**
     * Updates paddle position based on current velocity
     * @ensures vertical position changes by velocity value
     */
    public void move() {
        double oldY = y;
        y += velocity;
        assert y == oldY + velocity : "Y position must change by velocity";
        assertInvariants();
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

    public double getWidth() {
        assertInvariants();
        return width;
    }

    public double getHeight() {
        assertInvariants();
        return height;
    }

    public double getVelocity() {
        assertInvariants();
        return velocity;
    }

    /**
     * @param velocity new vertical velocity
     * @requires velocity must be finite
     */
    public void setVelocity(double velocity) {
        assert Double.isFinite(velocity) : "Velocity must be finite";
        this.velocity = velocity;
        assertInvariants();
    }
}