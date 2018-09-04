package com.fourmilliere.entities;

import java.util.Arrays;

import static com.fourmilliere.ihm.GUI.isFreeCase;
import static com.fourmilliere.ihm.GUI.isValidCase;
import static com.fourmilliere.main.MainFourmilliere.temp;

public class Guerriere extends Fourmi{

    int victimes = 0;


    public Guerriere(int id, Faction faction, int[] position) {
        this.id = id;
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

            System.out.println("*********************  Oulala !! Une "+typeFourmi+"  ***********************");

            // Dans tous les cas on doit liberer l'ancienne case de la guerriere
            GuerriereOutTOCase();


            if (typeFourmi.equals("Guerriere")){
                int figth = (int) (Math.random() * 2 + 1);

                // Elle gagne le combat
                if (figth < 2){
                    // Elle gagne le combat : l''ancienne place de la guerriere est déja remis à vide
                    // on va écraser la guerriere adverse avec notre guerriere

                    // ON set la case a null avant le depalcement
                    temp[positionTarget[1]][positionTarget[0]].setTypeFourmi(null);
                    GuerriereGoTOCase(positionTarget);
                    victimes++;
                }
            }
            else{

                GuerriereGoTOCase(positionTarget);
                victimes++;
            }
            kill(temp[positionTarget[1]][positionTarget[0]].getId());
        }
    }

    public void kill(int ennemi) {
        // TODO: Il faut recuperer l'id de la fourmi qui meurt et set alive() à false
        int idEnnemi = ennemi;
        Fourmi f = findById(idEnnemi);
        if (f != null) {
            f.setAlive(false);
           System.out.println("Malheureusement la Fourmi " +f.toString() +" a été tué :(");
        }

    }

    public void GuerriereGoTOCase(int[] positionTarget){
        this.setPosition(positionTarget);
        Case.addFourmiToCase(positionTarget, this);
        temp[positionTarget[1]][positionTarget[0]].setTypeFourmi("Guerriere");
        temp[positionTarget[1]][positionTarget[0]].setId(this.id);
        // On remet la couleur de l'ouvriere
        temp[positionTarget[1]][positionTarget[0]].setFaction(this.faction);
    }

    public void GuerriereOutTOCase(){
        int[] positionInitial;
        positionInitial = this.getPosition();
        Case.clearCase(positionInitial);
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
