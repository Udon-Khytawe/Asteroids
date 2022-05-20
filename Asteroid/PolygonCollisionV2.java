

public class PolygonCollisionV2 {
    /**
     * YEAH, IT WORKS!!!!!!!!!!!, Only took about three or four hours of debugging to finish. It can successfully detect collision between two convex polygons.
     * It uses the Seperating Axis Theorum to test whether the polygons are colliding. 
     * @param p1 the first convex polygon
     * @param p2 the second convex polygon
     * @return true if their is a collision between the two polygons otherwise false.
     */
    public static boolean collide(Polygon p1, Polygon p2){
        if(new Vector(p1.centerX - p2.centerX, p1.centerY - p2.centerY).abs() > 85){//tests to see wheter the asteroid is outside of collsion radius
            return false;
        }
        boolean collide = true; //boolean for testing whether a collision occurs
        Vector[][] normalVectors = new Vector[][]{p1.getNormalVectors(), p2.getNormalVectors()}; //normal vectors for p1 and p2
        int[][] p1Points = new int[][]{p1.getXCoords(), p1.getYCoords()}; //coordinates of p1
        int[][] p2Points = new int[][]{p2.getXCoords(), p2.getYCoords()}; //coordiantes of p2
        int rowLength = normalVectors.length;

        double[] p1Projected = new double[p1Points[0].length]; //the points of p1 projected onto the normal vectors
        double[] p2Projected = new double[p2Points[0].length]; //the points of p2 projected onto the normal vectors
        int len1 = p1Points[0].length;
        int len2 = p2Points[0].length;
        for(int row = 0; row < rowLength; row++){ //iterates through all the normal vectors
            for(int col = 0; col < normalVectors[row].length; col++){
                double max1 = (normalVectors[row][col].dotProduct(new Vector(p1Points[0][0], p1Points[1][0])))/normalVectors[row][col].abs();
                double min1 = (normalVectors[row][col].dotProduct(new Vector(p1Points[0][0], p1Points[1][0])))/normalVectors[row][col].abs();

                double max2 = (normalVectors[row][col].dotProduct(new Vector(p2Points[0][0], p2Points[1][0])))/normalVectors[row][col].abs();
                double min2 = (normalVectors[row][col].dotProduct(new Vector(p2Points[0][0], p2Points[1][0])))/normalVectors[row][col].abs();

                for(int i = 1; i < len1; i++){//iterates through all the points in the first polygon and projects them to the normal vector 
                    p1Projected[i] = (normalVectors[row][col].dotProduct(new Vector(p1Points[0][i], p1Points[1][i])))/normalVectors[row][col].abs();
                    double projected = p1Projected[i];
                    if(projected > max1){//finds the max projected value
                        max1 = projected;
                    } else if(projected < min1){//finds the min projected value
                        min1 = projected;
                    }
                }

                for(int k = 1; k < len2; k++){//does the same thing as the preceeding loop but for the second polygon
                    p2Projected[k] = (normalVectors[row][col].dotProduct(new Vector(p2Points[0][k], p2Points[1][k])))/normalVectors[row][col].abs();
                    double projected = p2Projected[k];
                    if(projected > max2){
                        max2 = projected;
                    } else if(projected < min2){
                        min2 = projected;
                    }
                }

                if((min2 > max1) ^ (min1 > max2)){//if the max of one is less than the min of the other then it is a collison on this normal, other wise it is false. This logic does that in such a matter that order doesn't matter
                    collide = false;//if, on any normal, the two polygons don't overlap then the collsion does not occur
                    break;
                }
            }
            if(!collide){
                break;
            }
        }
        return collide;
    }

    /**
     * Collides a polgyon and a circle. Only works if the center of the polgyon is the geometric center of the polygon(probably, as in I'm only pretty sure this does what I want)
     * @param p1
     * @param circle
     * @return
     */
    public static boolean collide(Polygon p1, Bullet bullet){
        Polygon line = new Polygon(new int[]{0, bullet.centerX - bullet.preCenterX}, new int[]{0, bullet.centerY - bullet.preCenterY}, bullet.preCenterX, bullet.preCenterY, 0, 1);
        return collide(p1, line);
    }
}
