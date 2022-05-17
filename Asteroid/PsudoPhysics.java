

public class PsudoPhysics {
    private double accel;
    private double maxVel;
    private Vector velocity;
    private double time;
    private double angle;
    public int xPos;
    public int yPos;

    /**
     * Creates some physics on an object 
     * @param xPos x positon
     * @param yPos y positon
     * @param accel acceleration of the object when it accelerates
     * @param velocity current velocity
     * @param maxVel maximum velocity of the object 
     * @param time delta time
     * @param angle angle at which the physics is acting
     */
    public PsudoPhysics(int xPos, int yPos, double accel, double velocity, double maxVel, double time, double angle){
        this.xPos = xPos;
        this.yPos = yPos;
        this.velocity = new Vector(velocity*Math.cos(angle), velocity*Math.sin(angle));
        this.maxVel = maxVel;
        this.time = time;
        this.angle = angle;
        this.accel = accel;
    }

    /**
     * updates the position of the object using velocity vectors
     * @param accelerate true for accelerating false otherwise
     */
    public void updatePosition(boolean accelerate){
        updateVelocity(accelerate);
        xPos = (int)(velocity.getXComponent() + xPos + 0.5);
        yPos = (int)(velocity.getYComponent() + yPos + 0.5);
    }

    /**
     * updates the velocity
     * @param accelerate true for accelerating false otherwise
     */
    private void updateVelocity(boolean accelerate){
        double abs = velocity.abs();
        if(accelerate){
            velocity = velocity.sum(new Vector(accel*time*Math.cos(angle), accel*time*Math.sin(angle)));
        } else if(velocity.abs() > 0){
            velocity.scaleTo(abs*0.95);
        }
        //TODO limit velocity

        if(abs > maxVel)
            velocity.scaleTo(maxVel);

    }

    /**
     * offsets the angle of the acting vectors
     * @param angle
     */
    public void offsetAngle(double angle){
        this.angle += angle;
    }

    /**
     * used to correct the positon if the object suddenly jumps a large distance(like going from the top of the screen to the bottom)
     * @param x x position
     * @param y y position
     */
    public void correctPositon(int x, int y){
        xPos = x;
        yPos = y;
    }
}
