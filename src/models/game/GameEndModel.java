package models.game;

import util.Helpers;

import java.io.*;
import java.util.*;

public class GameEndModel {
    private final String fileName = "highscores.txt";

    public void saveScoreToFile(String userName, int score) throws IOException {
        Map<Integer, List<String>> scores = new TreeMap<>(Collections.reverseOrder());

        File file = new File(fileName);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(" - ");
                    if (parts.length == 2) {
                        String playerName = parts[0].replaceAll("^[0-9]+\\.\\s*", "").trim();
                        String scoreString = parts[1].replace(" pkt", "").trim();

                        if (Helpers.isStringNumberic(scoreString)) {
                            int playerScore = Integer.parseInt(scoreString);
                            scores.putIfAbsent(playerScore, new ArrayList<>());
                            scores.get(playerScore).add(playerName);
                        } else {
                            System.err.println("Niepoprawna linia: " + line);
                        }
                    } else {
                        System.err.println("Niepoprawny format linii: " + line);
                    }
                }
            }
        }

        scores.putIfAbsent(score, new ArrayList<>());
        scores.get(score).add(userName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            int rank = 1;
            for (Map.Entry<Integer, List<String>> entry : scores.entrySet()) {
                for (String player : entry.getValue()) {
                    writer.write(rank + ". " + player + " - " + entry.getKey() + " pkt");
                    writer.newLine();
                    rank++;
                }
            }
        }

    }



}
