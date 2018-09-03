package com.fourmilliere.entities;

import java.util.Arrays;

public class Guerriere extends Fourmi{

    int victimes = 0;


    public Guerriere(Faction faction, int[] position) {
        this.faction = faction;
        this.position = position;
        this.alive = true;
    }

    @Override
    public void seDeplacer() {
        super.seDeplacer();
    }

    public void kill() {
        kill();
    }

    @Override
    public String toString() {
        return "Guerriere{" +
                "victimes=" + victimes +
                ", id=" + id +
                ", faction=" + faction +
                ", position=" + Arrays.toString(position) +
                ", alive=" + alive +
                '}';
    }
}
