package views.game;

import javax.swing.*;
import java.awt.*;

public class GameEndView extends JPanel {

    private JLabel messageLabel;
    private JTextField nameInput;
    private JButton saveButton;
    private JLabel statsLabel;

    public GameEndView() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        // Komunikat końcowy
        messageLabel = new JLabel("Gratulacje! Epidemia została pokonana", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        messageLabel.setForeground(new Color(50, 50, 50));

        // Pole do wprowadzenia imienia
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(Color.WHITE);
        JLabel nameLabel = new JLabel("Wpisz swoje imię:");
        nameInput = new JTextField(20);
        inputPanel.add(nameLabel);
        inputPanel.add(nameInput);

        // Statystyki gry
        statsLabel = new JLabel("Statystyki gry: ", SwingConstants.CENTER);
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statsLabel.setForeground(new Color(70, 70, 70));

        // Przycisk zapisz
        saveButton = new JButton("Zapisz");
        saveButton.addActionListener(e -> {
            System.out.println("CLICK!");
        });

        // Dodawanie komponentów do panelu
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(messageLabel, BorderLayout.NORTH);
        centerPanel.add(inputPanel, BorderLayout.CENTER);
        centerPanel.add(statsLabel, BorderLayout.SOUTH);

        this.add(centerPanel, BorderLayout.CENTER);
        this.add(saveButton, BorderLayout.SOUTH);
    }

    private void onBackToMenu() {
        SwingUtilities.getWindowAncestor(this).dispose();
        new controllers.AppController();
    }
}