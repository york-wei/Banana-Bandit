package Game.Utilities;

import Game.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class UserInput implements KeyListener {

    public static ArrayList<Key> keys = new ArrayList<Key>();

    //Add listener to the panel
    public UserInput (GamePanel gamePanel) {

        gamePanel.addKeyListener(this);

    }

    public static class Key {

        public boolean isPressed;

        //Add keys to the array list
        public Key () {

            keys.add(this);

        }

        //Change the key state
        public void toggle(boolean pressed) {

            if(isPressed != pressed) {

                isPressed = pressed;

            }

        }

    }

    //Initializing the keys
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key select = new Key();

    //Change the state of the key and mapping the keys to its key object
    public void toggle (KeyEvent e, boolean pressed) {

        if(e.getKeyCode() == KeyEvent.VK_W) {
            up.toggle(pressed);
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            down.toggle(pressed);
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            left.toggle(pressed);
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            right.toggle(pressed);
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            select.toggle(pressed);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) { }

    //Change the key state using KeyListener interface
    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e, false);
    }

}
