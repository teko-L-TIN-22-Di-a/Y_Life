package org.wolfnsheep.View;

import org.wolfnsheep.GameController;
import org.wolfnsheep.GameModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Zeigt den Game Over-Screen und ermöglicht einen Neustart.
 */
public class GameOverView extends JPanel implements ActionListener {
    private final JFrame frame;

    public GameOverView(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JLabel gameOverLabel = new JLabel("Game Over! Der Wolf ist verhungert.", JLabel.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 32));
        add(gameOverLabel, BorderLayout.CENTER);

        JButton restartButton = new JButton("Neustart");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 24));
        restartButton.addActionListener(this);
        add(restartButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Neustart des Spiels
        frame.getContentPane().removeAll(); // Entfernt den "Game Over"-Bildschirm
        GameModel newModel = new GameModel(frame);  // Neues Spielmodel initialisieren
        GameView newView = new GameView(newModel);     // Neue GameView initialisieren
        ScoreView newScoreView = new ScoreView(newModel); // Neue ScoreView initialisieren

        // Controller mit neuem Modell und neuer Ansicht neu starten
        GameController controller = new GameController(newModel, newView, newScoreView);
        controller.restartGame(newModel, newView, newScoreView);

        // Neue Ansicht und ScoreView im Frame anzeigen
        frame.getContentPane().add(newView, BorderLayout.CENTER);
        frame.getContentPane().add(newScoreView, BorderLayout.SOUTH);
        frame.revalidate(); // Aktualisiert den Frame
        frame.repaint();

        // Setze den Fokus auf das neue Spielfeld
        newView.requestFocusInWindow();
    }
}
