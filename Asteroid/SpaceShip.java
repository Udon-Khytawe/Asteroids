public class SpaceShip extends Polygon{
    private PsudoPhysics phys;
    /**
     * Creates a spaceship with a predetermined shape( a trianlge in this case)
     */
    public SpaceShip(){
        super(new int[]{-15,15, 0}, new int[]{20, 20, -20}, 500, 500, 0, 1);
        phys =  new PsudoPhysics(500, 500, 0.5, 0, 16, 1, -Math.PI/2);
    }

    /**
     * updates the position of the spaceship based on the physics
     * @param accel true for accelerating false otherwise
     * @param angle angle of the spaceship
     * @param boundWidth width of the panel the ship is being drawn on
     * @param boundHeight height of the panel the ship is being drawn on
     */
    public void updatePos(boolean accel, double angle, int boundWidth, int boundHeight){
        phys.offsetAngle(angle);
        phys.updatePosition(accel);
        offsetAngle(angle);
        int x = phys.xPos;
        int y = phys.yPos;
        if(x < 0){
            x += boundWidth;
        } else if(x > boundWidth){
            x = 0;
        }
        if(y < 0){
            y += boundHeight;
        } else if(y > boundHeight){
            y = 0;
        }
        phys.correctPositon(x, y);
        setLocation(x, y);
    }
}
