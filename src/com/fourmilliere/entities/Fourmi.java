package com.fourmilliere.entities;

import com.fourmilliere.dao.IActions;
import com.fourmilliere.ihm.Start;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static com.fourmilliere.main.MainFourmiliere.listFourmis;


public abstract class Fourmi  implements IActions{

    int id;
    protected Faction faction;
    protected int[] position;
    protected boolean alive = true;

    /**
     * Fonction pour faire une liste des direction possible
     * @return Liste des déplacements disponibles
     */
    public static ArrayList<int[]> getDirectionPossible(Fourmi fourmis)
    {

        ArrayList<int[]> positionDispo= new ArrayList<int[]>();
        int[] positionActuel = fourmis.getPosition(); // on recupere la position de notre fourmis
        // recupère positionActuel avec maj des positions possibles
        int[] positionActuelTemp;
        // Si la fourmi est au bord à gauche
        /*
        *   x o o
        *   x o o
        *   x o o
        */
        if(positionActuel[1] == 0) {
            // Tableau positionActuelTemp = positionActuel int[y,x] avec x+1
            positionActuelTemp = new int[]{positionActuel[0],positionActuel[1] + 1};
            positionDispo.add(positionActuelTemp); //droite
        }
        // Si la fourmi est au bord à droite
        /*
         *   o o x
         *   o o x
         *   o o x
         */
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
        // Si la fourmi est au bord en haut
        /*
         *   x x x
         *   o o o
         *   o o o
         */
        if(positionActuel[0] == 0) {
            // Tableau positionActuelTemp = positionActuel int[y,x] avec y+1
            positionActuelTemp = new int[]{positionActuel[0] + 1, positionActuel[1]};
            positionDispo.add(positionActuelTemp); //bas
        }
        // Si la fourmi est au bord en bas
        /*
         *   o o o
         *   o o o
         *   x x x
         */
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

    /**
     *
     * @param directions List des déplacement disponibles
     * @return coordonnées (int[]) d'un emplacement disponible parmi ceux disponibles
     */
    public static int[] randCase(ArrayList<int[]> directions)
    {
        int randomNum = ThreadLocalRandom.current().nextInt(0, directions.size());
        return directions.get(randomNum);
    }

    public void seDeplacer() {

    }


    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
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

    public Fourmi findById(int id) {
        for (Fourmi f : listFourmis) {
            if (f.getId() == id){
                return f;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fourmi)) return false;
        Fourmi fourmi = (Fourmi) o;
        return getId() == fourmi.getId() &&
                isAlive() == fourmi.isAlive() &&
                Objects.equals(getFaction(), fourmi.getFaction()) &&
                Arrays.equals(getPosition(), fourmi.getPosition());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
