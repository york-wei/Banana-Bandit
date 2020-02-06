package Game.Entities;

import Game.Graphics.Sprite;
import Game.Utilities.Position;
import Game.Utilities.UserInput;

import java.awt.*;

public class Guard extends Entity {

    public int tileX;
    public int tileY;

    public boolean randomMovement = true;
    public boolean choseMovement = false;

    public boolean isChasing = false;

    //Set idle when initialized
    public int nextDirection = 4;

    public Guard(Sprite sprite, Position position, int size, int tileX, int tileY) {
        super(sprite, position, size);
        this.tileX = tileX;
        this.tileY = tileY;
    }

    //Update the guard position based on given direction
    public void update() {

        super.update();
        move();
        position.x += dx;
        position.y += dy;

    }

    //Getting the input
    @Override
    public void input(UserInput userInput) {
    }

    //Rendering the frame of the guard
    @Override
    public void render(Graphics2D g) {
        g.drawImage(animation.getFrame(), position.x - 16, position.y - 16, size, size, null);
        if(isChasing)
            g.drawImage(emote.getFrame(), position.x - 12, position.y - 40, 24, 24, null);
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
