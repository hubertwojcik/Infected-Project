package components;

import javax.swing.*;
import java.awt.*;

public class GameResultLabel extends JLabel {
    public GameResultLabel(String text, int fontSize, boolean bold) {
        super(text);
        setFont(new Font("Arial", bold ? Font.BOLD : Font.PLAIN, fontSize));
        setForeground(Color.WHITE);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
