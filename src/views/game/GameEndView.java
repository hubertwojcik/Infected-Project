package views.game;

import components.GameResultLabel;
import components.StyledButton;
import controllers.gameEnd.GameEndController;
import enums.DifficultyLevel;
import util.GameSettings;

import javax.swing.*;
import java.awt.*;

public class GameEndView extends JPanel {
    private final JTextField nameInput;


    DifficultyLevel difficultyLevel = GameSettings.getDifficultyLevel();

    public GameEndView(GameEndController gameEndController, int days, int dead, int recovered) {
        this.setLayout(new GridBagLayout());
        this.setBackground(GameSettings.mainBackgroundGrey);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel messageLabel = new GameResultLabel("Gratulacje! Koniec gry!", 24, true);

        gbc.gridy = 0;
        this.add(messageLabel, gbc);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBackground(GameSettings.mainBackgroundGrey);
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        statsPanel.setMaximumSize(new Dimension(400, 150));

        statsPanel.add(new GameResultLabel("Dni: " + days, 16, false));
        statsPanel.add(new GameResultLabel("Liczba martwych: " + dead, 16, false));
        statsPanel.add(new GameResultLabel("Liczba uratowanych: " + recovered, 16, false));
        statsPanel.add(new GameResultLabel("Mnożnik za poziom trudności: " + difficultyLevel.getScoreModifier(), 16, false));
        statsPanel.add(new GameResultLabel("Punkty: " + Math.round((recovered / 1_000_000 * difficultyLevel.getScoreModifier())), 16, false));

        gbc.gridy = 1;
        this.add(statsPanel, gbc);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(GameSettings.mainBackgroundGrey);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setMaximumSize(new Dimension(400, 100));

        inputPanel.add(new GameResultLabel("Wpisz swoje imię:", 16, false));
        inputPanel.add(Box.createVerticalStrut(10));

        nameInput = new JTextField(20);
        nameInput.setMaximumSize(new Dimension(350, 60));
        nameInput.setBackground(new Color(30, 30, 30));
        nameInput.setForeground(Color.WHITE);
        nameInput.setCaretColor(Color.WHITE);
        nameInput.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        nameInput.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputPanel.add(nameInput);

        gbc.gridy = 2;
        this.add(inputPanel, gbc);

        JButton saveButton = new StyledButton("Zapisz", Color.GREEN, Color.WHITE);
        saveButton.addActionListener(e -> {
            String userName =nameInput.getText();
            if(userName.isEmpty()){
                JOptionPane.showMessageDialog(this, "Proszę wprowadzić swoje imię.", "Błąd", JOptionPane.ERROR_MESSAGE);

            }else{
                gameEndController.onSaveResultClick(userName, ((int) Math.round(((double) (recovered) / 1_000_000 * difficultyLevel.getScoreModifier()))));
                JOptionPane.showMessageDialog(this, "Dziękujemy, " + userName + "! Twoje wyniki zostały zapisane.", "Informacja", JOptionPane.INFORMATION_MESSAGE);

            }
                    });

        gbc.gridy = 3;
        this.add(saveButton, gbc);
    }




}
