package com.fourmilliere.entities;

import com.fourmilliere.dao.IActions;
import com.fourmilliere.ihm.Start;
import com.fourmilliere.main.Utils;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public abstract class Fourmi  implements IActions{

    int id;
    protected int faction;
    protected int[] position;
    protected boolean alive = true;

    /**
     * Fonction pour faire une liste des direction possible
     * @return
     */
    public static ArrayList<int[]> getDirectionPossible(Fourmi fourmis)
    {
        /*
        ArrayList<String> positionDispo= new ArrayList<String>();
        int[] positionActuel = fourmis.getPosition(); // on recupere la position de notre fourmis

        if(positionActuel[0] == 0)
            positionDispo.add("y+1"); //bas
        else if(positionActuel[0] == Start.size-1)
            positionDispo.add("y-1"); //haut
        else{
            positionDispo.add("y-1"); //haut
            positionDispo.add("y+1"); //bas
        }

        if(positionActuel[1] == 0)
            positionDispo.add("x+1"); //droite
        else if(positionActuel[1] == Start.size-1)
            positionDispo.add("x-1"); //gauche
        else{
            positionDispo.add("x+1"); //droite
            positionDispo.add("x-1"); //gauche
        }

        return positionDispo;
        */

        ArrayList<int[]> positionDispo= new ArrayList<int[]>();
        int[] positionActuel = fourmis.getPosition(); // on recupere la position de notre fourmis
        // recupère positionActuel avec maj des positions possibles
        int[] positionActuelTemp;

        if(positionActuel[1] == 0) {
            // Tableau positionActuelTemp = positionActuel int[y,x] avec x+1
            positionActuelTemp = new int[]{positionActuel[0],positionActuel[1] + 1};
            positionDispo.add(positionActuelTemp); //droite
        }
        else if(positionActuel[0] == Start.size -1) {
            // Tableau positionActuelTemp = positionActuel int[y,x] avec x-1
            positionActuelTemp = new int []{positionActuel[0], positionActuel[1] - 1};
            positionDispo.add(positionActuelTemp); //gauche
        }
        else{
            // Tableau positionActuelTemp = positionActuel int[y,x] avec x-1
            positionActuelTemp = new int []{positionActuel[0], positionActuel[1] + 1};
            positionDispo.add(positionActuelTemp); //droite
            positionActuelTemp = new int []{positionActuel[0], positionActuel[1] - 1};
            positionDispo.add(positionActuelTemp); //gauche
        }

        if(positionActuel[0] == 0) {
            // Tableau positionActuelTemp = positionActuel int[y,x] avec y+1
            positionActuelTemp = new int[]{positionActuel[0] + 1, positionActuel[1]};
            positionDispo.add(positionActuelTemp); //bas
        }
        else if(positionActuel[1] == Start.size -1) {
            // Tableau positionActuelTemp = positionActuel int[y,x] avec y-1
            positionActuelTemp = new int[]{positionActuel[0] - 1, positionActuel[1]};
            positionDispo.add(positionActuelTemp); //haut
        }
        else{
            positionActuelTemp = new int[]{positionActuel[0] + 1, positionActuel[1]};
            positionDispo.add(positionActuelTemp); //bas
            positionActuelTemp = new int[]{positionActuel[0] - 1, positionActuel[1]};
            positionDispo.add(positionActuelTemp); //haut

        }
        return positionDispo;



    }

    public static int[] randCase(ArrayList<int[]> directions)
    {
        int randomNum = ThreadLocalRandom.current().nextInt(0, directions.size());
        return directions.get(randomNum);
    }

    public void seDeplacer() {

    }




    public int getFaction() {
        return faction;
    }

    public void setFaction(int faction) {
        this.faction = faction;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
