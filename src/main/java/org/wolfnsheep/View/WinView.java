package org.wolfnsheep.View;

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
        winLabel.setFont(new Font("Arial", Font.BOLD, 32));
        add(winLabel, BorderLayout.CENTER);

        JLabel scoreLabel = new JLabel("Dein Score: " + score, JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        add(scoreLabel, BorderLayout.NORTH);

        JLabel highscoreLabel = new JLabel("Highscore: " + highscore, JLabel.CENTER);
        highscoreLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        add(highscoreLabel, BorderLayout.SOUTH);

        JButton restartButton = new JButton("Neustart");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 24));
        restartButton.addActionListener(this);
        add(restartButton, BorderLayout.SOUTH);

        checkAndSaveHighscore();
    }

    private int loadHighscore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            return 0;  // Kein Highscore vorhanden
        }
    }

    private void checkAndSaveHighscore() {
        if (score > highscore) {
            highscore = score;
            saveHighscore();
        }
    }

    private void saveHighscore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"))) {
            writer.write(String.valueOf(highscore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Neustart des Spiels
        frame.getContentPane().removeAll();
        GameModel newModel = new GameModel(frame);  // Neues Spielmodel initialisieren
        GameView newView = new GameView(newModel);     // Neue GameView initialisieren
        ScoreView newScoreView = new ScoreView(newModel); // Neue ScoreView initialisieren
        //GameController controller = new GameController(newModel, newView, newScoreView); // Neuen GameController starten

        frame.getContentPane().add(newView, BorderLayout.CENTER);
        frame.getContentPane().add(newScoreView, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();

        newView.requestFocusInWindow();  // Fokus auf das neue Spielfeld setzen
    }
}