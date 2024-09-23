package org.wolfnsheep.entity;

import org.wolfnsheep.GameModel;
import org.wolfnsheep.View.GameOverView;

import javax.swing.*;

public class Wolf {
    private int x, y;
    private int energy = 10;
    private final GameModel game;
    private final JFrame frame;

    public static final int MAX_ENERGY = 100;
    public static final int ENERGY_LOSS_RATE = 1;
    public static final int ENERGY_GAIN_FROM_SHEEP = 5;

    /**
     * Enthält alle Logik die zum Wolf gehört
     * Die Bewegung, die Position, die Anzahl gefressener Schaffe und
     * @param startX
     * @param startY
     * @param game
     * @param frame
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
     * @param dx
     * @param dy
     */
    public void move(int dx, int dy) {
        int newX = Math.max(0, Math.min(GameModel.GRID_WIDTH - 1, x + dx));
        int newY = Math.max(0, Math.min(GameModel.GRID_HEIGHT - 1, y + dy));

        eatSheepWhenOnNewPosition(newX, newY);
        setNewPosition(newX, newY);
        decreaseEnergy();
    }

    private void setNewPosition(int newX, int newY) {
        game.setCell(x, y, 0);
        x = newX;
        y = newY;
        game.setCell(x, y, -1);
    }

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
     * Zeigt Game Over Screen
     */
    private void die() {
        System.out.println("Der Wolf ist verhungert! Spielende.");
        showGameOverScreen();
    }

    private void showGameOverScreen() {
        frame.getContentPane().removeAll();
        GameOverView gameOverView = new GameOverView(frame);
        frame.getContentPane().add(gameOverView);
        frame.revalidate();
        frame.repaint();
    }
}
