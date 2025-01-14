package views.game;

import javax.swing.*;
import java.awt.*;

public class GameEndView extends JPanel {

    public GameEndView() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        JLabel messageLabel = new JLabel("Koniec gry!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        messageLabel.setForeground(new Color(50, 50, 50));

        JButton backButton = new JButton("Powrót do menu");
        backButton.addActionListener(e -> onBackToMenu());

        this.add(messageLabel, BorderLayout.CENTER);
        this.add(backButton, BorderLayout.SOUTH);
    }

    private void onBackToMenu() {
        SwingUtilities.getWindowAncestor(this).dispose();
        new controllers.AppController();
    }
}