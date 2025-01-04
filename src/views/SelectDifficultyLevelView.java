package views;

import controllers.AppController;

import javax.swing.*;
import java.awt.*;

public class SelectDifficultyLevelView extends JPanel{

    public SelectDifficultyLevelView(AppController app){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton hardButton = new JButton("Hard - trudny");
        JButton mediumButton = new JButton("Medium - średni");
        JButton easyButton = new JButton("Easy - łatwy");

        hardButton.addActionListener(e -> {
            app.startNewGame();
        });
        mediumButton.addActionListener(e -> {
            app.startNewGame();
        });
        easyButton.addActionListener(e -> {
            app.startNewGame();
        });


        gbc.gridy = 0;
        this.add(hardButton, gbc);
        gbc.gridy = 1;
        this.add(mediumButton,gbc);
        gbc.gridy = 2;
        this.add(easyButton, gbc);
    }
}
