package Game.Scenes;

import Game.Entities.Guard;
import Game.Entities.Player;
import Game.Graphics.Sprite;
import Game.Map.Tile;
import Game.Map.TileMap;
import Game.Utilities.Position;
import Game.Utilities.Score;
import Game.Utilities.SortName;
import Game.Utilities.UserInput;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class PlayScene extends Scene {

    //Game Classes
    private TileMap tileMap;
    private Player player;
    private Guard[] guards;

    //Fonts
    private Font customFont;
    private Font titleFont;

    //Sounds
    private AudioInputStream diedSound;
    private AudioInputStream teleportSound;
    private AudioInputStream watermelonSound;
    private AudioInputStream keySound;
    private AudioInputStream potionSound;
    private AudioInputStream bananaSound;
    public AudioInputStream levelUpSound;
    private Clip diedClip;
    private Clip teleportClip;
    private Clip watermelonClip;
    private Clip keyClip;
    private Clip potionClip;
    private Clip bananaClip;
    private Clip levelUpClip;

    //Map and item graphics
    BufferedImage map;
    BufferedImage chest;
    BufferedImage banana;
    BufferedImage key;
    BufferedImage enderpearl;
    BufferedImage potion;
    BufferedImage watermelon;

    //Starting settings
    private int level = 1;
    private int lives = 3;
    private int targetRange = 128; //for guards

    //Used to play certain game screens; counters allows it to show for a set amount of time, since counters increase once per update
    //and game updates at 60 times/second.
    private boolean playLevelScreen = true;
    private int playLevelScreenCounter = 0;

    private boolean gameOverScreen = false;
    private int gameOverScreenCounter = 0;

    public PlayScene(SceneManager sceneManager) {

        super(sceneManager);

        //Initializing the classes
        initLevel();

        //Load images
        try {
            map = ImageIO.read(new File("src/Game/Assets/tilemap.png"));
            chest = ImageIO.read(new File("src/Game/Assets/items/chest.png"));
            banana = ImageIO.read(new File("src/Game/Assets/items/banana.png"));
            key = ImageIO.read(new File("src/Game/Assets/items/key.png"));
            enderpearl = ImageIO.read(new File("src/Game/Assets/items/enderpearl.png"));
            potion = ImageIO.read(new File("src/Game/Assets/items/potion.png"));
            watermelon = ImageIO.read(new File("src/Game/Assets/items/watermelon.png"));
        }
        catch(IOException e) {
            System.out.println("Error loading images");
        }

        //Configuring font
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Game/Assets/CompassPro.ttf")).deriveFont(36f);
            titleFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/Game/Assets/CompassPro.ttf")).deriveFont(120f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            ge.registerFont(titleFont);
        } catch (IOException | FontFormatException e) {
            System.out.println("Error loading font");
        }

        //Configuring sound effects
        try {
            diedSound = AudioSystem.getAudioInputStream(new File("src/Game/Assets/music/died.wav"));
            diedClip = AudioSystem.getClip();
            diedClip.open(diedSound);

            teleportSound = AudioSystem.getAudioInputStream(new File("src/Game/Assets/music/teleport.wav"));
            teleportClip = AudioSystem.getClip();
            teleportClip.open(teleportSound);

            watermelonSound = AudioSystem.getAudioInputStream(new File("src/Game/Assets/music/watermelon.wav"));
            watermelonClip = AudioSystem.getClip();
            watermelonClip.open(watermelonSound);

            keySound = AudioSystem.getAudioInputStream(new File("src/Game/Assets/music/key.wav"));
            keyClip = AudioSystem.getClip();
            keyClip.open(keySound);

            potionSound = AudioSystem.getAudioInputStream(new File("src/Game/Assets/music/potion.wav"));
            potionClip = AudioSystem.getClip();
            potionClip.open(potionSound);

            bananaSound = AudioSystem.getAudioInputStream(new File("src/Game/Assets/music/banana.wav"));
            bananaClip = AudioSystem.getClip();
            bananaClip.open(bananaSound);

            levelUpSound = AudioSystem.getAudioInputStream(new File("src/Game/Assets/music/levelup.wav"));
            levelUpClip = AudioSystem.getClip();
            levelUpClip.open(levelUpSound);
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error loading music");
        }

    }

    public void initLevel() {

        tileMap = new TileMap();

        //Place items at random locations
        getRandomTile().key = true;
        getRandomTile().enderpearl = true;
        getRandomTile().potion = true;
        getRandomTile().watermelon = true;

        //Load players at starting position
        player = new Player(new Sprite("src/Game/Assets/knight", "player"), new Position(tileMap.tileArr[25][13].getPosition()), 32, 13, 25);

        //Setting difficulty based on the level
        if(level == 1) {

            guards = new Guard[3];

            guards[0] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[17][1].getPosition()), 32, 1, 17);
            guards[0].right = true;

            guards[1] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[17][25].getPosition()), 32, 25, 17);
            guards[1].left = true;

            guards[2] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[6][13].getPosition()), 32, 13, 6);
            guards[2].right = true;

        }

        else if(level == 2) {

            guards = new Guard[4];

            guards[0] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[17][1].getPosition()), 32, 1, 17);
            guards[0].right = true;

            guards[1] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[17][25].getPosition()), 32, 25, 17);
            guards[1].left = true;

            guards[2] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[6][13].getPosition()), 32, 13, 6);
            guards[2].right = true;

            guards[3] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[11][13].getPosition()), 32, 13, 11);
            guards[3].right = true;

            targetRange = 160;

        }

        else if(level == 3 || level == 4) {

            guards = new Guard[4];

            guards[0] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[17][1].getPosition()), 32, 1, 17);
            guards[0].right = true;

            guards[1] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[17][25].getPosition()), 32, 25, 17);
            guards[1].left = true;

            guards[2] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[6][13].getPosition()), 32, 13, 6);
            guards[2].right = true;

            guards[3] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[11][13].getPosition()), 32, 13, 11);
            guards[3].right = true;

            targetRange = 192;

            if(level == 4)
                targetRange = 224;

            getRandomTile().watermelon = true;

        }

        else if(level >= 5) {

            guards = new Guard[6];

            guards[0] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[17][1].getPosition()), 32, 1, 17);
            guards[0].right = true;

            guards[1] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[17][25].getPosition()), 32, 25, 17);
            guards[1].left = true;

            guards[2] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[6][13].getPosition()), 32, 13, 6);
            guards[2].right = true;

            guards[3] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[11][13].getPosition()), 32, 13, 11);
            guards[3].right = true;

            guards[4] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[5][25].getPosition()), 32, 25, 5);
            guards[4].left = true;

            guards[5] = new Guard(new Sprite("src/Game/Assets/goblin", "guard"), new Position(tileMap.tileArr[5][1].getPosition()), 32, 1, 5);
            guards[5].right = true;

            targetRange = 256;

            if(level == 6)
                targetRange = 288;
            else if(level == 7)
                targetRange = 320;
            else if(level > 7)
                targetRange = 832;
            getRandomTile().watermelon = true;
            getRandomTile().enderpearl = true;
            getRandomTile().potion = true;

        }

    }

    @Override
    public void update() {

        //Main game screen is playing
        if(!playLevelScreen && !gameOverScreen) {

            //Increase powerup counters if needed
            if (player.hasWatermelon) {
                player.watermelonCounter++;
            }
            if (player.hasPotion) {
                player.potionCounter++;
            }

            //Level up condition; player is at exit with banana - update level variable, setup level again, and show the level screen
            if (player.tileX == 13 && player.tileY == 26 && player.hasBanana) {
                level++;
                lives++;
                playSound("levelup");
                initLevel();
                playLevelScreen = true;
            }

            //Disable player's movement in a direction if there is a wall, or if player is not correctly aligned on the tile
            if (player.up && tileMap.tileArr[player.tileY - 1][player.tileX].getType() == 0 || player.position.x != tileMap.tileArr[player.tileY][player.tileX].getPosition().x)
                player.up = false;
            if (player.down && tileMap.tileArr[player.tileY + 1][player.tileX].getType() == 0 || player.position.x != tileMap.tileArr[player.tileY][player.tileX].getPosition().x)
                player.down = false;
            if (player.left && tileMap.tileArr[player.tileY][player.tileX - 1].getType() == 0 || player.position.y != tileMap.tileArr[player.tileY][player.tileX].getPosition().y)
                player.left = false;
            if (player.right && tileMap.tileArr[player.tileY][player.tileX + 1].getType() == 0 || player.position.y != tileMap.tileArr[player.tileY][player.tileX].getPosition().y)
                player.right = false;

            //If the player has chosen then next move, the tile is not a wall, and the player is correctly aligned on the current tile, then set the direction
            if (player.nextDirection == 0 && tileMap.tileArr[player.tileY - 1][player.tileX].getType() != 0 && player.position.x == tileMap.tileArr[player.tileY][player.tileX].getPosition().x) {
                player.up = player.disableAllExcept();
            }
            if (player.nextDirection == 1 && tileMap.tileArr[player.tileY + 1][player.tileX].getType() != 0 && player.position.x == tileMap.tileArr[player.tileY][player.tileX].getPosition().x) {
                player.down = player.disableAllExcept();
            }
            if (player.nextDirection == 2 && tileMap.tileArr[player.tileY][player.tileX - 1].getType() != 0 && player.position.y == tileMap.tileArr[player.tileY][player.tileX].getPosition().y) {
                player.left = player.disableAllExcept();
            }
            if (player.nextDirection == 3 && tileMap.tileArr[player.tileY][player.tileX + 1].getType() != 0 && player.position.y == tileMap.tileArr[player.tileY][player.tileX].getPosition().y) {
                player.right = player.disableAllExcept();
            }

            player.update();

            //Update the tile that the player is on by comparing x and y coordinates
            //Also check if powerups should be still active or not; doing it here ensures that the player is in the center
            //of a tile and stops it from moving into invalid pixels due to speed powerup
            if (player.position.y == tileMap.tileArr[player.tileY - 1][player.tileX].getPosition().y) {
                player.tileY--;
                checkPowerUp();
            } else if (player.position.y == tileMap.tileArr[player.tileY + 1][player.tileX].getPosition().y) {
                player.tileY++;
                checkPowerUp();
            } else if (player.position.x == tileMap.tileArr[player.tileY][player.tileX - 1].getPosition().x) {
                player.tileX--;
                checkPowerUp();
            } else if (player.position.x == tileMap.tileArr[player.tileY][player.tileX + 1].getPosition().x) {
                player.tileX++;
                checkPowerUp();
            }

            //Check if the tile the player is on has items, update player and map accordingly, and play sound
            if (tileMap.tileArr[player.tileY][player.tileX].key) {
                tileMap.tileArr[player.tileY][player.tileX].key = false;
                tileMap.tileArr[1][13].chest = false;
                tileMap.tileArr[1][13].banana = true;
                playSound("key");
            } else if (tileMap.tileArr[player.tileY][player.tileX].enderpearl) {
                tileMap.tileArr[player.tileY][player.tileX].enderpearl = false;
                playSound("teleport");
                Tile random = getRandomTile();
                player.position = new Position(random.getPosition());
                player.tileX = random.tileX;
                player.tileY = random.tileY;
            } else if (tileMap.tileArr[player.tileY][player.tileX].watermelon) {
                tileMap.tileArr[player.tileY][player.tileX].watermelon = false;
                playSound("watermelon");
                player.hasWatermelon = true;
                player.speed = 4;
            } else if (tileMap.tileArr[player.tileY][player.tileX].potion) {
                tileMap.tileArr[player.tileY][player.tileX].potion = false;
                playSound("potion");
                player.hasPotion = true;
            } else if (tileMap.tileArr[player.tileY][player.tileX].banana) {
                tileMap.tileArr[player.tileY][player.tileX].banana = false;
                playSound("banana");
                player.hasBanana = true;
            }

            //Update each guard
            for (Guard guard : guards) {

                //Restrict movement if guard is not perfectly lined up on the tile
                if ((guard.up || guard.down) && guard.position.x != tileMap.tileArr[guard.tileY][guard.tileX].getPosition().x) {
                    guard.up = false;
                    guard.down = false;
                }
                if ((guard.left || guard.right) && guard.position.y != tileMap.tileArr[guard.tileY][guard.tileX].getPosition().y) {
                    guard.left = false;
                    guard.right = false;
                }

                //Calculate which direction to go if chasing player and at an intersection where a turn can be made
                if (guard.isChasing && tileMap.tileArr[guard.tileY][guard.tileX].getType() == 2 && !guard.choseMovement) {

                    while (true) {
                        //Call path finding method with start and target tile
                        int targetDirection = pathfind(tileMap.tileArr[guard.tileY][guard.tileX], tileMap.tileArr[player.tileY][player.tileX]);

                        //After direction is found, check if it is valid (not a wall)
                        if (targetDirection == 0 && tileMap.tileArr[guard.tileY - 1][guard.tileX].getType() != 0) {
                            guard.up = guard.disableAllExcept();
                            break;
                        } else if (targetDirection == 1 && tileMap.tileArr[guard.tileY + 1][guard.tileX].getType() != 0) {
                            guard.down = guard.disableAllExcept();
                            break;
                        } else if (targetDirection == 2 && tileMap.tileArr[guard.tileY][guard.tileX - 1].getType() != 0) {
                            guard.left = guard.disableAllExcept();
                            break;
                        } else if (targetDirection == 3 && tileMap.tileArr[guard.tileY][guard.tileX + 1].getType() != 0) {
                            guard.right = guard.disableAllExcept();
                            break;
                        }

                        //If direction was invalid, keep trying to find optimal direction until it can be performed
                    }

                    //Keeps track if a move has already been calculated when guard is on intersection tile
                    guard.choseMovement = true;

                }
                //If at intersection and not chasing
                else if (guard.randomMovement && tileMap.tileArr[guard.tileY][guard.tileX].getType() == 2 && !guard.choseMovement) {
                    while (true) {
                        int randomDirection = (int) (Math.random() * (3 + 1)); //Generates random direction (0-3)
                        if (randomDirection == 0 && tileMap.tileArr[guard.tileY - 1][guard.tileX].getType() != 0 && !guard.down) {
                            guard.up = guard.disableAllExcept();
                            break;
                        } else if (randomDirection == 1 && tileMap.tileArr[guard.tileY + 1][guard.tileX].getType() != 0 && !guard.up) {
                            guard.down = guard.disableAllExcept();
                            break;
                        } else if (randomDirection == 2 && tileMap.tileArr[guard.tileY][guard.tileX - 1].getType() != 0 && !guard.right) {
                            guard.left = guard.disableAllExcept();
                            break;
                        } else if (randomDirection == 3 && tileMap.tileArr[guard.tileY][guard.tileX + 1].getType() != 0 && !guard.left) {
                            guard.right = guard.disableAllExcept();
                            break;
                        }
                        //Keeps generating random direction until valid movement is found (does not bump into wall, or the same direction they came from)
                    }
                    guard.choseMovement = true;
                }
                //When not at an intersection just follow the maze path
                else {

                    if (tileMap.tileArr[guard.tileY][guard.tileX].getType() == 1)
                        guard.choseMovement = false;

                    //Update movement if wall is in front
                    if (guard.up && tileMap.tileArr[guard.tileY - 1][guard.tileX].getType() == 0) {
                        guard.up = false;
                        followPath(guard, 0);
                    }
                    if (guard.down && tileMap.tileArr[guard.tileY + 1][guard.tileX].getType() == 0) {
                        guard.down = false;
                        followPath(guard, 1);
                    }
                    if (guard.left && tileMap.tileArr[guard.tileY][guard.tileX - 1].getType() == 0) {
                        guard.left = false;
                        followPath(guard, 2);
                    }
                    if (guard.right && tileMap.tileArr[guard.tileY][guard.tileX + 1].getType() == 0) {
                        guard.right = false;
                        followPath(guard, 3);
                    }

                }

                guard.update();

                //Update guard's tile X and Y based on pixel coordinates
                if (guard.position.y == tileMap.tileArr[guard.tileY - 1][guard.tileX].getPosition().y)
                    guard.tileY--;
                else if (guard.position.y == tileMap.tileArr[guard.tileY + 1][guard.tileX].getPosition().y)
                    guard.tileY++;
                else if (guard.position.x == tileMap.tileArr[guard.tileY][guard.tileX - 1].getPosition().x)
                    guard.tileX--;
                else if (guard.position.x == tileMap.tileArr[guard.tileY][guard.tileX + 1].getPosition().x)
                    guard.tileX++;

                //Collision between player and guard
                if (guard.position.within(player.position, 12) && !player.hasPotion) {
                    playSound("died");
                    lives--;
                    //Game over condition
                    if (lives == 0)
                        gameOverScreen = true;
                    initLevel();
                } else if (player.hasBanana && !player.hasPotion) //Always chase if player has banana
                    guard.isChasing = true;
                else if (guard.position.within(player.position, targetRange) && !player.hasPotion) //Chase if within the target range
                    guard.isChasing = true;
                else
                    guard.isChasing = false; //No chasing - player doesn't have the banana and is too far away to be detected

            }

        }

    }

    //Send input to player object
    @Override
    public void input(UserInput userInput) {
        if(!playLevelScreen)
            player.input(userInput);
    }

    @Override
    public void render(Graphics2D g) {

        //Draw according to the screen
        if(playLevelScreen) {

            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, 832, 832);
            g.setColor(new Color(255, 255, 255));
            g.setFont(customFont);
            g.drawString("LEVEL " + level, 365, 420);

            //update counter to keep time
            playLevelScreenCounter++;

            if(playLevelScreenCounter == 120) {
                playLevelScreen = false;
                playLevelScreenCounter = 0;
            }

        }

        else if(gameOverScreen) {

            g.setColor(new Color(0, 0, 0));
            g.fillRect(0, 0, 832, 832);
            g.setColor(new Color(110, 0, 5));
            g.setFont(titleFont);
            g.drawString("GAME OVER", 160, 420);

            gameOverScreenCounter++;

            if(gameOverScreenCounter == 120) {

                //Creating custom JOptionPane to get player name input
                String [] options = {"OK"};
                JPanel panel = new JPanel();
                JLabel label = new JLabel("Enter your name: ");
                JTextField textField = new JTextField(10);
                panel.add(label);
                panel.add(textField);
                JOptionPane.showOptionDialog(null, panel, "Record Your Score", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                while(true) {

                    //Valid input
                    if(!textField.getText().isBlank()) {
                        try {

                            //Read the existing scores from the text file and load into Score objects
                            ArrayList<Score> scores = new ArrayList<Score>();
                            String s;
                            BufferedReader br = new BufferedReader(new FileReader(new File("src/Game/highscores.txt")));
                            while((s = br.readLine()) != null){
                                scores.add(new Score(s.substring(0, s.indexOf(' ')), Integer.parseInt(s.substring(s.indexOf(' ') + 1))));
                            }
                            br.close();
                            Score currentScore = new Score(textField.getText(), level-1); //Score based on the number of bananas stolen

                            //Binary search using name
                            scores.sort(new SortName());
                            int bs = Collections.binarySearch(scores, currentScore, new SortName());
                            //If player did not play before then add new entry
                            if(bs < 0)
                                scores.add(Math.abs(bs+1), currentScore);
                            else if(scores.get(bs).getScore() < currentScore.getScore()) //If player did play before then update score only if it's higher
                                scores.set(bs, currentScore);

                            //Load into treemap to sort
                            TreeMap<Score, String> treeMap = new TreeMap<>();
                            for(Score score: scores)
                                treeMap.put(score, score.getName());
                            FileWriter fw = new FileWriter("src/Game/highscores.txt", false);
                            PrintWriter pw = new PrintWriter(fw);

                            //Write into the textfile the top 10 scores
                            for(int i=0; i < 10; i++) {
                                Score score = treeMap.firstKey();
                                pw.println(score.getName() + " " + score.getScore());
                                treeMap.remove(score);
                            }
                            pw.close();
                            //Calling scene manager to switch back to menu
                            super.switchScene("menu");
                        }
                        catch (IOException e) {
                            System.out.println("Error adding highscore");
                        }
                        break;

                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Invalid input!", "", JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showOptionDialog(null, panel, "Record Your Score", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    }

                }

            }

        }
        else {

            //Drawing main game screen

            //Background
            g.drawImage(map, 0, 0, null);

            //Draw items
            for (int i = 0; i < 28; i++) {
                for (int j = 0; j < 27; j++) {
                    if (tileMap.tileArr[i][j].banana)
                        g.drawImage(banana, tileMap.tileArr[i][j].getPosition().x - 12, tileMap.tileArr[i][j].getPosition().y - 12, 24, 24, null);
                    else if (tileMap.tileArr[i][j].chest)
                        g.drawImage(chest, tileMap.tileArr[i][j].getPosition().x - 12, tileMap.tileArr[i][j].getPosition().y - 12, 24, 24, null);
                    else if (tileMap.tileArr[i][j].key)
                        g.drawImage(key, tileMap.tileArr[i][j].getPosition().x - 12, tileMap.tileArr[i][j].getPosition().y - 12, 24, 24, null);
                    else if (tileMap.tileArr[i][j].enderpearl)
                        g.drawImage(enderpearl, tileMap.tileArr[i][j].getPosition().x - 12, tileMap.tileArr[i][j].getPosition().y - 12, 24, 24, null);
                    else if (tileMap.tileArr[i][j].potion)
                        g.drawImage(potion, tileMap.tileArr[i][j].getPosition().x - 12, tileMap.tileArr[i][j].getPosition().y - 12, 24, 24, null);
                    else if (tileMap.tileArr[i][j].watermelon)
                        g.drawImage(watermelon, tileMap.tileArr[i][j].getPosition().x - 12, tileMap.tileArr[i][j].getPosition().y - 12, 24, 24, null);
                }

//                //Show grid
//                g.drawRect(tileMap.tileArr[i][j].getPosition().x - 16, tileMap.tileArr[i][j].getPosition().y - 16, 32, 32);

//                //Show all intersections
//                if(tileMap.tileArr[i][j].getType() == 2) {
//                    g.fillRect(tileMap.tileArr[i][j].getPosition().x - 16, tileMap.tileArr[i][j].getPosition().y - 16, 32, 32);
//                }

            }

            //Draw player
            player.render(g);

            //Draw guards
            for (Guard guard : guards) {
                guard.render(g);

                //Show tile of guard
//            g.fillRect(tileMap.tileArr[guard.tileY][guard.tileX].getPosition().x - 16, tileMap.tileArr[guard.tileY][guard.tileX].getPosition().y - 16, 32, 32);

            }

            //Show tile of player
//        g.fillRect(tileMap.tileArr[player.tileY][player.tileX].getPosition().x - 16, tileMap.tileArr[player.tileY][player.tileX].getPosition().y - 16, 32, 32);

            //Level and lives text
            g.setFont(customFont);
            g.drawString("Level: " + level, 16, 816);
            g.drawString("Lives: " + lives, 710, 816);
        }
    }

    //Checks if powerup should still be active or not using their counter - if counter passed the specified time, then revert player back to normal
    public void checkPowerUp () {
        if (player.watermelonCounter >= 60) {
            player.hasWatermelon = false;
            player.watermelonCounter = 0;
            player.speed = 2;
        }
        if (player.potionCounter >= 120) {
            player.hasPotion = false;
            player.potionCounter = 0;
        }
    }

    //Follows a maze path by setting guard movement in the forward direction if there is not a wall there and is not making guard reverse
    public void followPath(Guard guard, int direction) {
        if (tileMap.tileArr[guard.tileY - 1][guard.tileX].getType() != 0 && direction != 1)
            guard.up = true;
        else if (tileMap.tileArr[guard.tileY + 1][guard.tileX].getType() != 0 && direction != 0)
            guard.down = true;
        else if (tileMap.tileArr[guard.tileY][guard.tileX - 1].getType() != 0 && direction != 3)
            guard.left = true;
        else if (tileMap.tileArr[guard.tileY][guard.tileX + 1].getType() != 0 && direction != 2)
            guard.right = true;
    }

    /*
        Description: Finds the optimal path between 2 tiles on the grid using A* algorithm
        Parameters: The starting Tile, and the target Tile
        Return: The integer denoting the direction the goblin will take to go on the optimal path
    */
    public int pathfind(Tile start, Tile target) {

        //the visited tiles
        Set<Tile> visited = new HashSet<Tile>();

        //priority queue that prioritizes tiles with the lowest F value. That way, the tile with the lowest f value will lead to the optimal path.
        //Tiles in this queue will be used to find possible optimal paths
        PriorityQueue<Tile> queue = new PriorityQueue<Tile>();

        try {
            //target 5 tiles above or below the player based on player movement and type of tile to try and predict movement and catch player
            if (player.up && tileMap.tileArr[target.tileY-5][target.tileX].getType() != 0)
                target = tileMap.tileArr[target.tileY-5][target.tileX];
            else if (player.down && tileMap.tileArr[target.tileY+5][target.tileX].getType() != 0)
                target = tileMap.tileArr[target.tileY+5][target.tileX];
            else
                throw(new ArrayIndexOutOfBoundsException());

        }
        catch (ArrayIndexOutOfBoundsException e) {

            //If out of bounds or tile type is a wall then just target player tile instead
            target = tileMap.tileArr[player.tileY][player.tileX];

        }

        //gcost denotes the movement cost (moving 1 tile from current tile in any direction (up/down/left/right)  will cost 1)
        //hcost denotes the the predicted cost of the tile to the target, by using the manhattan distance between the tiles
        //fcost is the sum of gcost and fcost

        start.gCost = 0;
        start.hCost = Math.abs(start.getPosition().y - target.getPosition().y) + Math.abs(start.getPosition().x - target.getPosition().x);
        start.fCost = start.gCost + start.hCost;

        //Add to priority queue to explore tile
        queue.add(start);
        boolean found = false;

        //Keep searching until it is determined that there are no paths or until the target tile is found
        while (!queue.isEmpty() && !found) {

            //Pull highest priority tile to explore
            Tile current = queue.poll();
            //Add to visited set
            visited.add(current);

            //Check if target tile is found
            if (current.equals(target))
                found = true;

            //Check all possible directions of current tile and add to priority queue with its calculated costs

            //CHECK UP
            if (tileMap.tileArr[current.tileY - 1][current.tileX].getType() != 0) {
                Tile child = tileMap.tileArr[current.tileY - 1][current.tileX];

                //calculate costs of the child tile
                child.gCost = current.gCost + 1;
                child.hCost = Math.abs(child.tileY- target.tileY) + Math.abs(child.tileX - target.tileX);
                child.fCost = child.gCost + child.gCost;

                //only add to priority queue if it is not currently in the visited set or the priority queue
                if (!queue.contains(child) && !visited.contains(child)) {
                    child.parent = current; //Add current tile as parent so the route can be retraced later
                    queue.add(child);
                }
            }

            //CHECK DOWN
            if (tileMap.tileArr[current.tileY + 1][current.tileX].getType() != 0) {
                Tile child = tileMap.tileArr[current.tileY + 1][current.tileX];
                child.gCost = current.gCost + 1;
                child.hCost = Math.abs(child.tileY - target.tileY) + Math.abs(child.tileX - target.tileX);
                child.fCost = child.gCost + child.gCost;

                if (!queue.contains(child) && !visited.contains(child)) {
                    child.parent = current;
                    queue.add(child);
                }
            }

            //CHECK LEFT
            if (tileMap.tileArr[current.tileY][current.tileX-1].getType() != 0) {
                Tile child = tileMap.tileArr[current.tileY][current.tileX - 1];
                child.gCost = current.gCost + 1;
                child.hCost = Math.abs(child.tileY - target.tileY) + Math.abs(child.tileX - target.tileX);
                child.fCost = child.gCost + child.gCost;

                if (!queue.contains(child) && !visited.contains(child)) {
                    child.parent = current;
                    queue.add(child);
                }
            }

            //CHECK RIGHT
            if (tileMap.tileArr[current.tileY][current.tileX + 1].getType() != 0) {
                Tile child = tileMap.tileArr[current.tileY][current.tileX+1];
                child.gCost = current.gCost + 1;
                child.hCost = Math.abs(child.tileY - target.tileY) + Math.abs(child.tileX - target.tileX);
                child.fCost = child.gCost + child.gCost;

                if (!queue.contains(child) && !visited.contains(child)) {
                    child.parent = current;
                    queue.add(child);
                }
            }

        }

        //Retracing the path
        while (target.parent != null) {
            Tile child = target;
            target = target.parent;
            child.parent = null; //Remove parent
            if(target.parent == start)
                //If one tile away from parent then stop retracing. The position of this tile will determine the direction to go in the intersection
                break;
        }

        //Return the direction based on position of the retraced tile
        if (target.tileY == start.tileY - 1)
            return 0;
        else if (target.tileY == start.tileY + 1)
            return 1;
        else if (target.tileX == start.tileX - 1)
            return 2;
        else if (target.tileX == start.tileX + 1)
            return 3;
        else
            return (int) (Math.random() * (3 + 1)); //In the case that the current and target tile are the same, then just choose a random direction
    }

    //Returns a random tile that is not a wall on the tile map
    public Tile getRandomTile () {
        while(true) {
            int x = (int) (Math.random() * (27));
            int y = (int) (Math.random() * (23));
            //Making sure items don't appear on walls or stack on each other
            if(!tileMap.tileArr[y][x].hasItem && tileMap.tileArr[y][x].getType() != 0) {
                tileMap.tileArr[y][x].hasItem = true;
                return tileMap.tileArr[y][x];
            }
        }
    }

    //Play sound clip based on the given effect parameter
    public void playSound (String effect) {
        switch (effect) {
            case "died":
                diedClip.setMicrosecondPosition(0);
                diedClip.start();
                break;
            case "teleport":
                teleportClip.setMicrosecondPosition(0);
                teleportClip.start();
                break;
            case "key":
                keyClip.setMicrosecondPosition(0);
                keyClip.start();
                break;
            case "watermelon":
                watermelonClip.setMicrosecondPosition(0);
                watermelonClip.start();
                break;
            case "potion":
                potionClip.setMicrosecondPosition(0);
                potionClip.start();
                break;
            case "banana":
                bananaClip.setMicrosecondPosition(0);
                bananaClip.start();
                break;
            case "levelup":
                levelUpClip.setMicrosecondPosition(0);
                levelUpClip.start();
                break;
        }
    }

}
