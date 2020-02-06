package Game.Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sprite {

    private BufferedImage[][] spriteArray;

    //Constructor loads the sprites into BufferedImage Array, given the file path
    //Since it is a 2D array, the frames of each movement animation can be stored in an array, in an array.
    public Sprite(String file, String entity) {

            spriteArray = new BufferedImage[5][6];

            for (int i = 0; i < 6; i++) {

                try {
                    spriteArray[0][i] = ImageIO.read(new File(file + "/left/run/" + i + ".png"));
                    spriteArray[1][i] = ImageIO.read(new File(file + "/right/run/" + i + ".png"));
                    spriteArray[2][i] = ImageIO.read(new File(file + "/left/idle/" + i + ".png"));
                    spriteArray[3][i] = ImageIO.read(new File(file + "/right/idle/" + i + ".png"));
                    spriteArray[4][i] = ImageIO.read(new File(file + "/emote/" + i + ".png"));
                } catch (IOException e) {
                    System.out.println("Error reading sprites");
                }

            }

    }

    /*
        Description: Allows user to access the sprites for a specific animation
        Parameters: An integer that specifies what type of animation is needed
        Return: An array of frames that make up the specified animation.
    */
    public BufferedImage[] getFrames (int direction) {
        return spriteArray[direction];
    }

}
