package util;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ViewsManager {
    private final JFrame frame;
    private final Map<String,JPanel> screens = new HashMap<>();

    public ViewsManager(JFrame frame) {
        this.frame = frame;
        this.frame.setLayout(new CardLayout());
    }

    public void addScreen(String name, JPanel screen) {
        screens.put(name, screen);
        frame.add(screen, name);
    }

    public void showScreen(String name) {
        CardLayout layout = (CardLayout) frame.getContentPane().getLayout();
        layout.show(frame.getContentPane(), name);
    }

    public JPanel getScreen(String name) {
        return screens.get(name);
    }

    public void removeScreen(String name) {
        JPanel screen = screens.remove(name);
        if (screen != null) {
            frame.remove(screen);
        }
    }

}
