package views.selectDifficulty;

import components.StyledButton;
import enums.DifficultyLevel;
import controllers.AppController;
import util.GameSettings;

import javax.swing.*;
import java.awt.*;

public class SelectDifficultyLevelView extends JPanel {

    public SelectDifficultyLevelView(AppController app) {
        this.setLayout(new GridBagLayout());
        this.setBackground((GameSettings.mainBackgroundGrey));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBackground(GameSettings.mainBackgroundGrey);

        JLabel label = new JLabel("Wybierz poziom trudności:");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 28));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);


        containerPanel.add(label);
        containerPanel.add(Box.createVerticalStrut(20));

        JButton easyButton = new StyledButton("Easy - łatwy", Color.GREEN,Color.WHITE);
        easyButton.addActionListener(e -> app.startNewGame(DifficultyLevel.EASY));

        JButton mediumButton = new StyledButton("Medium - średni", Color.orange,Color.WHITE);
        mediumButton.addActionListener(e -> app.startNewGame(DifficultyLevel.MEDIUM));

        JButton hardButton = new StyledButton("Hard - trudny",Color.RED,Color.WHITE);
        hardButton.addActionListener(e -> app.startNewGame(DifficultyLevel.HARD));


        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mediumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        containerPanel.add(easyButton);
        containerPanel.add(Box.createVerticalStrut(15));

        containerPanel.add(mediumButton);
        containerPanel.add(Box.createVerticalStrut(15));

        containerPanel.add(hardButton);


        this.add(containerPanel, gbc);
    }

}
