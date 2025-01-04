package views;

import controllers.AppController;


import javax.swing.*;
import java.awt.*;

public class StartGameView extends JPanel {

    public StartGameView(AppController app) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton startButton = new JButton("New Game - nowa gra");
        JButton highScoresButton = new JButton("High scores - tabela wyników");
        JButton exitButton = new JButton("Exit - wyjście");

        startButton.addActionListener(e -> {
            app.showDifficultySelection();
        });

        highScoresButton.addActionListener(e->{
            app.showHighScoresView();
        });

        exitButton.addActionListener(e -> System.exit(0));

        gbc.gridy = 0;
        this.add(startButton, gbc);
        gbc.gridy = 1;
        this.add(highScoresButton,gbc);
        gbc.gridy = 2;
        this.add(exitButton, gbc);
    }
}
