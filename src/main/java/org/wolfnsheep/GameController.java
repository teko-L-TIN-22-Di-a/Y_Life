package org.wolfnsheep;

import org.wolfnsheep.View.GameView;
import org.wolfnsheep.View.ScoreView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Der GameController handelt Benutzereingaben und Timer-Ereignisse, um das Spiel zu steuern.
 */
public class GameController implements KeyListener, ActionListener {
    private GameModel model;
    private GameView view;
    private ScoreView scoreView;
    private Timer timer;

    /**
     * Initialisiert das Modell, die Ansichten und den Timer.
     *
     * @param model GameModel, für die Steuerung.
     * @param view GameView, für die aktualisierte Anzeige.
     * @param scoreView ScoreView, für die aktualisierte Anzeige.
     */
    public GameController(GameModel model, GameView view, ScoreView scoreView) {
        this.model = model;
        this.view = view;
        this.scoreView = scoreView;
        setupGame();
    }

    /**
     * Setzt Spiel durch Initialisierung von KeyListener und Timer auf.
     */
    private void setupGame() {
        this.view.addKeyListener(this);
        this.view.setFocusable(true);
        this.view.requestFocusInWindow();

        // Timer initialisieren (1000 ms = 1 Sekunde)
        this.timer = new Timer(1000, this);
        this.timer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT -> model.moveWolf(-1, 0);
            case KeyEvent.VK_RIGHT -> model.moveWolf(1, 0);
            case KeyEvent.VK_UP -> model.moveWolf(0, -1);
            case KeyEvent.VK_DOWN -> model.moveWolf(0, 1);
        }
        view.repaint();
        scoreView.repaint();
    }

    /**
     * Darstellung des atuellen Spielzustands
     * @param e Timer-Ereignis
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        model.updateGameOfLife();
        updateViews();
    }

    private void updateViews() {
        view.repaint();
        scoreView.repaint();
    }

    /**
     * Startet das Spiel neu und stellt sicher, dass der Timer und der KeyListener korrekt funktionieren.
     */
    public void restartGame(GameModel newModel, GameView newView, ScoreView newScoreView) {
        // Stoppe den alten Timer
        if (timer != null) {
            timer.stop();
        }

        // Aktualisiere das Modell, die Ansicht und die ScoreView
        this.model = newModel;
        this.view = newView;
        this.scoreView = newScoreView;

        // Setup Game erneut aufrufen, um den KeyListener und Timer neu zu registrieren
        setupGame();
    }

    // Unused methods can remain empty
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}