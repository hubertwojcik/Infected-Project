package views;

import controllers.AppController;
import controllers.PauseController;
import enums.GameState;

import javax.swing.*;
import java.awt.*;

public class PauseView extends JPanel {
    public PauseView(PauseController pauseController) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton resumeButton = new JButton("Resume");
        JButton exitButton = new JButton("Exit");

        // Akcje przycisków
        resumeButton.addActionListener(e -> {
            pauseController.resumeGame();

        });
        exitButton.addActionListener(e -> pauseController.exitGame());

        // Dodanie przycisków do panelu
        gbc.gridy = 0;
        this.add(resumeButton, gbc);
        gbc.gridy = 1;
        this.add(exitButton, gbc);
    }
}
