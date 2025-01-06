package views.game;

import controllers.game.GameController;
import models.game.GameModel;

import javax.swing.*;
import java.awt.*;

public class GameHeaderView extends JPanel {
    private final GameModel gameModel;

    private JLabel gameDateLabel;

    public GameHeaderView(GameController gameController, GameModel gameModel){
        this.gameModel = gameModel;

        this.setBackground(Color.GREEN);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        gameDateLabel = new JLabel("");
        gameDateLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(gameDateLabel,BorderLayout.CENTER);
    }

    public void updateGameDateLabel() {
        gameDateLabel.setText("Dzień epidemii: " + gameModel.getDayCounter());
    }
}
