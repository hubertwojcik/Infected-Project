package components;

import javax.swing.*;
import java.awt.*;

public class StyledButton extends JButton {
    public StyledButton(String text, Color backgroundColor, Color textColor){

        this.setText(text);
        this.setFont(new Font("Arial", Font.BOLD, 18));
        this.setPreferredSize(new Dimension(350, 50));
        this.setMinimumSize(new Dimension(350, 50));
        this.setMaximumSize(new Dimension(350, 50));
        this.setBackground(backgroundColor);
        this.setForeground(textColor);
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }
}
