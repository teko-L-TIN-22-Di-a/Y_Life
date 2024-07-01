package org.wolfnsheep.View;

import org.wolfnsheep.GameModel;

import javax.swing.*;
import java.awt.*;

/**
 * Die ScoreView stellt den Score und die aktuelle Anzahl Schafe dar.
 */
public class ScoreView extends JPanel {
    private GameModel model;

    /**
     * Konstruktor für die ScoreView, der das Modell setzt und die Größe der Ansicht festlegt.
     *
     * @param model das Spielmodell
     */
    public ScoreView(GameModel model) {
        this.model = model;
        setPreferredSize(new Dimension(GameModel.GRID_WIDTH * 20, 40));
    }

    /**
     *  Anzeige des Scores und der aktuellen Schafanzahl
     * @param g das Graphics Objekt welches angezeigt wird
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.drawString("Score: " + model.getScore(), 10, 20);
        g.drawString("Aktuelle Anzahl der Schafe: " + model.getCurrentSheepCount(), 10, 35);
    }
}
