package com.fourmilliere.entities;

public class Guerriere extends Fourmi{

    int victimes = 0;


    public Guerriere(int faction, int[] position) {
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

}
