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

        positionTarget = randCase(getDirectionPossible(this));

        while(!isValidCase(positionTarget[1],positionTarget[0])) {
            positionTarget = randCase(getDirectionPossible(this));
        }

        // S'il y a ni Ressource ni Fourmi
        if (isFreeCase(positionTarget[1],positionTarget[0])) {

            GuerriereOutTOCase();

            GuerriereGoTOCase(positionTarget);
        }
        // S'il y a une Fourmi
        else if (temp[positionTarget[1]][positionTarget[0]].getTypeFourmi() != null
                && !temp[positionTarget[1]][positionTarget[0]].getFaction().equals(this.getFaction())) {
            String typeFourmi = temp[positionTarget[1]][positionTarget[0]].typeFourmi;

            // Dans tous les cas on doit liberer l'ancienne case de la guerriere
            GuerriereOutTOCase();

            if (typeFourmi.equals("Reine")){
                System.out.println("*********************  Oulala !! Une Reine  ***********************");

                GuerriereGoTOCase(positionTarget);
                victimes++;
            }
            if (typeFourmi.equals("Ouvriere")){
                System.out.println("*********************  Oulala !! Une Ouvriere  ***********************");

                GuerriereGoTOCase(positionTarget);
                victimes++;
            }
            if (typeFourmi.equals("Guerriere")){
                System.out.println("*********************  Oulala !! Une Guerriere  ***********************");
                int figth = (int) (Math.random() * 2 + 1);

                // Elle gagne le combat
                if (figth < 2){
                    // Elle gagne le combat : l''ancienne place de la guerriere est déja remis à vide
                    // on va écraser la guerriere adverse avec notre guerriere
                    GuerriereGoTOCase(positionTarget);
                    victimes++;
                }

            }
        }
    }

    public void kill() {
        kill();
    }

    public void GuerriereGoTOCase(int[] positionTarget){
        this.setPosition(positionTarget);
        temp[positionTarget[1]][positionTarget[0]].setTypeFourmi("Guerriere");
        // On remet la couleur de l'ouvriere
        temp[positionTarget[1]][positionTarget[0]].setFaction(this.faction);
    }

    public void GuerriereOutTOCase(){
        int[] positionInitial;
        positionInitial = this.getPosition();
        temp[positionInitial[1]][positionInitial[0]].setTypeFourmi(null);
        // On remet la couleur en noir
        temp[positionInitial[1]][positionInitial[0]].setFaction(new Faction());
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
