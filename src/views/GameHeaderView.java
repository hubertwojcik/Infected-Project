package views;

import controllers.AppController;
import controllers.GameController;
import enums.GameState;
import models.GameModel;

import javax.swing.*;
import java.awt.*;

public class GameHeaderView extends JPanel {
    private final GameController gameController;
    private final GameModel gameModel;
    private JButton pauseButton;
    private JLabel gameDateLabel;

    public GameHeaderView(GameController gameController, GameModel gameModel){
        this.gameController = gameController;
        this.gameModel = gameModel;

        this.setBackground(Color.GREEN);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        pauseButton = new JButton();
        pauseButton.setText("Pause");
        pauseButton.addActionListener(e->{
          gameController.pauseGame();
        });
        this.add(pauseButton, BorderLayout.WEST);

        gameDateLabel = new JLabel("Date: " + gameModel.getDayCounter());
        gameDateLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(gameDateLabel,BorderLayout.CENTER);
    }

    public void updateGameDateLabel() {
        gameDateLabel.setText("Dzień epidemii: " + gameModel.getDayCounter());
    }
}
