package Game.Scenes;

import Game.Entities.Guard;
import Game.Entities.Player;
import Game.Graphics.Sprite;
import Game.Utilities.Position;
import Game.Utilities.Score;
import Game.Utilities.UserInput;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MenuScene extends Scene {

    //Fonts
    private Font titleFont;
    private Font subtitleFont;
    private Font buttonFont;

    //Entities
    private Player player;
    private Guard guard;

    //Graphics
    private BufferedImage banana;
    private BufferedImage key;

    //Navigation
    private int currentButton = 0;
    private int currentScreen = 0;

    //Audio
    private AudioInputStream buttonSound;
    private Clip buttonClip;

    //Counters for keeping time
    private int introScreenCounter = 0; //How long to play intro
    private int screenDelayCounter = 0; //Delay between screens so input does not click 2 buttons

    public MenuScene(SceneManager sceneManager, boolean playIntro) {
        super(sceneManager);

        //Loading fonts and graphics
        try {
            titleFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Game/Assets/CompassPro.ttf")).deriveFont(120f);
            subtitleFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Game/Assets/CompassPro.ttf")).deriveFont(30f);
            buttonFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Game/Assets/CompassPro.ttf")).deriveFont(60f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(titleFont);
            ge.registerFont(subtitleFont);
            ge.registerFont(buttonFont);

            banana = ImageIO.read(new File("src/Game/Assets/items/banana.png"));
            key = ImageIO.read(new File("src/Game/Assets/items/key.png"));

        } catch (IOException | FontFormatException e) {
            System.out.println("Error loading assets!");
        }

        try {
            buttonSound = AudioSystem.getAudioInputStream(new File("src/Game/Assets/music/button.wav"));
            buttonClip = AudioSystem.getClip();
            buttonClip.open(buttonSound);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error loading sound");
        }

        //Set up entities on the main menu screen
        player = new Player(new Sprite("src/Game/Assets/knight", "player"), new Position(375, 400), 128, 0, 0);
        player.currentDirection = 3;
        player.speed = 0;

        guard = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(250, 400), 128, 0, 0);
        guard.right = true;
        guard.speed = 0;

        //Skip intro
        if(!playIntro)
            currentScreen = 1;

    }

    @Override
    public void update() {
        //Updating counters and entity animations
        screenDelayCounter++;
        if(currentScreen == 0)
            introScreenCounter++;
        else if(currentScreen == 1) {
            player.update();
            guard.update();
        }
    }

    @Override
    public void input(UserInput userInput) {

        //Switch buttons based on input
        if(currentScreen == 1) {
            if (userInput.up.isPressed && currentButton == 1) {
                currentButton = 0;
            } else if (userInput.down.isPressed && currentButton == 0) {
                currentButton = 1;
            }
            player.right = true;

            //Select button based on input and switch screens
            if(userInput.select.isPressed && currentButton == 0 && screenDelayCounter > 10) {
                currentScreen = 2;
                screenDelayCounter = 0;
                playSound();
            }
            else if(userInput.select.isPressed && currentButton == 1 && screenDelayCounter > 10) {
                currentScreen = 3;
                screenDelayCounter = 0;
                currentButton = 0;
                playSound();
            }
        }
        else if(currentScreen == 2) {
            if (userInput.up.isPressed && currentButton == 1) {
                currentButton = 0;
            } else if (userInput.down.isPressed && currentButton == 0) {
                currentButton = 1;
            }
            if(userInput.select.isPressed && currentButton == 0 && screenDelayCounter > 10) {
                playSound();
                //Call scene manager to switch screen
                super.switchScene("play");
            }
            else if(userInput.select.isPressed && currentButton == 1 && screenDelayCounter > 10) {
                currentScreen = 1;
                currentButton = 0;
                screenDelayCounter = 0;
                playSound();
            }
        }
        else if(currentScreen == 3) {
            if(userInput.select.isPressed && currentButton == 0 && screenDelayCounter > 10) {
                currentScreen = 1;
                currentButton = 0;
                screenDelayCounter = 0;
                playSound();
            }
        }


    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, 832, 832);

        //Draw menu based on screen
        if(currentScreen == 0) {
            g.setColor(new Color(255, 255, 255));
            g.setFont(buttonFont);
            g.drawString("In an alternate reality...", 150, 250);
            if(introScreenCounter >= 80) {
                g.drawString("The Banana is the most", 150, 350);
                g.drawString("valuable commodity.", 150, 450);
                g.drawImage(banana, 350, 500, 120, 120, null);
            }
            if(introScreenCounter >= 210)
                currentScreen = 1;
        }
        else if(currentScreen == 1) {
            g.setColor(new Color(255, 242, 3));
            g.setFont(titleFont);
            g.drawString("BANANA", 220, 200);
            g.setColor(new Color(255, 255, 255));
            g.drawString("BANDIT", 240, 300);
            g.setFont(subtitleFont);
            g.drawString("Original Java Game by York Wei", 230, 350);

            player.render(g);
            guard.render(g);
            g.drawImage(banana, 500, 400, 90, 90, null);

            //Change button color to show selection
            if (currentButton == 0) {
                g.setColor(new Color(255, 159, 182));
                g.setFont(buttonFont);
                g.drawString("Play", 375, 600);
                g.setColor(new Color(115, 115, 115));
                g.drawString("High Scores", 285, 675);
            } else if (currentButton == 1) {
                g.setColor(new Color(115, 115, 115));
                g.setFont(buttonFont);
                g.drawString("Play", 375, 600);
                g.setColor(new Color(255, 159, 182));
                g.drawString("High Scores", 285, 675);
            }

        }
        else if(currentScreen == 2) {

            g.setColor(new Color(255, 255, 255));
            g.setFont(titleFont);
            g.drawString("OBJECTIVE", 170, 200);
            g.drawImage(key, 275, 235, 64, 64, null);
            g.drawImage(banana, 500, 235, 64, 64, null);
            g.setColor(new Color(255, 242, 3));
            g.drawString("->", 380, 290);
            g.setColor(new Color(255, 255, 255));
            g.setFont(subtitleFont);
            g.drawString("Steal as many bananas as possible by finding the key,", 100, 340);
            g.drawString("retrieving the banana, then exiting the maze while avoiding", 75, 370);
            g.drawString("the goblins!", 350, 400);
            g.drawString("The difficulty increases as you steal more bananas,", 130, 460);
            g.drawString("but there are randomly placed powerups throughout the maze", 60, 490);
            g.drawString("to help you. Each time you level up, you gain 1 life.", 120, 520);
            if (currentButton == 0) {
                g.setColor(new Color(255, 159, 182));
                g.setFont(buttonFont);
                g.drawString("Start", 370, 600);
                g.setColor(new Color(115, 115, 115));
                g.drawString("Back", 375, 675);
            } else if (currentButton == 1) {
                g.setColor(new Color(115, 115, 115));
                g.setFont(buttonFont);
                g.drawString("Start", 370, 600);
                g.setColor(new Color(255, 159, 182));
                g.drawString("Back", 375, 675);
            }

        }
        else if(currentScreen == 3) {
            g.setColor(new Color(255, 242, 3));
            g.setFont(titleFont);
            g.drawString("BANANA", 230, 200);;
            g.setFont(buttonFont);
            g.drawString("HALL OF FAME", 250, 250);
            g.setColor(new Color(255, 255, 255));
            ArrayList<Score> scores = new ArrayList<Score>();
            String s;
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File("src/Game/highscores.txt")));
                while((s = br.readLine()) != null){
                    scores.add(new Score(s.substring(0, s.indexOf(' ')), Integer.parseInt(s.substring(s.indexOf(' ') + 1))));
                }
                br.close();
            }
            catch (IOException ignored) {};
            g.setFont(subtitleFont);
            for(int i = 0; i < 10; i++) {
                g.drawString((i+1) + ". " + scores.get(i).getName(), 200, 300+(35*i));
                g.drawString(""+scores.get(i).getScore(), 585, 300+(35*i));
                g.drawImage(banana, 630, 277+(35*i), 24, 24, null);
            }
            g.setFont(buttonFont);
            g.setColor(new Color(255, 159, 182));
            g.drawString("Back", 375, 675);
        }
    }

    //Play the button clip
    public void playSound() {
        buttonClip.setMicrosecondPosition(0);
        buttonClip.start();
    }
}
