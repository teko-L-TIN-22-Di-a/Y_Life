package org.wolfnsheep.entity;

import org.wolfnsheep.GameModel;
import org.wolfnsheep.View.GameOverView;

import javax.swing.*;

public class Wolf {
    private int x, y;
    private int eatenSheepCount = 0;
    private int energy = 10; // Wolf startet mit voller Energie
    private final GameModel game;
    private int moveCooldown = 1; // Standardbewegungsgeschwindigkeit in Millisekunden
    private long lastMoveTime;
    private final JFrame frame; // Referenz auf das JFrame

    public static final int MAX_ENERGY = 100;
    public static final int ENERGY_LOSS_RATE = 1;
    public static final int ENERGY_GAIN_FROM_SHEEP = 5;

    public Wolf(int startX, int startY, GameModel game, JFrame frame) {
        this.x = startX;
        this.y = startY;
        this.game = game;
        this.frame = frame; // Rahmen für den Game Over Screen
        game.setCell(x, y, -1); // Set Wolf position in the grid
        lastMoveTime = System.currentTimeMillis();
    }

    public void move(int dx, int dy) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMoveTime < moveCooldown) {
            return; // Wenn der Wolf zu schnell bewegt wird, blockiere die Bewegung
        }

        int newX = Math.max(0, Math.min(GameModel.GRID_WIDTH - 1, x + dx));
        int newY = Math.max(0, Math.min(GameModel.GRID_HEIGHT - 1, y + dy));

        if (game.getCell(newX, newY) == SheepManager.SHEEP) {
            eatSheep();
        }

        game.setCell(x, y, 0); // Clear old position
        x = newX;
        y = newY;
        game.setCell(x, y, -1); // Set new position

        // Energie verringern und überprüfen, ob der Wolf stirbt
        decreaseEnergy();
        adjustSpeedBasedOnEnergy();
        lastMoveTime = currentTime;
    }

    private void eatSheep() {
        eatenSheepCount++;
        energy = Math.min(energy + ENERGY_GAIN_FROM_SHEEP, MAX_ENERGY); // Max 100 Energie
    }

    private void decreaseEnergy() {
        energy -= ENERGY_LOSS_RATE;
        if (energy <= 0) {
            die(); // Wolf stirbt, wenn Energie auf 0 sinkt
        }
    }

    private void adjustSpeedBasedOnEnergy() {
        if (energy > 5) {
            moveCooldown = 1; // Normale Geschwindigkeit
        } else if (energy > 4) {
            moveCooldown = 600; // Langsamer, da Energie niedrig ist
        } else {
            moveCooldown = 1000; // langsam bei sehr niedriger Energie
        }
    }

    public int getEnergy() {
        return energy;
    }

    public int getEatenSheepCount() {
        return eatenSheepCount;
    }

    private void die() {
        System.out.println("Der Wolf ist verhungert! Spielende.");
        showGameOverScreen();
    }

    private void showGameOverScreen() {
        // Verwende die neue GameOverView-Klasse, um den "Game Over"-Bildschirm anzuzeigen
        frame.getContentPane().removeAll(); // Entferne das aktuelle Spiel
        GameOverView gameOverView = new GameOverView(frame);
        frame.getContentPane().add(gameOverView); // Zeige den "Game Over"-Bildschirm
        frame.revalidate();
        frame.repaint();
    }
}
