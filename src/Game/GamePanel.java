package Game;

import Game.Scenes.SceneManager;
import Game.Utilities.UserInput;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {

    private int width;
    private int height;

    private BufferedImage img;
    private Graphics2D g;

    private Thread gameThread;
    private UserInput userInput;

    private SceneManager sceneManager;

    public GamePanel(int width, int height) {

        this.width = width;
        this.height = height;

        //Setting up the JPanel
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();

        //Load background music
        try {
            AudioInputStream backgroundMusic = AudioSystem.getAudioInputStream(new File("src/Game/Assets/music/background.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(backgroundMusic);
            clip.loop(1000000000);
            clip.start();
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error loading music");
        }

    }

    //Getters & setters for dimensions
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    //Overrides the addNotify function to see if the game thread needs to be initialized.
    public void addNotify() {

        super.addNotify();

        if(gameThread == null) {

            gameThread = new Thread(this);
            gameThread.start();

        }

    }


    //Implements the Runnable interface - code in run() method to be executed by thread.
    @Override
    public void run() {

        //Setting up graphics to be drawn onto the JPanel
        img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();

        //Setting up user input and scene manager
        userInput = new UserInput(this);
        sceneManager = new SceneManager();

        //Game loop variables

        //Elapsed time in nanoseconds before an update needs to occur - game updates 60 times in 1 second.
        double timeBeforeUpdate = 1000000000 / 60.0;

        int updatesBeforeRender = 5;

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        //Elapsed time in nanoseconds before rendering - game renders 60 times in 1 second.
        double timeBeforeRender = 1000000000 / 60.0;

        while(true) {

            double timeNow = System.nanoTime();
            int updateCount = 0;

            //Keep updating the game until the upper limit of the number of updates is reached by either time or number of updates.
            while((timeNow - lastUpdateTime) > timeBeforeUpdate && updateCount < updatesBeforeRender) {

                update();
                input(userInput);
                updateCount++;
                lastUpdateTime += timeBeforeUpdate;

            }

            //Update the last update time
            if((timeNow - lastUpdateTime) > timeBeforeUpdate) {

                lastUpdateTime = timeNow - timeBeforeUpdate;

            }

            //Get user input and render graphics
            input(userInput);
            render();
            lastRenderTime = timeNow;

            //Delaying the game loop to ensure game runs at 60 updates/renders per second.
            while((timeNow - lastRenderTime) < timeBeforeRender && timeNow - lastUpdateTime < timeBeforeUpdate) {
                timeNow = System.nanoTime();
            }

        }

    }

    //Calling update to the scene manager
    public void update() {
        sceneManager.update();
    }

    //Giving user input to the scene manager
    public void input(UserInput userInput) {
        sceneManager.input(userInput);
    }

    public void render() {

        //Drawing on the graphics2D of the buffered image
        sceneManager.render(g);

        //Drawing the panel by drawing the buffered image
        Graphics g2 = (Graphics) this.getGraphics();
        g2.drawImage(img, 0, 0, this.width, this.height, null);
        g2.dispose();

    }

}
