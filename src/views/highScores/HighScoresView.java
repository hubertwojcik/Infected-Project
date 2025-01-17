package views.highScores;

import components.StyledButton;
import controllers.AppController;
import util.GameSettings;
import models.highScores.HighScoresModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HighScoresView extends JPanel {

    public HighScoresView(AppController app, HighScoresModel highScoresModel) {
        this.setLayout(new GridBagLayout());
        this.setBackground(GameSettings.mainBackgroundGrey);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;


        JLabel label = new JLabel("Najlepsze wyniki:");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        gbc.gridy = 0;
        this.add(label, gbc);

        List<String> highScores = highScoresModel.loadHighScores();


        JList<String> scoresList = new JList<>(highScores.toArray(new String[0]));
        scoresList.setFont(new Font("Arial", Font.PLAIN, 14));
        scoresList.setBackground(GameSettings.mainBackgroundGrey);
        scoresList.setForeground(Color.WHITE);
        scoresList.setFixedCellHeight(25);


        JScrollPane scrollPane = new JScrollPane(scoresList);
        scrollPane.setPreferredSize(new Dimension(350, 200));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(GameSettings.mainBackgroundGrey);

        gbc.gridy = 1;
        this.add(scrollPane, gbc);


        StyledButton backButton = new StyledButton("Wróc do menu głównego",Color.pink,Color.white);
        backButton.addActionListener(e -> app.showHomeView());

        gbc.gridy = 2;
        this.add(backButton, gbc);
    }
}
