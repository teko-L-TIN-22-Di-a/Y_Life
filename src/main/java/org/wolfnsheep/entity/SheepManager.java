package org.wolfnsheep.entity;

import org.wolfnsheep.GameModel;

import java.util.Random;

public class SheepManager {

    public static final int SHEEP = 1;
    private int sheepCount = 0;
    private final GameModel game;
    private final Random random = new Random();

    public SheepManager(GameModel game) {
        this.game = game;
    }

    public void populateGridWithSheep() {
        for (int i = 0; i < GameModel.GRID_WIDTH; i++) {
            for (int j = 0; j < GameModel.GRID_HEIGHT; j++) {
                if (random.nextDouble() < 0.2) {
                    game.setCell(i, j, SHEEP);
                    sheepCount++;
                }
            }
        }
    }

    public int getSheepCount() {
        return sheepCount;
    }

    public void updateSheepPopulation() {
        int[][] newGrid = new int[GameModel.GRID_WIDTH][GameModel.GRID_HEIGHT];

        for (int i = 0; i < GameModel.GRID_WIDTH; i++) {
            for (int j = 0; j < GameModel.GRID_HEIGHT; j++) {
                if (game.getCell(i, j) == -1) {
                    newGrid[i][j] = -1; // Wolf stays
                } else {
                    int neighbors = countNeighbors(i, j);
                    if (game.getCell(i, j) == SHEEP) {
                        if (neighbors < 2 || neighbors > 3) {
                            sheepCount--;
                        } else {
                            newGrid[i][j] = SHEEP;
                        }
                    } else if (neighbors == 3) {
                        newGrid[i][j] = SHEEP;
                        sheepCount++;
                    }
                }
            }
        }

        for (int i = 0; i < GameModel.GRID_WIDTH; i++) {
            for (int j = 0; j < GameModel.GRID_HEIGHT; j++) {
                game.setCell(i, j, newGrid[i][j]);
            }
        }
    }

    private int countNeighbors(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int nx = x + i;
                int ny = y + j;
                if (nx >= 0 && ny >= 0 && nx < GameModel.GRID_WIDTH && ny < GameModel.GRID_HEIGHT) {
                    if (game.getCell(nx, ny) == SHEEP) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
