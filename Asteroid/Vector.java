

public class Vector {
    private double xComponent;
    private double yComponent;

    /**
     * Creates a vectors with and x and y component, NOT magnitude angle
     * @param xComponent the x component of the vector
     * @param yComponent the y component of the vector
     */
    public Vector(double xComponent, double yComponent){
        this.xComponent = xComponent;
        this.yComponent = yComponent;
    }

    /**
     * returns the dot product of this vector and vector provided in the input
     * @param vector other vector being used for the dot product
     * @return
     */
    public double dotProduct(Vector vector){
        return xComponent*vector.getXComponent() + yComponent*vector.getYComponent();
    }

    /**
     * @return the absolute value of the vector
     */
    public double abs(){
        return Math.sqrt(xComponent*xComponent + yComponent*yComponent);
    }

    /**
     * Sums this vector and the other vector
     * @param vector other vector to add to this vector
     * @return
     */
    public Vector sum(Vector vector){
        return new Vector(xComponent + vector.getXComponent(), yComponent + vector.getYComponent());
    }

    /**
     * returns the x component
     * @return
     */
    public double getXComponent(){
        return xComponent;
    }

    /**
     * returns the y component 
     * @return
     */
    public double getYComponent(){
        return yComponent;
    }

    /**
     * returns the vector as a string
     */
    public String toString(){
        return "<" + xComponent + ", " + yComponent + ">";
    }

    /**
     * scales the vector to a provided magnitude 
     * @param magnitude the magnitude which this vector is to be scaled to 
     */
    public void scaleTo(double magnitude){
        double magRatio = magnitude / abs();
        xComponent = magRatio*xComponent;
        yComponent = magRatio*yComponent;
    }
}
