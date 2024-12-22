import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("My super game!");
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setLayout(new CardLayout());

    }
}
