package views.game;

import components.StyledButton;
import controllers.AppController;
import util.GameSettings;


import javax.swing.*;
import java.awt.*;

public class StartGameView extends JPanel {

    public StartGameView(AppController app) {
        this.setLayout(new GridBagLayout());
        this.setBackground((GameSettings.mainBackgroundGrey));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBackground(GameSettings.mainBackgroundGrey);

        JLabel label = new JLabel("Zacznij rozgrywkę w Treat inc!");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 38));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        containerPanel.add(label);
        containerPanel.add(Box.createVerticalStrut(60));


        JButton startButton = new StyledButton("New Game - nowa gra", Color.GREEN,Color.WHITE);
        startButton.addActionListener(e -> {
            app.showDifficultySelection();
        });

        JButton highScoresButton = new StyledButton("High scores - tabela wyników", Color.orange,Color.WHITE);
        highScoresButton.addActionListener(e->{
            app.showHighScoresView();
        });

        JButton exitButton = new StyledButton("Exit - wyjście",Color.RED,Color.WHITE);
        exitButton.addActionListener(e -> System.exit(0));


        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        highScoresButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        containerPanel.add(startButton);
        containerPanel.add(Box.createVerticalStrut(15));

        containerPanel.add(highScoresButton);
        containerPanel.add(Box.createVerticalStrut(15));

        containerPanel.add(exitButton);


        this.add(containerPanel, gbc);
    }
}
