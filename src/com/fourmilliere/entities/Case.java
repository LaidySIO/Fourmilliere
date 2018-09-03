package com.fourmilliere.entities;

import com.fourmilliere.ihm.GUI;

public class Case {

    int x = 0;
    int y = 0;
    int id = 0;
    Faction f = null;
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
        return empty;
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

    public Faction getF() {
        return f;
    }

    public void setF(Faction f) {
        this.f = f;
    }

    @Override
    public String toString() {

        if (this.typeFourmi != null)
        {
            return this.typeFourmi.toString();
        }
        else if ((this.typeRessource != null)){
            return this.typeRessource.toString();
        }

        else {
            return "";
        }

    }
}
