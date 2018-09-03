package com.fourmilliere.entities;

import java.awt.*;

public class Faction {

    private int id;
    private Color color = null;

    public Faction() {
        this.id = 0;
        this.color = new Color(0,0,0);
    }

    public Faction(int id) {
        this.id = id;
        this.color = generateColor();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private Color generateColor() {
        int red = (int) (Math.random() * 255);
        int blue = (int) (Math.random() * 255);
        int green = (int) (Math.random() * 255);

        return new Color(red,blue,green);
    }
}
