package components;

import util.GameSettings;
import interfaces.Upgrade;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class UpgradeInformations extends JPanel {
    public UpgradeInformations(Upgrade upgrade, boolean isBought, Runnable onPurchase) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(GameSettings.mainBackgroundGrey);
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));


        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        namePanel.setBackground(GameSettings.mainBackgroundGrey);

        JLabel upgradeLabel = new JLabel(upgrade.getName());
        upgradeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        upgradeLabel.setForeground(Color.WHITE);
        namePanel.add(upgradeLabel);


        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(GameSettings.mainBackgroundGrey);


        StyledDetailLabel costLabel = new StyledDetailLabel(
                "Koszt",
                upgrade.getCost() + " pkt",
                Color.WHITE,
                Color.WHITE
        );
        detailsPanel.add(costLabel);


        for (Map.Entry<String, Double> effect : upgrade.getEffects().entrySet()) {
            StyledDetailLabel effectLabel = new StyledDetailLabel(
                    effect.getKey(),
                    String.valueOf(effect.getValue()),
                    Color.WHITE,
                    Color.WHITE
            );
            detailsPanel.add(effectLabel);
        }


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(GameSettings.mainBackgroundGrey);

        JButton buyButton = new JButton(isBought ? "Aktywne" : "Kup");
        buyButton.setEnabled(!isBought);
        buyButton.addActionListener(e -> onPurchase.run());

        buttonPanel.add(buyButton);


        this.add(namePanel);
        this.add(Box.createVerticalStrut(10));
        this.add(detailsPanel);
        this.add(Box.createVerticalStrut(10));
        this.add(buttonPanel);
    }
}
