package Game.Scenes;

import Game.Utilities.UserInput;

import java.awt.*;

public class SceneManager {

    private Scene currentScene;

    public SceneManager() {

        //First time show the intro sequence
        currentScene = new MenuScene(this, true);

    }

    //Update the current scene
    public void update () {

        currentScene.update();

    }

    //Send input to the current scene
    public void input (UserInput userInput) {

        currentScene.input(userInput);

    }

    //Render the current scene
    public void render (Graphics2D g) {

        currentScene.render(g);

    }

    //Changes the current scene to another
    public void switchScene(String scene) {

        if(scene.equals("menu"))
            currentScene = new MenuScene(this, false);
        else if(scene.equals("play"))
            currentScene = new PlayScene(this);

    }

}
