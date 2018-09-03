package com.fourmilliere.entities;

import java.util.Arrays;

import static com.fourmilliere.ihm.GUI.isFreeCase;
import static com.fourmilliere.ihm.GUI.isValidCase;
import static com.fourmilliere.main.MainFourmilliere.temp;

public class Guerriere extends Fourmi{

    int victimes = 0;


    public Guerriere(Faction faction, int[] position) {
        this.faction = faction;
        this.position = position;
        this.alive = true;
    }

    @Override
    public void seDeplacer() {

        int[] positionTarget;
        int[] positionInitial;

        positionTarget = randCase(getDirectionPossible(this));

        while(!isValidCase(positionTarget[1],positionTarget[0])) {
            positionTarget = randCase(getDirectionPossible(this));
        }


        if (isFreeCase(positionTarget[1],positionTarget[0])) {
            positionInitial = this.getPosition();
            temp[positionInitial[1]][positionInitial[0]].setTypeFourmi(null);
            // On remet la couleur en noir
            temp[positionInitial[1]][positionInitial[0]].setFaction(new Faction());

            this.setPosition(positionTarget);
            temp[positionTarget[1]][positionTarget[0]].setTypeFourmi("Guerriere");
            // On remet la couleur de l'ouvriere
            temp[positionTarget[1]][positionTarget[0]].setFaction(this.faction);
        }




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
