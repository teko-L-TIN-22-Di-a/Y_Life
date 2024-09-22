package org.wolfnsheep.View;

import org.wolfnsheep.GameModel;
import org.wolfnsheep.entity.Wolf;

import javax.swing.*;
import java.awt.*;

/**
 * Die ScoreView stellt den Score und die aktuelle Anzahl Schafe dar.
 */
public class ScoreView extends JPanel {
    private final GameModel model;

    /**
     * Konstruktor für die ScoreView, der das Modell setzt und die Größe der Ansicht festlegt.
     *
     * @param model das Spielmodell
     */
    public ScoreView(GameModel model) {
        this.model = model;
        setPreferredSize(new Dimension(GameModel.GRID_WIDTH * 20, 60));
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
        int currentSheepCount = model.getCurrentSheepCount();
        // Überprüfe, ob das Spiel gewonnen wurde
        if (currentSheepCount == 0) {
            g.drawString("Alle Schafe gefressen! Du hast gewonnen!", 10, 50);
        }
        g.drawString("Score: " + model.getScore(), 10, 20);
        g.drawString("Aktuelle Anzahl der Schafe: " + currentSheepCount, 10, 35);

        // Anzeige der Energie des Wolfs
        Wolf wolf = model.getWolf();
        g.drawString("Energie des Wolfs: " + wolf.getEnergy(), 10, 50);

        // Visualisiere die Energie als Balken
        int energyBarWidth = 200;
        int energyBarHeight = 10;
        int energyPercentage = (wolf.getEnergy() * energyBarWidth) / Wolf.MAX_ENERGY;
        g.setColor(Color.RED);
        g.fillRect(10, 55, energyPercentage, energyBarHeight);
        g.setColor(Color.WHITE);
        g.drawRect(10, 55, energyBarWidth, energyBarHeight);

    }
}
