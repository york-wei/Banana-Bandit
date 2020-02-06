package Game.Scenes;

import Game.Utilities.UserInput;

import java.awt.*;

//Abstract class for creating Scene classes
public abstract class Scene {

    private SceneManager sceneManager;

    //Initialized with the scene manager object so child classes can call scene manager through this abstract class
    public Scene (SceneManager sceneManager) {

        this.sceneManager = sceneManager;

    }

    //calls the scene manager to switch scenes
    public void switchScene(String scene) {
        sceneManager.switchScene(scene);
    }

    public abstract void update();

    public abstract void input(UserInput userInput);

    public abstract void render(Graphics2D g);

}
