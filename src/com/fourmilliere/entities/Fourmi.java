package com.fourmilliere.entities;

import com.fourmilliere.dao.IActions;
import com.fourmilliere.ihm.Start;
import com.fourmilliere.main.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static com.fourmilliere.main.MainFourmilliere.listFourmis;


public abstract class Fourmi  implements IActions{

    int id;
    protected Faction faction;
    protected int[] position;
    protected boolean alive = true;

    /**
     * Fonction pour faire une liste des direction possible
     * @return
     */
    public static ArrayList<int[]> getDirectionPossible(Fourmi fourmis)
    {

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
