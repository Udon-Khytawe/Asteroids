import java.awt.*;
public class Polygon {
    private int[] xCoords;
    private int[] yCoords;
    public double angle = 0;
    public int centerX;
    public int centerY;
    private Vector[] normalVectors;
    public double scale;

    /**
     * Creates a polygon which can be drawn onto 2d graphics. The dimension of the x and y arrays must be the same, points are drawn from left to right in the array
     * @param x array of x coordinates relative to centerX
     * @param y array of y coordinates relative to centerY
     * @param centerX center x coordiante
     * @param centerY center y coordinate
     * @param angle angle(in radians) which the polygon is drawn at 
     * @param scale scale factor 
     */
    public Polygon(int[] x, int[] y, int centerX, int centerY, double angle, double scale){
        xCoords = x.clone();
        yCoords = y.clone();
        this.centerX = centerX;
        this.centerY = centerY;
        this.angle = angle;
        this.scale = scale;
        normalVectors = new Vector[xCoords.length];
    }

    /**
     * rotates the polygon to the input angle
     * @param angle angle to be rotated to
     */
    public void rotateTo(double angle){
        this.angle = angle;
    }

    /**
     * Shifts the polygon by a x anf y offset 
     * @param deltaX //x offset
     * @param deltaY //y offset
     */
    public void move(int deltaX, int deltaY){
        centerX += deltaX;
        centerY += deltaY;
    }

    /**
     * sets the location to the input x and y coordinates
     * @param x new centerX of the polygon
     * @param y new centerY of the polygon
     */
    public void setLocation(int x, int y){
        centerX = x;
        centerY = y;
    }

    /**
     * draws the shape onto the provided 2d graphics 
     * @param g2 the 2d graphcis being used to draw this polygon
     */
    public void draw(Graphics2D g2){
        int len = xCoords.length;
        double cos = Math.cos(angle) ;
        double sin = Math.sin(angle);
        int index2;
        for(int i = 0; i < len; i++){
            index2 = (i + 1)%len;
            g2.drawLine((int)((xCoords[i]*cos - yCoords[i]*sin)*scale) + centerX, (int)((xCoords[i]*sin + yCoords[i]*cos)*scale) + centerY, (int)((xCoords[index2]*cos - yCoords[index2]*sin)*scale) + centerX, (int)((xCoords[index2]*sin + yCoords[index2]*cos)*scale) + centerY);
        }
    }

    /**
     * returns the array of x coordinates
     * @return
     */
    public int[] getXCoords(){
        int len = xCoords.length;
        int[] x = new int[len];
        for(int i = 0; i < len; i++){
            x[i] = (int)((xCoords[i]*Math.cos(angle) - yCoords[i]*Math.sin(angle))*scale) + centerX;
        }
        return x;
    }

    /**
     * returns the array of the y coordiates
     * @return
     */
    public int[] getYCoords(){
        int len = yCoords.length;
        int[] y = new int[len];
        for(int i = 0; i < len; i++){
            y[i] = (int)((xCoords[i]*Math.sin(angle) + yCoords[i]*Math.cos(angle))*scale) + centerY;
        }
        return y;
    }

    /**
     * Rotates the polgon by the input angle 
     * @param angle angle offset 
     */
    public void offsetAngle(double angle){
        this.angle+= angle;
    }

    /**
     * returns the normal vectors from each of the vectors represetning the sides
     * @return
     */
    public Vector[] getNormalVectors(){
        int length = xCoords.length;
        int index = 0;
        int[] x = getXCoords();
        int[] y = getYCoords();
        for(int i  = 0; i < length; i++){
            index = (i+1)%length;
            normalVectors[i] = new Vector(y[index] - y[i], -(x[index] - x[i]));
        }
        return normalVectors;
    }
}
