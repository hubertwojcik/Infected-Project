package views;

import controllers.AppController;
import enums.GameState;
import models.GameModel;

import javax.swing.*;
import java.awt.*;

public class GameHeaderView extends JPanel {
    private final AppController appController;
    private final GameModel gameModel;
    private JButton pauseButton;
    private JLabel gameDateLabel;

    public GameHeaderView(AppController appController, GameModel gameModel){
        this.appController = appController;
        this.gameModel = gameModel;

        this.setBackground(Color.GREEN);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        pauseButton = new JButton();
        pauseButton.setText("Pause");
        pauseButton.addActionListener(e->{
            appController.pauseGame();
            appController.setGameState(GameState.PAUSED);
        });
        this.add(pauseButton, BorderLayout.WEST);

        gameDateLabel = new JLabel("Date: " + gameModel.getGameDate());
        gameDateLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(gameDateLabel,BorderLayout.CENTER);
    }

    public void updateGameDateLabel() {
        gameDateLabel.setText("Date: " + gameModel.getGameDate());
    }
}
