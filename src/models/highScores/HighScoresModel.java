package models.highScores;



import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighScoresModel {
    private final String fileName = "highscores.txt";

    public List<String> loadHighScores() {
        List<String> highScores = new ArrayList<>();
        File file = new File(fileName);

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    highScores.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                highScores.add("Błąd podczas wczytywania wyników!");
            }
        } else {
            highScores.add("Brak zapisanych wyników.");
        }

        return highScores;
    }
}
