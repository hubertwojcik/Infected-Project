package views.game;

import controllers.game.GameEndController;
import enums.DifficultyLevel;
import game.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameEndView extends JPanel {
    private final GameEndController gameEndController;
    private JLabel messageLabel;
    private JTextField nameInput;
    private JButton saveButton;
    DifficultyLevel difficultyLevel  = GameSettings.getDifficultyLevel();
    Random r = new Random();

    public GameEndView(GameEndController gameEndController,int days, int dead, int recovered) {
        this.gameEndController = gameEndController;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);

        // Komunikat końcowy
        messageLabel = new JLabel("Gratulacje! Koniec gry!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        messageLabel.setForeground(new Color(50, 50, 50));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sekcja statystyk gry
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statystyki gry"));
        statsPanel.setMaximumSize(new Dimension(400, 120));
        statsPanel.setMinimumSize(new Dimension(400, 120));
        statsPanel.setPreferredSize(new Dimension(400, 120));

        JLabel daysLabel = new JLabel("Dni: " + days);
        JLabel deadLabel = new JLabel("Liczba martwych: " + dead);
        JLabel recoveredLabel = new JLabel("Liczba uratowanych: " + recovered);
        JLabel pointsMultiplierLabel = new JLabel("Mnożnik za poziom trudności: " + difficultyLevel.getScoreModifier() );
        JLabel pointsLabel = new JLabel("Punkty: " + (recovered * difficultyLevel.getScoreModifier()));


        for (JLabel label : new JLabel[]{daysLabel, deadLabel, recoveredLabel, pointsMultiplierLabel,pointsLabel}) {
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            statsPanel.add(label);
        }

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Zapisz swoje wyniki"));
        inputPanel.setMaximumSize(new Dimension(400, 100));
        inputPanel.setMinimumSize(new Dimension(400, 100));
        inputPanel.setPreferredSize(new Dimension(400, 100));

        JLabel nameLabel = new JLabel("Wpisz swoje imię:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameInput = new JTextField(20);
        nameInput.setMaximumSize(new Dimension(200, 30));
        nameInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(nameLabel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(nameInput);

        // Przycisk zapisz
        saveButton = new JButton("Zapisz");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 16));
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.addActionListener(e -> onSaveButtonClick());


        this.add(Box.createVerticalStrut(20));
        this.add(messageLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(statsPanel);
        this.add(Box.createVerticalStrut(20));
        this.add(inputPanel);
        this.add(Box.createVerticalStrut(20));
        this.add(saveButton);
        this.add(Box.createVerticalStrut(20));
    }


    private void onSaveButtonClick() {
        String name = nameInput.getText();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Proszę wprowadzić swoje imię.", "Błąd", JOptionPane.ERROR_MESSAGE);
        } else {
            gameEndController.onSaveResultClick(name,20 + r.nextInt());
            JOptionPane.showMessageDialog(this, "Dziękujemy, " + name + "! Twoje wyniki zostały zapisane.", "Informacja", JOptionPane.INFORMATION_MESSAGE);

        }
    }


}
