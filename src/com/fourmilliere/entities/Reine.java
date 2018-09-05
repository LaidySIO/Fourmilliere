package com.fourmilliere.entities;

import javax.swing.*;
import java.util.*;

import static com.fourmilliere.main.MainFourmiliere.fourmiliereCase;
import static com.fourmilliere.main.MainFourmiliere.listFourmis;

public class Reine extends Fourmi {

    private int water = 0;
    private int food = 0;
    private int nbNaissance = 0;

    public Reine(int id, Faction faction, int[] position) {
        this.id = id;
        this.faction = faction;
        this.position = position;
        this.alive = true;
        this.icon = new ImageIcon(getClass().getResource("/com/fourmilliere/Files/queen.png"));
    }

    /**
     * La reine va donner naissance à une fourmi
     * elle cherche un emplacement libre
     */
    public void donnerVie() {

        int[] position = this.getPosition();
        int naissance = 0;
        System.out.println("Position de la reine N° "+ this.id +" au début du jeu : x " + position[0] + " y : " + position[1]);
        int[] positionTemp = new int[2];
        // TODO : Si aucune place de libre autour de la reine essayer d'élargir le scope
        int directionPos = getDirectionPossible(this).size();
        int loop = 0;

        for (int i =0; i<=3;i++){
            position = randCase(getDirectionPossible(this));
            try {
                if (fourmiliereCase[position[1]][position[0]].typeFourmi == null && fourmiliereCase[position[1]][position[0]] != null) {
                    positionTemp = position;
                    switch (naissance) {
                        case 0:
                            actionDonnerVie(positionTemp, "Ouvriere");
                            break;
                        case 1:
                            actionDonnerVie(positionTemp, "Guerriere");
                            break;
                        case 2:
                            // Random 1 ou 2
                            // Random pour la troisième fourmi
                            int x = (int) (Math.random() * 2 + 1);
                            if (x < 2) {
                                actionDonnerVie(positionTemp, "Ouvriere");
                                break;
                            }
                            else {
                                actionDonnerVie(positionTemp, "Guerriere");
                                break;
                            }
                    }
                    loop++;
                    naissance++;
                }
            }catch (ArrayIndexOutOfBoundsException e){

                System.out.println("une reine a essayer de donner vie a la position :"+positionTemp[1] +" , "+positionTemp[0]+" sans succes car la case n'existe pas");
            }
        }
    }

    /**
     * Ajoute un nouvelle fourmi dans la fourmiliereCase
     * @param positionTemp Position de la fourmi qui va naitre
     * @param type Ouvrière ou Guerrière
     */
    public void actionDonnerVie(int[] positionTemp, String type) {
        // On incrémente le compteur des naissances
        this.setNbNaissance(getNbNaissance() + 1);
        // Calcul du prochain id a attribuer
        int nextId = listFourmis.size();
        if (type.equals("Ouvriere")){
            listFourmis.add(new Ouvriere(nextId, this.faction, positionTemp));
        }
        else {
            listFourmis.add(new Guerriere(nextId, this.faction, positionTemp));
        }
        fourmiliereCase[positionTemp[1]][positionTemp[0]].setTypeFourmi(type);
        fourmiliereCase[positionTemp[1]][positionTemp[0]].setId(nextId);
        fourmiliereCase[positionTemp[1]][positionTemp[0]].setIcon(listFourmis.get(nextId).getIcon());
        System.out.println("Naissance d'une "+ type +" au debut du jeu = " + listFourmis.get(listFourmis.size() - 1).toString());

    }


    public static Reine getReine(Faction faction ) {
        ArrayList<Fourmi> mesFourmis = listFourmis;
        for (Fourmi fourmi:mesFourmis) {
            String getClass = fourmi.getClass().toString();
            if( getClass.equals( "class com.fourmilliere.entities.Reine")) {
                if (fourmi.getFaction().equals(faction))
                    return (Reine) fourmi;
            }
        }
        return null;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getNbNaissance() {
        return nbNaissance;
    }

    public void setNbNaissance(int nbNaissance) {
        this.nbNaissance = nbNaissance;
    }

    @Override
    public String toString() {
        return "Reine{" +
                "water=" + water +
                ", food=" + food +
                ", id=" + id +
                ", faction=" + faction +
                ", position=" + Arrays.toString(position) +
                ", alive=" + alive +
                '}';
    }

    public void checkRessource() {
        if (getFood() > 0 && getWater() > 0) {
            System.out.println("La reine accouche !! ");
            this.donnerVie();
            this.setFood(this.getFood() - 1);
            this.setWater(this.getWater() - 1);
        }
    }

    /**
     * Compare le nombre de naissance des reines
     */

    public static Comparator<Reine> CompareNaissances = new Comparator<Reine>() {

        @Override
        public int compare(Reine r1, Reine r2) {

            return Integer.compare(r2.getNbNaissance(),r1.getNbNaissance());
        }
    };

    /**
     *
     * @param liste : liste des reines de la liste listFourmi
     * @return Retourne la liste des reines selon le nombre de naissance
     */
    public static List<Reine> sortByNaissance(List<Reine> liste) {
        List<Reine> sortedList = liste;

        Collections.sort(sortedList, Reine.CompareNaissances);
        return sortedList;
    }
}
