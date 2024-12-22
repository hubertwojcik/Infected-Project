import enums.GameState;

import javax.swing.*;
import java.awt.*;

public class PauseScreen extends JPanel {
    public PauseScreen(GamePanel app) {
        this.setLayout(new GridBagLayout()); // Wyśrodkowanie przycisków
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton resumeButton = new JButton("Resume");
        JButton exitButton = new JButton("Exit");

        // Akcje przycisków
        resumeButton.addActionListener(e -> app.setGameState(GameState.PLAYING));
        exitButton.addActionListener(e -> System.exit(0));

        // Dodanie przycisków do panelu
        gbc.gridy = 0;
        this.add(resumeButton, gbc);
        gbc.gridy = 1;
        this.add(exitButton, gbc);
    }
}
