import java.awt.Graphics2D;

public class Bullet {
    public int radius;
    public int centerX;
    public int centerY;
    private double velocity;
    public double angle;

    /**
     * Creates a bullet projectile to be fired by the spaceship
     * @param centerX center of the bullet on the x axis
     * @param centerY center of the bullet on the y axis
     * @param radius radius of the bullet
     * @param velocity velocity of the bullet
     * @param angle angle direction the bullet is going 
     */
    public Bullet(int centerX, int centerY, int radius, double velocity, double angle){
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.velocity = velocity;
        this.angle = angle;
    }

    /**
     * Draws the bullet on some 2d graphics 
     * @param g2 the 2d graphics being used by a panel
     */
    public void draw(Graphics2D g2){
        updatePos();
        g2.fillOval(centerX, centerY, radius, radius);
    }

    /**
     * updates the x and y positons of the bullet 
     */
    public void updatePos(){
        centerX += velocity*Math.cos(angle);
        centerY += velocity*Math.sin(angle);
    }

    /**
     * sets the movement angle
     * @param angle
     */
    public void setAngle(double angle){
        this.angle = angle;
    }
}
