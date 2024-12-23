package views;

import controllers.AppController;
import models.AppModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import models.CountryRectangle;

public class GameView extends JPanel {
    private final AppModel appModel;
    private final AppController appController;

    private final List<CountryRectangle> countries;
    private final UpgradeCountryView upgradeCountryView;

    public GameView(AppModel appModel,AppController appController) {
        this.appModel = appModel;
        this.appController = appController;

        this.setLayout(new BorderLayout());

        // Przycisk pauzy
        JButton pauseButton = new JButton("Pause");

        pauseButton.addActionListener(e -> {
            appController.pauseGame();
//            appController.setGameState(GameState.PAUSED);
        });

        // Dodanie przycisku do panelu
        this.add(pauseButton, BorderLayout.NORTH);

        JPanel sidebar = new JPanel(new BorderLayout());

        // Panel ulepszeń
        upgradeCountryView = new UpgradeCountryView();
        this.add(upgradeCountryView, BorderLayout.EAST);


        // Inicjalizacja krajów
        countries = initializeCountries();
        JPanel mapArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                // Rysowanie krajów
                for (CountryRectangle country : countries) {
                    g2.setColor(country.isSelected() ? Color.RED : Color.GREEN);
                    Rectangle bounds = country.getBounds();
                    g2.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
                    g2.setColor(Color.BLACK);
                    g2.drawString(country.getName(), bounds.x + 5, bounds.y + 15);
                }
            }
        };
        mapArea.setBackground(Color.WHITE);
        this.add(mapArea, BorderLayout.CENTER);
        // Obsługa kliknięć w kraje
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                for (CountryRectangle country : countries) {
                    if (country.contains(e.getPoint())) {
                        System.out.println("Clicked on: " + country.getName());
                        country.setSelected(!country.isSelected());
//                        repaint(); // Ponowne rysowanie po kliknięciu
                    }
                }
            }
        });
    }

    private List<CountryRectangle> initializeCountries() {
        List<CountryRectangle> list = new ArrayList<>();
        list.add(new CountryRectangle("Russia", 50, 50, 200, 100));
        list.add(new CountryRectangle("China", 300, 150, 150, 100));
        list.add(new CountryRectangle("India", 300, 300, 100, 100));
        list.add(new CountryRectangle("Japan", 500, 200, 100, 50));
        return list;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Wyczyszczenie panelu przed rysowaniem
        Graphics2D g2 = (Graphics2D) g;

        // Rysowanie krajów
        for (CountryRectangle country : countries) {
            g2.setColor(country.isSelected() ? Color.RED : Color.GREEN); // Zmiana koloru, jeśli kraj został kliknięty
            Rectangle bounds = country.getBounds();
            g2.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

            // Dodanie nazwy kraju
            g2.setColor(Color.BLACK);
            g2.drawString(country.getName(), bounds.x + 5, bounds.y + 15);
        }
    }
}
