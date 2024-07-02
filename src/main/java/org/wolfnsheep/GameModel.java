package org.wolfnsheep;

import java.util.Random;

/**
 * Das GameModel verwaltet den Zustand des Spiels, einschließlich des Gitters, den Wolf und die Schafe.
 */
public class GameModel {
    public static final int GRID_WIDTH = 40;
    public static final int GRID_HEIGHT = 30;

    //Konstanten, die den Zustand einer Zelle im Gitter repräsentieren
    public static final int SHEEP = 1;
    public static final int EMPTY = 0;

    private int[][] grid = new int[GRID_WIDTH][GRID_HEIGHT];
    private int wolfX = GRID_WIDTH / 2;
    private int wolfY = GRID_HEIGHT / 2;
    private int generatedSheep = 0;
    private int eatenSheep = 0;

    /**
     * Konstruktor für das GameModel, initialisiert das Gitter.
     */
    public GameModel() {
        initializeGrid();
    }

    /**
     * Initialisiert das Gitter mit zufälligen Schafen und setzt den Wolf in die Mitte.
     */
    public void initializeGrid() {
        Random random = new Random();
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                if (random.nextDouble() < 0.2) {
                    grid[i][j] = SHEEP;
                    generatedSheep++;
                }
            }
        }
        grid[wolfX][wolfY] = -1; // Wolf
    }

    /**
     * Gibt den Wert einer Zelle im Gitter zurück.
     *
     * @param x die x-Koordinate
     * @param y die y-Koordinate
     * @return der Wert der Zelle
     */
    public int getCell(int x, int y) {
        return grid[x][y];
    }

    /**
     * Setzt den Wert einer Zelle im Gitter.
     *
     * @param x     die x-Koordinate
     * @param y     die y-Koordinate
     * @param value der zu setzende Wert
     */
    public void setCell(int x, int y, int value) {
        grid[x][y] = value;
    }

    /**
     * Diese Methode bewegt den Wolf um dx und dy im Gitter.
     * Sie aktualisiert die Position des Wolfes und zählt gefressene Schafe, wenn der Wolf auf eine Schafzelle trifft.
     *
     * @param dx die Verschiebung in x-Richtung
     * @param dy die Verschiebung in y-Richtung
     */
    public void moveWolf(int dx, int dy) {
        int newX = Math.max(0, Math.min(GRID_WIDTH - 1, wolfX + dx));
        int newY = Math.max(0, Math.min(GRID_HEIGHT - 1, wolfY + dy));

        if (grid[newX][newY] == SHEEP) {
            eatenSheep++;
            generatedSheep--;
        }
        grid[wolfX][wolfY] = EMPTY;
        wolfX = newX;
        wolfY = newY;
        grid[wolfX][wolfY] = -1;
    }

    /**
     * Gibt die Anzahl der gefressenen Schafe zurück.
     *
     * @return die Anzahl der gefressenen Schafe
     */
    public int getScore() {
        return eatenSheep;
    }

    /**
     * Gibt die aktuelle Anzahl der Schafe im Gitter zurück.
     *
     * @return die aktuelle Anzahl der Schafe
     */
    public int getCurrentSheepCount() {
        int currentSheepCount = 0;
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                if (grid[i][j] == SHEEP) {
                    currentSheepCount++;
                }
            }
        }
        return currentSheepCount;
    }

    /**
     * Aktualisiert das Gitter basierend auf den Regeln des Game of Life.
     */
    public void updateGameOfLife() {
        int[][] newGrid = new int[GRID_WIDTH][GRID_HEIGHT];

        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                if (grid[i][j] == -1) {
                    newGrid[i][j] = -1; // Wolf
                    continue;
                }

                int neighbors = countNeighbors(i, j);
                if (grid[i][j] == SHEEP) {
                    if (neighbors < 2 || neighbors > 3) {
                        newGrid[i][j] = EMPTY;
                    } else {
                        newGrid[i][j] = SHEEP;
                    }
                } else if (grid[i][j] == EMPTY && neighbors == 3) {
                    newGrid[i][j] = SHEEP;
                    generatedSheep++;
                }
            }
        }
        grid = newGrid;
    }

    /**
     * Zählt die Anzahl der Nachbarn einer Zelle.
     *
     * @param x die x-Koordinate
     * @param y die y-Koordinate
     * @return die Anzahl der Nachbarn
     */
    public int countNeighbors(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int nx = x + i;
                int ny = y + j;
                if (nx >= 0 && ny >= 0 && nx < GRID_WIDTH && ny < GRID_HEIGHT) {
                    if (grid[nx][ny] == SHEEP) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
