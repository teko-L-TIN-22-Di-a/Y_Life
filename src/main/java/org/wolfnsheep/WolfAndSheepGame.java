package org.wolfnsheep;

import org.wolfnsheep.View.GameView;
import org.wolfnsheep.View.ScoreView;

import javax.swing.*;
import java.awt.*;

/**
 * Die Hauptklasse des Spiels, die das Spiel startet.
 */
public class WolfAndSheepGame {

    public static void main(String[] args) {
        GameModel model = new GameModel();
        GameView view = new GameView(model);
        ScoreView scoreView = new ScoreView(model);
        GameController controller = new GameController(model, view, scoreView);

        JFrame frame = new JFrame("Wolf and Sheep");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(view, BorderLayout.CENTER);
        frame.getContentPane().add(scoreView, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
}