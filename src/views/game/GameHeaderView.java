package views.game;

import components.StyledDetailLabel;
import controllers.game.GameController;
import enums.TransportType;
import game.GameSettings;
import models.country.Country;
import models.game.GameModel;
import models.game.GameObserver;

import javax.swing.*;
import java.awt.*;

public class GameHeaderView extends JPanel implements GameObserver {
    private final GameModel gameModel;

    private final StyledDetailLabel gameDateLabel;

    public GameHeaderView(GameModel gameModel) {
        this.gameModel = gameModel;

        this.setBackground(GameSettings.mainBackgroundGrey);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(0, 30));


        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // Transparentne tło

        gameDateLabel = new StyledDetailLabel("Dzień epidemii:", "", Color.WHITE, Color.WHITE);

        centerPanel.add(gameDateLabel);

        this.add(centerPanel, BorderLayout.CENTER);
    }



    @Override
    public void onDayUpdate(int dayCounter) {
        gameDateLabel.setValue("" + gameModel.getDayCounter());
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

    @Override
    public void onTransportStateUpdate(Country country,TransportType transportType, boolean isEnabled) {

    }
}
