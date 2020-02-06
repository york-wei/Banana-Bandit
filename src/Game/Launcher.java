package Game;

import javax.swing.*;

public class Launcher {

    public static void main(String [] args) {

        //Setting up the main JFrame
        JFrame window = new JFrame();
        window.setIconImage(new ImageIcon("src/Game/Assets/items/banana.png").getImage());
        window.setTitle("Banana Bandit");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new GamePanel(832, 832));
        window.setIgnoreRepaint(true);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }

}

