package app;

import controllers.AppController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->new AppController());
    }
}
