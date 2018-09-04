package com.fourmilliere.entities;

import com.fourmilliere.ihm.GUI;
import com.fourmilliere.main.MainFourmilliere;

import java.util.ArrayList;
import java.util.Arrays;

import static com.fourmilliere.main.MainFourmilliere.listFourmis;
import static com.fourmilliere.main.MainFourmilliere.temp;

public class Reine extends Fourmi {

    private int water = 0;
    private int food = 0;

    public Reine(int id, Faction faction, int[] position) {
        this.id = id;
        this.faction = faction;

        this.position = position;
        this.alive = true;
    }

    public void donnerVie() {
        int[] position = this.getPosition();
        int naissance = 0;
        System.out.println("Position de la reine au début du jeu : x " + position[0] + " y : " + position[1]);
        int[] positionTemp = new int[2];
        int directionPos = getDirectionPossible(this).size();
        int loop = 0;

        while (naissance < 3){
            position = randCase(getDirectionPossible(this));
            try {
                if (MainFourmilliere.temp[position[1]][position[0]].typeFourmi == null && MainFourmilliere.temp[position[1]][position[0]] != null) {
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
                            int x = (int) (Math.random() * 2 + 1);
                            System.out.println("Random pour la troisième fourmi : " + x);
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

    public void actionDonnerVie(int[] positionTemp, String type) {
        int nextId = listFourmis.size() - 1;
        if (type.equals("Ouvriere")){
            listFourmis.add(new Ouvriere(nextId, this.faction, positionTemp));
        }

        else {
            listFourmis.add(new Guerriere(nextId, this.faction, positionTemp));
        }
        temp[positionTemp[1]][positionTemp[0]].setTypeFourmi(type);
        temp[positionTemp[1]][positionTemp[0]].setId(nextId);
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
}
