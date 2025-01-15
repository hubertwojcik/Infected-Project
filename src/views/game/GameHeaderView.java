package views.game;

import controllers.game.GameController;
import models.game.GameModel;
import models.game.GameObserver;

import javax.swing.*;
import java.awt.*;

public class GameHeaderView extends JPanel implements GameObserver {
    private final GameModel gameModel;

    private JLabel gameDateLabel;

    public GameHeaderView(GameController gameController, GameModel gameModel){
        this.gameModel = gameModel;

        this.setBackground(Color.RED);
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setPreferredSize(new Dimension(0, 30));

        gameDateLabel = new JLabel("");
        gameDateLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(gameDateLabel,BorderLayout.CENTER);
    }

    public void updateGameDateLabel() {

    }

    @Override
    public void onDayUpdate(int dayCounter) {
        gameDateLabel.setText("Dzień epidemii: " + gameModel.getDayCounter());
    }

    @Override
    public void onSelectedCountryUpdate(String countryName, double points,int population, int suspectible,int infected, int cured, int dead,double infectedRate,double recoveryRestinatce, double moratyliRate) {

    }

    @Override
    public void onGlobalStatsUpdate(int infected, int cured, int dead) {

    }

    @Override
    public void onGameEnd() {

    }
}
