package org.wolfnsheep.entity;

import org.wolfnsheep.GameModel;
import org.wolfnsheep.View.GameOverView;

import javax.swing.*;

/**
 * Enthält alle Logik die zum Wolf gehört
 * Die Bewegung, die Position, die Anzahl gefressener Schaffe und
 */
public class Wolf {
    private int x, y;
    private int energy = 10;
    private final GameModel game;
    private final JFrame frame;

    public static final int MAX_ENERGY = 100;
    public static final int ENERGY_LOSS_RATE = 1;
    public static final int ENERGY_GAIN_FROM_SHEEP = 5;

    /**
     * @param startX AnfangsX-Koordinate des Wolfs
     * @param startY AnfangsY-Koordinate des Wolfs
     * @param game Aktuelles Game model
     * @param frame Aktueller Frame
     */

    public Wolf(int startX, int startY, GameModel game, JFrame frame) {
        this.x = startX;
        this.y = startY;
        this.game = game;
        this.frame = frame; // Rahmen für den Game Over Screen
        game.setCell(x, y, -1); // Set Wolf position in the grid
    }

    /**
     * Steuerung des Wolfs
     * @param dx aktuelle x-Position
     * @param dy aktuelle y-Position
     */
    public void move(int dx, int dy) {
        int newX = Math.max(0, Math.min(GameModel.GRID_WIDTH - 1, x + dx));
        int newY = Math.max(0, Math.min(GameModel.GRID_HEIGHT - 1, y + dy));

        eatSheepWhenOnNewPosition(newX, newY);
        setNewPosition(newX, newY);
        decreaseEnergy();
    }

    /**
     * Setzt die neue Position
     * @param newX neue x-Position
     * @param newY neue y-Position
     */
    private void setNewPosition(int newX, int newY) {
        game.setCell(x, y, 0);
        x = newX;
        y = newY;
        game.setCell(x, y, -1);
    }

    /**
     * Schaf wird gefressen wenn es sich auf neuer Position befindet
     * @param newX neue x-Position
     * @param newY neue y-Position
     */
    private void eatSheepWhenOnNewPosition(int newX, int newY) {
        if (game.getCell(newX, newY) == SheepManager.SHEEP) {
            eatSheep();
        }
    }

    /**
     * Schaf wurde gefressen
     * Anzahl gefressener Schafe erhöht sich
     * Energie wird erhöht
     */
    private void eatSheep() {
        SheepManager.setEatenSheepCount();
        energy = Math.min(energy + ENERGY_GAIN_FROM_SHEEP, MAX_ENERGY);
    }

    /**
     * Verringert die Energie
     * Wolf stirbt, wenn Energie auf 0 sinkt
     */
    private void decreaseEnergy() {
        energy -= ENERGY_LOSS_RATE;
        if (energy <= 0) {
            die();
        }
    }

    public int getEnergy() {
        return energy;
    }

    /**
     * Wolf stirbt
     */
    private void die() {
        System.out.println("Der Wolf ist verhungert! Spielende.");
        showGameOverScreen();
    }

    /**
     * Zeigt Game Over Screen
     */
    private void showGameOverScreen() {
        frame.getContentPane().removeAll();
        GameOverView gameOverView = new GameOverView(frame);
        frame.getContentPane().add(gameOverView);
        frame.revalidate();
        frame.repaint();
    }
}
