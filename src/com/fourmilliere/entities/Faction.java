package com.fourmilliere.entities;

import java.awt.*;

public class Faction {

    private int id;
    private Color color = null;
    // Reine de cette Faction
    private Reine reine = null;

    public Faction(int id, Color color, Reine reine) {
        this.id = id;
        this.color = generateColor();
        this.reine = reine;
    }



    private Color generateColor() {
        int red = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);

        return new Color(red,blue,green);
    }
}
