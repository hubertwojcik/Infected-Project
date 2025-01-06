package views.general;

import controllers.AppController;

import javax.swing.*;
import java.awt.*;

public class HighScoresView extends JPanel{

    public HighScoresView(AppController app){
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel("High scores!:");

        this.add(label);


        JButton backButton = new JButton("Back - wróć");


        backButton.addActionListener(e -> {
            app.showHomeView();
        });

        this.add(backButton);
    }
}
