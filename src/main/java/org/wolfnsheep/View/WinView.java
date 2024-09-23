package org.wolfnsheep.View;

import org.wolfnsheep.GameController;
import org.wolfnsheep.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class WinView extends JPanel implements ActionListener {
    private final JFrame frame;
    private final int score;
    private int highscore;

    public WinView(JFrame frame, int score) {
        this.frame = frame;
        this.score = score;
        this.highscore = loadHighscore();

        setLayout(new BorderLayout());

        JLabel winLabel = new JLabel("Du hast gewonnen!", JLabel.CENTER);
        winLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(winLabel, BorderLayout.CENTER);

        JLabel scoreLabel = new JLabel("Dein Score: " + score, JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(scoreLabel, BorderLayout.NORTH);

        JLabel highscoreLabel = new JLabel("Highscore: " + highscore, JLabel.CENTER);
        highscoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(highscoreLabel, BorderLayout.SOUTH);

        JButton restartButton = new JButton("Neustart");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 24));
        restartButton.addActionListener(this);
        add(restartButton, BorderLayout.SOUTH);

        checkAndSaveHighscore();
    }

    /**
     * Lädt den Highscore aus einer Datei, wenn sie existiert
     * Falls die Datei nicht existiert, erstellen wir sie mit einem Standardwert von 0
     */
    private int loadHighscore() {
        File file = new File("highscore.txt");
        if (!file.exists()) {
            saveHighscore(0);
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Prüft, ob der aktuelle Score der höchste ist, und speichert ihn, falls ja
     */
    private void checkAndSaveHighscore() {
        if (score > highscore) {
            highscore = score;
            saveHighscore(highscore);
        }
    }

    /**
     * Speichert den neuen Highscore in eine Datei
     */
    private void saveHighscore(int newHighscore) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"))) {
            writer.write(String.valueOf(newHighscore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Neustart des Spiels
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.getContentPane().removeAll();

        GameModel newModel = new GameModel(frame);
        newModel.reset();

        GameView newView = new GameView(newModel);
        ScoreView newScoreView = new ScoreView(newModel);
        GameController controller = new GameController(newModel, newView, newScoreView);
        controller.restartGame(newModel, newView, newScoreView);

        frame.getContentPane().add(newView, BorderLayout.CENTER);
        frame.getContentPane().add(newScoreView, BorderLayout.SOUTH);

        frame.revalidate();
        frame.repaint();

        newView.requestFocusInWindow();
    }
}