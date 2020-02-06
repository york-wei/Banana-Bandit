package Game.Graphics;

import java.awt.image.BufferedImage;

public class Animation {

    private BufferedImage[] frames;
    private int currentFrame;
    private int numFrames;

    private int count;
    private int delay;

    public Animation () {}

    /*
        Description: Allows user to set up frames for animation and choose delay time between each frame
        Parameters: An array of Buffered Images that contain individual frames of an entity's animation
        Return: None
    */
    public void setFrames(BufferedImage [] frames, int delay) {

        this.frames = frames;
        currentFrame = 0;
        numFrames = frames.length;
        count = 0;
        this.delay = delay;

    }

    public void update() {

        //Updating the animation by changing the frame after the specified delay. If the end of the animation is reached,
        //animation loops back to the first frame.
        count++;
        if(count == delay) {
            currentFrame++;
            count = 0;
        }
        if(currentFrame == numFrames) {
            currentFrame = 0;
        }

    }

    //Returns the current frame of animation
    public BufferedImage getFrame() {
        return frames[currentFrame];
    }

}
