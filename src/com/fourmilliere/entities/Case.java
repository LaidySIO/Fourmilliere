package com.fourmilliere.entities;

import static com.fourmilliere.main.MainFourmiliere.fourmiliere;

public class Case {

    int x = 0;
    int y = 0;
    int id = 0;
    Faction faction = null;
    String typeRessource = null;
    String typeFourmi = null;
    Boolean empty = true;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Boolean getEmpty() {
        return this.empty;
    }

    public boolean noBorder(int x, int y, int size) {
        if ((x > 1 && x < size - 2) && y > 1 && y < size - 2) {
            return true;
        }

        return false;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    public String getTypeRessource() {
        return typeRessource;
    }

    public void setTypeRessource(String typeRessource) {
        this.typeRessource = typeRessource;
    }

    public String getTypeFourmi() {
        return typeFourmi;
    }

    public void setTypeFourmi(String typeFourmi) {
        this.typeFourmi = typeFourmi;
        setEmpty(false);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    @Override
    public String toString() {

        if (this.typeFourmi != null)
        {
            return this.typeFourmi.toString() + " - " + this.getId();
        }
        else if ((this.typeRessource != null)){
            return this.typeRessource.toString();
        }

        else {
            return "";
        }

    }

    public static void clearCase(int[] positionTarget) {
            fourmiliere[positionTarget[1]][positionTarget[0]].setTypeFourmi(null);
            fourmiliere[positionTarget[1]][positionTarget[0]].setId(0);
            // On remet la couleur en noir
            fourmiliere[positionTarget[1]][positionTarget[0]].setFaction(new Faction());

    }

    public static void addFourmiToCase(int[] positionTarget, Fourmi fourmi) {
        String getClass = fourmi.getClass().toString();
        System.out.println("addFourmiToCase => " + getClass);

            if(getClass.equals("class com.fourmilliere.entities.Ouvriere")) {
                fourmiliere[positionTarget[1]][positionTarget[0]].setTypeFourmi("Ouvriere");
            }
            if(getClass.equals("class com.fourmilliere.entities.Guerriere")) {
                fourmiliere[positionTarget[1]][positionTarget[0]].setTypeFourmi("Guerriere");
            }

            fourmiliere[positionTarget[1]][positionTarget[0]].setId(fourmi.getId());
            // On remet la couleur de la fourmi
            fourmiliere[positionTarget[1]][positionTarget[0]].setFaction(fourmi.getFaction());

    }
}
