import enums.GameState;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel{
    public GameScreen(GamePanel app) {
        this.setLayout(new BorderLayout());

        // Przycisk pauzy
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> {
            app.pauseGame();
            app.setGameState(GameState.PAUSED);
        });

        // Dodanie przycisku do panelu
        this.add(pauseButton, BorderLayout.NORTH);

        // Przykładowy obszar gry
        JPanel gameArea = new JPanel();
        gameArea.setBackground(Color.GREEN);
        this.add(gameArea, BorderLayout.CENTER);
    }
}
