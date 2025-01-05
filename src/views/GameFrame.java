package views;

import config.GameSettings;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("GUI Projekt!");
        this.setSize(GameSettings.windowWidth, GameSettings.windowHeight);
        this.setLocationRelativeTo(null);
        this.setLayout(new CardLayout());

    }
}
