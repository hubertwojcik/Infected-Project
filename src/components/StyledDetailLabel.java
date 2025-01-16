package components;

import game.GameSettings;

import javax.swing.*;
import java.awt.*;

public class StyledDetailLabel extends JPanel {
    private final JLabel valueLabel;
    public StyledDetailLabel(String labelText, String valueText,Color labelColor, Color valueColor){
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        this.setBackground(GameSettings.mainBackgroundGrey);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(labelColor);


        valueLabel = new JLabel(valueText);
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        valueLabel.setForeground(valueColor);

        this.add(label);
        this.add(valueLabel);
    }

    public void setValue(String value){
        valueLabel.setText(value);
    }
}
