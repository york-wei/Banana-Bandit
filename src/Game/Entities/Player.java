package Game.Entities;

import Game.Graphics.Sprite;
import Game.Utilities.Position;
import Game.Utilities.UserInput;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class Player extends Entity {

    public int tileX;
    public int tileY;

    //Set idle when initialized
    public int nextDirection = 4;

    public boolean hasWatermelon = false;
    public int watermelonCounter = 0;

    public boolean hasPotion = false;
    public int potionCounter = 0;

    public boolean hasBanana = false;

    public Player(Sprite sprite, Position position, int size, int tileX, int tileY) {
        super(sprite, position, size);
        this.tileX = tileX;
        this.tileY = tileY;
    }

    //Update the player position based on input
    public void update() {

        super.update();
        move();
        position.x += dx;
        position.y += dy;

    }

    //Getting the input
    @Override
    public void input(UserInput userInput) {

        up = userInput.up.isPressed;
        down = userInput.down.isPressed;
        left = userInput.left.isPressed;
        right = userInput.right.isPressed;

        //Allows for continuous movement if there is no change in direction
        if (currentDirection == 0 && !down && !left && !right)
            up = disableAllExcept();
        else if (currentDirection == 1 && !up && !left && !right)
            down = disableAllExcept();
        else if (currentDirection == 2 && !up && !down && !right)
            left = disableAllExcept();
        else if (currentDirection == 3 && !up && !down && !left)
            right = disableAllExcept();
        else {

            //Set up the next direction if user has pressed another direction
            if (up) {
                nextDirection = 0;
                up = disableAllExcept();
            } else if (down) {
                nextDirection = 1;
                down = disableAllExcept();
            } else if (left) {
                nextDirection = 2;
                left = disableAllExcept();
            } else if (right) {
                nextDirection = 3;
                right = disableAllExcept();
            }

            //Keep the current direction
            if(currentDirection == 0)
                up = true;
            else if(currentDirection == 1)
                down = true;
            else if(currentDirection == 2)
                left = true;
            else if(currentDirection == 3)
                right = true;
            else if(currentDirection == 4)
                disableAllExcept();

        }
    }

    //Rendering the frame of the player
    @Override
    public void render(Graphics2D g) {

        BufferedImage frame = animation.getFrame();

        //Change the alpha of the buffered image to make it look like the player is invisible
        if(hasPotion) {
            RescaleOp alpha = new RescaleOp(new float[]{1.0f, 1.0f, 1.0f, 0.5f}, new float[]{0f, 0f, 0f, 0f}, null);
            frame = alpha.filter(frame, null);
        }

        g.drawImage(frame, position.x - 16, position.y - 16, size, size, null);

    }

    //Updating the player position after input is received
    //If the direction is no longer valid, then stop moving
    public void move() {

        if(up) {
            dy = -speed;
        }
        else if(dy < 0) {
            dy = 0;
        }

        if(down) {
            dy = speed;
        }
        else if(dy > 0) {
            dy = 0;
        }

        if(left) {
            dx = -speed;
        }
        else if(dx < 0) {
            dx = 0;
        }

        if(right) {
            dx = speed;
        }
        else if(dx > 0) {
            dx = 0;
        }

    }

}
