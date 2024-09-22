package org.wolfnsheep;

import org.wolfnsheep.entity.SheepManager;
import org.wolfnsheep.entity.Wolf;

import javax.swing.*;

/**
 * Das GameModel verwaltet den Zustand des Spiels, einschließlich des Gitters, den Wolf und die Schafe.
 */
public class GameModel {
    public static final int GRID_WIDTH = 20;
    public static final int GRID_HEIGHT = 15;

    private final int[][] grid = new int[GRID_WIDTH][GRID_HEIGHT];

    private Wolf wolf;
    private SheepManager sheepManager;

    public GameModel(JFrame frame) {
        initializeGame(frame);
        wolf = new Wolf(GRID_WIDTH / 2, GRID_HEIGHT / 2, this, frame);
    }

    private void initializeGame(JFrame frame) {
        wolf = new Wolf(GRID_WIDTH / 2, GRID_HEIGHT / 2, this, frame);
        sheepManager = new SheepManager(this);
        sheepManager.populateGridWithSheep();
    }

    public int getCell(int x, int y) {
        return grid[x][y];
    }

    public void setCell(int x, int y, int value) {
        grid[x][y] = value;
    }

    public void moveWolf(int dx, int dy) {
        wolf.move(dx, dy);
    }

    /**
     * holt den aktuellen zustand des Wolfs
     */
    public Wolf getWolf() {
        return wolf;
    }

    public int getScore() {
        return wolf.getEatenSheepCount();
    }

    public int getCurrentSheepCount() {
        return sheepManager.getSheepCount();
    }

    public void updateGameOfLife() {
        sheepManager.updateSheepPopulation();
    }
}