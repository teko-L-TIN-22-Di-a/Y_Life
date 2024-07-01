package org.wolfnsheep;

import org.wolfnsheep.View.GameView;
import org.wolfnsheep.View.ScoreView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Der GameController behandelt Benutzereingaben und Timer-Ereignisse, um das Spiel zu steuern.
 */
public class GameController implements KeyListener, ActionListener {
    private GameModel model;
    private GameView view;
    private ScoreView scoreView;
    private Timer timer;


    /**
     * Konstruktor für den GameController.
     * Initialisiert das Modell, die Ansichten und den Timer.
     *
     * @param model Das GameModel, für die Steuerung.
     * @param view Die GameView, welche aktualisiert wird.
     * @param scoreView Die ScoreView, welche auch aktualisiert wird.
     */
    public GameController(GameModel model, GameView view, ScoreView scoreView) {
        this.model = model;
        this.view = view;
        this.scoreView = scoreView;
        this.view.addKeyListener(this);
        this.view.setFocusable(true);
        this.timer = new Timer(1000, this);
        this.timer.start();
    }

    /**
     * Momentan keine Funktion
     * @param e der zu verarbeitende Tastatur Event
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Steuerung des Wolfs mit dem Keypad
     * @param e der zu verarbeitende Tastatur Event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            model.moveWolf(-1, 0);
        } else if (key == KeyEvent.VK_RIGHT) {
            model.moveWolf(1, 0);
        } else if (key == KeyEvent.VK_UP) {
            model.moveWolf(0, -1);
        } else if (key == KeyEvent.VK_DOWN) {
            model.moveWolf(0, 1);
        }
        view.repaint();
        scoreView.repaint();
    }

    /**
     * Momentan keine Funktion
     * @param e der zu verarbeitende Tastatur Event
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Darstellung der Schaffe, welches laufend geupdatet wird sobald der Timer abläuft
     * @param e der zu verarbeitende Tastatur Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        model.updateGameOfLife();
        view.repaint();
        scoreView.repaint();
    }
}
