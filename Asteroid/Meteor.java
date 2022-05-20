
public class Meteor extends Polygon{
    public double velocity = 0;
    public double angle = 0;
    /**
     * Creates a meteor polygon, predetermined layout is initilized 
     * @param x center of the meteor on the x axis
     * @param y center of the meteor on the y axis
     * @param scale the scale factors of the meteor
     */
    public Meteor(int x, int y, double scale){
        super(new int[]{-60, -60, -45, -15, 15, 45, 60, 60, 45, 15, -15, -45}, new int[]{15, -15, -45, -60, -60, -45, -15, 15, 45, 60, 60, 45}, x, y, 0, scale);
        angle = Math.random()*Math.PI*2;
        velocity = (int)(Math.random()*6)+4;
        offsetAngle(angle);
        super.angle = angle;
    }

    /**
     * Creates a meteor polygon, predetermined layout is initilized 
     * @param x center of the meteor on the x axis
     * @param y center of the meteor on the y axis
     * @param scale scale factor of the meteor
     * @param angle2 rotational angle and movement angle
     * @param velocity velocity of the meteor
     */
    public Meteor(int x, int y, double scale, double angle2, double velocity){
        super(new int[]{-60, -60, -45, -15, 15, 45, 60, 60, 45, 15, -15, -45}, new int[]{15, -15, -45, -60, -60, -45, -15, 15, 45, 60, 60, 45}, x, y, angle2, scale);
        this.angle = angle2;
        this.velocity = velocity;
        offsetAngle(angle2);
    }

    /**
     * updates the position of the meteor 
     */
    public void updatePos(){
        setLocation(super.centerX + (int)(velocity*Math.cos(angle)), super.centerY + (int)(velocity*Math.sin(angle)));
    }


    
    

}
