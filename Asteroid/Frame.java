import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.*;
import javax.swing.*;
import jaco.mp3.player.MP3Player;
import java.io.File;

public class Frame extends JFrame implements KeyListener{
    private boolean[] upDownLeftRight = new boolean[4];//{up, down, left, right}
    private boolean shoot = false;
    private Panel p1 = new Panel();
    private Timer frameRate;
    private boolean gameOver = false;
    private boolean pause = false;
    private boolean mute = false;
    private MP3Player player = new MP3Player(new File("Asteroid/Orbit_of_the_Dutchman.mp3"));
    private long startTime;
    /**
     * creates the Jframe with a keylistener and a gamepanel
     */
    public Frame(){
        super("Asteroids");//names the frame
        player.play();
        startTime = System.currentTimeMillis();

        int delay = 30;//sets the delay between loopmusic checks
        ActionListener loopMusic = new ActionListener() {//creates a tast for the timer to execute
            public void actionPerformed(ActionEvent evt) {
                long currentTime = System.currentTimeMillis();
                if(currentTime - startTime > 52000){
                    startTime = currentTime;
                    player.play();
                }
            }
        };
        Timer music = new Timer(delay, loopMusic);//creates the timer
        music.start();
        
        ImageIcon img = new ImageIcon("Icon.png");//creates an image 
        setIconImage(img.getImage());//sets the icon of the frame
        addKeyListener(this);//adds a key listener 
        setSize(1000, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        add(p1);
        startGame();
    }
    /**
     * Initlizing sequence to start the game
     */
    public void startGame(){
        int delay = 30;//sets the delay between frames
        ActionListener taskPerformer = new ActionListener() {//creates a tast for the timer to execute
            boolean accel;
            double angleOffsetLeft = 0;
            double angleOffsetRight = 0;
            public void actionPerformed(ActionEvent evt) {
                accel = false;
                angleOffsetLeft = 0;
                angleOffsetRight = 0;

                if(upDownLeftRight[0]){
                    accel = true;
                }
                if(upDownLeftRight[1]){}

                if(upDownLeftRight[2])
                    angleOffsetLeft = -0.2;
                if(upDownLeftRight[3])
                    angleOffsetRight = 0.2;
                if(p1.refreash(accel, angleOffsetLeft+angleOffsetRight, shoot)){
                    frameRate.stop();//stops the game when the ship collides
                    p1.gameOver();
                    p1.refreash(accel, angleOffsetLeft+angleOffsetRight, shoot);
                    gameOver = true;
                }
                
            }
        };
        frameRate = new Timer(delay, taskPerformer);//creates the timer
        frameRate.start();//starts the timer
    }

    /**
     * Updates the gameframe if a key is pressed
     */
    @Override
    public void keyPressed(KeyEvent arg0) {
        int key = arg0.getKeyCode();
        if(key == KeyEvent.VK_UP){
            upDownLeftRight[0] = true;
        }
        if(key == KeyEvent.VK_DOWN){
            upDownLeftRight[1] = true;
            
        }
        if(key == KeyEvent.VK_LEFT){
            upDownLeftRight[2] = true;
            
        }
        if(key == KeyEvent.VK_RIGHT){
            upDownLeftRight[3] = true;
            
        }
        if(key == KeyEvent.VK_SPACE){
            shoot = true;
        }
    }

    /**
     * If a key is released it is set to a value which will not be detected as a key(-999 in this case).
     * If both keys are released then the momentum method is called.
     */
    @Override
    public void keyReleased(KeyEvent arg0) {
        int key = arg0.getKeyCode();

        if(key == KeyEvent.VK_UP){
            upDownLeftRight[0] = false;
        }
        if(key == KeyEvent.VK_DOWN){
            upDownLeftRight[1] = false;

        }
        if(key == KeyEvent.VK_LEFT){
            upDownLeftRight[2] = false;
        }
        if(key == KeyEvent.VK_RIGHT){
            upDownLeftRight[3] = false;
        }
        if(key == KeyEvent.VK_SPACE){
            shoot = false;
        }
        if(key == KeyEvent.VK_N && gameOver){//closes the frame if the game is over and the user presses no
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        if(key == KeyEvent.VK_Y && gameOver){//starts a new game if the game is over and the user presses yes
            gameOver = false;
            remove(p1);
            p1 = new Panel();
            add(p1);
            getContentPane().invalidate();
            getContentPane().validate();
            getContentPane().repaint();
            startGame();
        }
        if(key == KeyEvent.VK_P){//pause
            if(!pause){
                frameRate.stop();
                pause = true;
            } else {
                frameRate.start();
                pause = false;
            }
        }

        if(key == KeyEvent.VK_M){//pause
            if(!mute){
                player.stop();
                mute = true;
            } else {
                player.play();
                startTime = System.currentTimeMillis();
                mute = false;
            }
        }
    }

    /**
     * Doesn't do anything for now
     */
    @Override
    public void keyTyped(KeyEvent arg0) {
    }
}