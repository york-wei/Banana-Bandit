package Game.Entities;

import Game.Graphics.Animation;
import Game.Graphics.Sprite;
import Game.Utilities.Position;
import Game.Utilities.UserInput;

import java.awt.*;

public abstract class Entity {

    //What direction the entity is facing; 0 --> left, 1 --> right
    public int facing;
    public final int FACING_LEFT = 0;
    public final int FACING_RIGHT = 1;

    public int currentDirection;
    public final int UP = 0;
    public final int DOWN = 1;
    public final int LEFT = 2;
    public final int RIGHT = 3;
    public final int IDLE = 4;

    //Directions
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;

    //Used for rendering and keeping track of position
    public Animation animation;
    public Animation emote;
    public Sprite sprite;
    public Position position;
    public int size;

    //Movement variables - changes in x, y, and the speed
    public int dx;
    public int dy;
    public int speed = 2;

    //Constructor for entity
    public Entity(Sprite sprite, Position position, int size) {
        this.sprite = sprite;
        this.position = position;
        this.size = size;

        animation = new Animation();
        animation.setFrames(sprite.getFrames(3), 6); //Set default starting animation

        emote = new Animation();
        emote.setFrames(sprite.getFrames(4), 6);

        facing = FACING_RIGHT;
        currentDirection = IDLE;
    }

    //Checks for any changes in animation. If the direction changes, then the animation frames are changed using the setframes function
    //to switch to a new animation
    public void animate() {

        if(up) {
            if(currentDirection != UP) {
                currentDirection = UP;
                animation.setFrames(sprite.getFrames(facing), 6);
            }
        }
        else if(down) {
            if(currentDirection != DOWN) {
                currentDirection = DOWN;
                animation.setFrames(sprite.getFrames(facing), 6);
            }
        }
        else if(left) {
            if(currentDirection != LEFT) {
                facing = FACING_LEFT;
                currentDirection = LEFT;
                animation.setFrames(sprite.getFrames(facing), 6);
            }
        }
        else if(right) {
            if(currentDirection != RIGHT) {
                facing = FACING_RIGHT;
                currentDirection = RIGHT;
                animation.setFrames(sprite.getFrames(facing), 6);
            }
        }
        else {
            if(currentDirection != IDLE) {
                currentDirection = IDLE;
                animation.setFrames(sprite.getFrames(facing + 2), 6);
            }
        }

        animation.update();
        emote.update();

    }

    public void update () {
        animate();
    }

    public abstract void input(UserInput userInput);

    public abstract void render(Graphics2D g);

    //Disables all directions, and then returns true. Used as such: set the direction you want to have enabled to this method call
    //so all directions are disabled except that one.
    public boolean disableAllExcept() {
        up = false;
        down = false;
        left = false;
        right = false;
        return true;
    }

}
