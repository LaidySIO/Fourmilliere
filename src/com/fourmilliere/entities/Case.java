package com.fourmilliere.entities;

import javax.swing.*;

import static com.fourmilliere.main.MainFourmiliere.fourmiliereCase;

public class Case {

    int x = 0;
    int y = 0;
    int id = 0;
    Faction faction = null;
    String typeRessource = null;
    String typeFourmi = null;
    Boolean empty = true;
    ImageIcon icon = null;

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

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {

        if (this.typeFourmi != null) {
            return this.typeFourmi.toString() + " - " + this.getId();
        } else if ((this.typeRessource != null)) {
            return this.typeRessource.toString();
        } else {
            return "";
        }

    }

    public static void clearCase(int[] positionTarget) {
        fourmiliereCase[positionTarget[1]][positionTarget[0]].setTypeFourmi(null);
        fourmiliereCase[positionTarget[1]][positionTarget[0]].setId(0);
        // On remet la couleur en noir
        fourmiliereCase[positionTarget[1]][positionTarget[0]].setFaction(new Faction());
        fourmiliereCase[positionTarget[1]][positionTarget[0]].setIcon(null);
/*        if (fourmiliereCase[positionTarget[1]][positionTarget[0]].typeRessource != null) {
            fourmiliereCase[positionTarget[1]][positionTarget[0]].setIcon(
                    setFoodIcon(fourmiliereCase[positionTarget[1]][positionTarget[0]].typeRessource)
            );

        } else
            fourmiliereCase[positionTarget[1]][positionTarget[0]].setIcon(null);*/


    }

    public static void addFourmiToCase(int[] positionTarget, Fourmi fourmi) {
        String getClass = fourmi.getClass().toString();
        System.out.println("addFourmiToCase => " + getClass);

        if (getClass.equals("class com.fourmilliere.entities.Ouvriere")) {
            fourmiliereCase[positionTarget[1]][positionTarget[0]].setTypeFourmi("Ouvriere");
        }
        if (getClass.equals("class com.fourmilliere.entities.Guerriere")) {
            fourmiliereCase[positionTarget[1]][positionTarget[0]].setTypeFourmi("Guerriere");
        }

        fourmiliereCase[positionTarget[1]][positionTarget[0]].setId(fourmi.getId());
        fourmiliereCase[positionTarget[1]][positionTarget[0]].setIcon(fourmi.getIcon());
        // On remet la couleur de la fourmi
        fourmiliereCase[positionTarget[1]][positionTarget[0]].setFaction(fourmi.getFaction());

    }

    public static ImageIcon setFoodIcon(String typeRessource) {

        if (typeRessource == "EAU")
            return new ImageIcon(ImageIcon.class.getResource("/com/fourmilliere/Files/water.png"));

        // Sinon c'est de la nourritutre
        return new ImageIcon(ImageIcon.class.getResource("/com/fourmilliere/Files/water.png"));

    }
}
