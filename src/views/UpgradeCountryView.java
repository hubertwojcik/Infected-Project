package views;

import models.CountryRectangle;

import javax.swing.*;
import java.awt.*;

public class UpgradeCountryView extends JPanel {
    private final JLabel countryNameLabel;
    private final DefaultListModel<String> upgradesModel;

    public UpgradeCountryView() {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(200, 0)); // Panel boczny

        // Tytuł kraju
        countryNameLabel = new JLabel("Select a country");
        countryNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(countryNameLabel, BorderLayout.NORTH);

        // Lista ulepszeń
        upgradesModel = new DefaultListModel<>();
        JList<String> upgradesList = new JList<>(upgradesModel);
        this.add(new JScrollPane(upgradesList), BorderLayout.CENTER);
    }

    public void updatePanel(CountryRectangle country) {
        // Aktualizacja nazwy kraju
        countryNameLabel.setText("Upgrades for: " + country.getName());

        // Lista ulepszeń
        upgradesModel.clear();
        for(int i = 0 ;i<5;i++){
            upgradesModel.addElement(i + " (Cost: " + i+ ")");

        }

    }
}
