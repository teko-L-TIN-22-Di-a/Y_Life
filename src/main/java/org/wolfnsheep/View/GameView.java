package org.wolfnsheep.View;

import org.wolfnsheep.GameModel;
import org.wolfnsheep.entity.SheepManager;

import javax.swing.*;
import java.awt.*;

/**
 * Das GameView stellt die grafische Darstellung des Spiels bereit.
 */
public class GameView extends JPanel {
    private final GameModel model;

    /**
     * Setzt Modell und die Grösse der Ansicht festlegt.
     *
     * @param model das Spielfeld
     */
    public GameView(GameModel model) {
        this.model = model;
        setPreferredSize(new Dimension(GameModel.GRID_WIDTH * 20, GameModel.GRID_HEIGHT * 20));
    }

    /**
     * Anzeige des Spielfelds
     * @param g das Graphics Objekt welches angezeigt wird
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < GameModel.GRID_WIDTH; i++) {
            for (int j = 0; j < GameModel.GRID_HEIGHT; j++) {
                if (model.getCell(i, j) == SheepManager.SHEEP) {
                    g.setColor(Color.WHITE);
                } else if (model.getCell(i, j) == -1) { // Wolf
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillRect(i * 20, j * 20, 20, 20);
            }
        }
    }
}
