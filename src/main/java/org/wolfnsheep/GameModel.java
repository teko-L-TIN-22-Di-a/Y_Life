package org.wolfnsheep;

import org.wolfnsheep.entity.SheepManager;
import org.wolfnsheep.entity.Wolf;

import javax.swing.*;

/**
 * Das GameModel initialisiert und verwaltet das Spielfeld.
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

    /**
     * Initialisiert das Spielfeld mit Wolf und Schafen
     * @param frame
     */
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
     * Zustand des Wolfs
     */
    public Wolf getWolf() {
        return wolf;
    }

    /**
     * Aktueller Score
     * @return
     */
    public int getScore() {
        return sheepManager.getEatenSheepCount();
    }

    /**
     * Anzahl Schafe auf Spielfeld
     * @return
     */
    public int getCurrentSheepCount() {
        return SheepManager.getSheepCount();
    }

    /**
     * Updatet Game of Life Zyklus der Schafe
     */
    public void updateGameOfLife() {
        sheepManager.updateSheepPopulation();
    }

    public void reset() {
        SheepManager.resetCounter();
        initializeGame(new JFrame());
        }
    }