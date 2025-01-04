package views;

import config.Config;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("My super game!");
        this.setSize(Config.windowWidth,Config.windowHeight);
        this.setLocationRelativeTo(null);
        this.setLayout(new CardLayout());

    }
}
