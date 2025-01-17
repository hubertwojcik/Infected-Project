package enums;

import java.awt.*;

public enum CountryColor {
    RUSSIA(new Color(135, 206, 250)),
    CHINA(new Color(255, 239, 0)),
    INDIA(new Color(255, 165, 0)),
    KAZAKHSTAN(new Color(154, 205, 50)),
    MONGOLIA(new Color(255, 105, 180)),
    INDONESIA(new Color(0, 128, 0)),
    IRAN(new Color(138, 43, 226)),
    JAPAN(new Color(173, 216, 230)),
    VIETNAM(new Color(255, 127, 80)),
    PAKISTAN(new Color(255, 182, 193)),
    DEFAULT(Color.GRAY);

    private final Color color;

    CountryColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
