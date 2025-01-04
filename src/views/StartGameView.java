package views;

import controllers.AppController;
import enums.GameState;


import javax.swing.*;
import java.awt.*;

public class StartGameView extends JPanel {

    public StartGameView(AppController app) {
        this.setLayout(new GridBagLayout()); // Wyśrodkowanie przycisków
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton startButton = new JButton("Start New Game");
        JButton exitButton = new JButton("Exit");

        // Akcje przycisków
        startButton.addActionListener(e -> {
            app.startGame();
        });
        exitButton.addActionListener(e -> System.exit(0));

        // Dodanie przycisków do panelu
        gbc.gridy = 0;
        this.add(startButton, gbc);
        gbc.gridy = 1;
        this.add(exitButton, gbc);
    }
}
