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

    public Reine(int faction, int[] position) {
        this.faction = faction;

        this.position = position;
        this.alive = true;
    }

    public void donnerVie() {
        int[] position = this.getPosition();
        int[] positionTemp = new int[2];

        for (int i =0; i<=2;i++){
            position = randCase(getDirectionPossible(this));
            try {
                if (MainFourmilliere.temp[position[1]][position[0]].typeFourmi == null && MainFourmilliere.temp[position[1]][position[0]] != null) {
                    positionTemp = position;

                    positionTemp = randCase(getDirectionPossible(this));
                    positionTemp[1]++;
                    listFourmis.add(new Ouvriere(this.faction, positionTemp));
                    temp[positionTemp[1]][positionTemp[0]].setTypeFourmi("Ouvriere");
                    System.out.println(listFourmis.get(listFourmis.size() - 1).toString());
                }
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("une reine a essayer de donner vie a la position :"+positionTemp[1] +" , "+positionTemp[0]+" sans succes car la case n'existe pas");

            }
        }
    }

    public static Reine getReine(int faction ) {
        ArrayList<Fourmi> mesFourmis = listFourmis;
        for (Fourmi fourmi:mesFourmis) {
            String getClass = fourmi.getClass().toString();
            if( getClass.equals( "class com.fourmilliere.entities.Reine")) {
                if (fourmi.getFaction() == faction)
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
