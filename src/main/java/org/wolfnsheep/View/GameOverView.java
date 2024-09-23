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
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(gameOverLabel, BorderLayout.CENTER);

        JButton restartButton = new JButton("Neustart");
        restartButton.setFont(new Font("Arial", Font.PLAIN, 24));
        restartButton.addActionListener(this);
        add(restartButton, BorderLayout.SOUTH);
    }

    /**
     * Neustart des Spiels
     * @param e the event to be processed
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
