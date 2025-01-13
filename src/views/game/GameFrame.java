package views.game;

import game.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameFrame extends JFrame {
    public GameFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("GUI Projekt!");
        this.setSize(GameSettings.windowWidth, GameSettings.windowHeight);
        this.setLocationRelativeTo(null);
        this.setLayout(new CardLayout());


        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e){
                int newWidth = getWidth();
                int newHeight = getHeight();

                GameSettings.updateScales(newWidth, newHeight);
            }
        });
    }
}
