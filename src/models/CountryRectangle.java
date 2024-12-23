package models;

import java.awt.*;

public class CountryRectangle {
    private final String name;
    private final Rectangle bounds; // Prostokąt reprezentujący kraj
    private boolean selected; // Czy kraj został kliknięty

    public CountryRectangle(String name, int x, int y, int width, int height) {
        this.name = name;
        this.bounds = new Rectangle(x, y, width, height);
        this.selected = false;
    }

    public String getName() {
        return name;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // Sprawdzenie, czy kliknięcie znajduje się w obrębie prostokąta
    public boolean contains(Point point) {
        return bounds.contains(point);
    }
}
