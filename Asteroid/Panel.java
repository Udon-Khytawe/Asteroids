import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel{
    private Graphics2D g2;
    private SpaceShip ship= new SpaceShip();
    private Meteor[] asteroids = new Meteor[40];
    private Meteor cue;
    private double[] asteroidScales = new double[]{.25, .5, 1};
    private Bullet[] bullets = new Bullet[3];
    private boolean shoot;
    private int count = 10;
    private boolean canShoot = true;
    private int score = 0;
    private boolean gameOver = false;
    private int frameCount = 0;

    /**
     *  Creates an instance of the game panel for asteroids 
     */
    public Panel(){
        setBackground(Color.BLACK);
    }

    /**
     * sets the game over boolean to true;
     */
    public void gameOver(){
        gameOver = true;
    }

    /**
     * Paints the asteroids, ship, and the bullets in the current instance of the game
     */
    public void paintComponent(Graphics g){
        int width = getSize().width;//gets the panel width
        int height = getSize().height;//gets the panel height
        super.paintComponent(g);//sets teh graphics which are going to be used by the panel
        g2 = (Graphics2D) g;//casts the graphics to 2d graphcis 
        g2.setPaint(Color.WHITE);//sets the paint color to white 
        ship.draw(g2);//draws a the ship
        if(gameOver){//displays the game over screen
            Font font = new Font ("font", Font.PLAIN, 55);
		    g2.setFont (font);
		    g2.drawString ("GAME OVER", 0, 50); //use of string text 
            g2.drawString("Score: " + (score + (frameCount/33)*10), 0, 105);
            font = new Font ("font", Font.PLAIN, 30);
            g2.setFont(font);
            g2.drawString("Restart the Game[y/n]?", 0, 140);
        } else {
            int length = bullets.length;
            for(int i = 0 ; i < length; i++){//iterates through all the bullets testing to see if the bullet is null or if the bullet is out of bounds
                if(bullets[i] != null && !(bullets[i].centerX > width+200 || bullets[i].centerX < -200 || bullets[i].centerY > height+200 || bullets[i].centerY < -200)){
                    bullets[i].draw(g2);
                } else if(shoot && canShoot){//shooting conditions
                    bullets[i] = new Bullet(ship.centerX, ship.centerY, 5, 20, ship.angle - Math.PI/2);
                    canShoot = false;
                } 
            
            }
                int len = asteroids.length;
            for(int i = 0; i < len ; i++){
                if(asteroids[i] != null && !(asteroids[i].centerX > width+200 || asteroids[i].centerX < -200 || asteroids[i].centerY > height+200 || asteroids[i].centerY < -200)){
                    asteroids[i].draw(g2);//draws the asteroids if they are in bound and not null
                } else if(cue != null){
                    asteroids[i] = new Meteor(cue.centerX, cue.centerY, cue.scale, cue.angle, cue.velocity);//sets a null asteroid to the cue
                    cue = null;
                } else if(Math.random()*100 < 10){//10 percent chance a null or out of bounds asteroid 
                    int xLoc = (int)(Math.random()*400) - 200;
                    if(xLoc > 0)
                        xLoc += width;
                        int yLoc = (int)(Math.random()*400) - 200;
                    if(yLoc > 0){
                       yLoc += height;
                  }
                  asteroids[i] = new Meteor(xLoc, yLoc, asteroidScales[(int)(Math.random()*3)]);
              }
            }
        }
    }

    /**
     * Updates the positon of the ball based on the applied force
     * @param xInc //force applied to the x axis 
     * @param yInc //force applied to the y axis
     */
    public boolean refreash(boolean accel, double angleOffset, boolean shoot){
        boolean collison = false;
        ship.updatePos(accel, angleOffset, getSize().width, getSize().height);//updates the positon of the ship
        int len = asteroids.length;
        for(int i = 0; i <  len; i++){//iterates through all the asteroids
            int length = bullets.length;
            for(int k = 0; k < length; k++){//collides all the meteors with all the bullets
                if(bullets[k] != null && asteroids[i] != null && PolygonCollisionV2.collide(asteroids[i], bullets[k])){
                    if(asteroids[i].scale == .25){//adds to the score depending on the asteroid scale
                        score+=200;
                    } else if(asteroids[i].scale == .5){
                        score+=100;
                    } else if(asteroids[i].scale == 1){
                        score+=50;
                    }
                    if(asteroids[i].scale > .25){//if the asteroid is bigger than quarter scale it splits, and one aseroid is put in the cue to be created, the other is spawned immediatly
                        cue = new Meteor(asteroids[i].centerX, asteroids[i].centerY, asteroids[i].scale/2, asteroids[i].angle - .4, asteroids[i].velocity);
                        asteroids[i] = new Meteor(asteroids[i].centerX, asteroids[i].centerY, asteroids[i].scale/2, asteroids[i].angle + .4, asteroids[i].velocity);
                    } else {
                    asteroids[i] = null;
                    }
                    bullets[k] = null;
                } 
            }
            if(asteroids[i] != null){
                asteroids[i].updatePos();//if the asteroid is not null then the positon is updated
                if(PolygonCollisionV2.collide(ship, asteroids[i])){
                    collison = true;//if their is a collision between the ship and the asteroid collide is set to true
                }
            }
        }
        if(!(this.shoot && canShoot))//shooting logic
            this.shoot = shoot;
        if(!canShoot && count < 10){
            count++;
        } else {
            canShoot = true;
            count = 0;
        }
        repaint();//repaints the panel(calls paint component)
        frameCount++;
        return collison;
    }

    //TODO implement a spawn method for spawning asteroids. this will attemp to spawn asteroids when certain condtions are met. Importantly it will be used to spawn asteroids when 
}
