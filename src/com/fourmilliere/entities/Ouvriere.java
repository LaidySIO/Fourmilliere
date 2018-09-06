package com.fourmilliere.entities;


import java.util.*;

import static com.fourmilliere.main.MainFourmiliere.fourmiliereCase;

public class Ouvriere extends Fourmi{

    String inventaire = null;
    int nbNourrir = 0;


    public Ouvriere(int id, Faction faction, int[] position) {
        this.id = id;
        this.faction = faction;
        this.position = position;
        this.alive = true;
    }

    /**
     * Fonction pour trouver la case du déplacement
     * @return
     */
    public  int[] getPositionFinal(String typeDeDeplacement) {
        // Ou elle est
        int[] positionInitial  = this.position;
        // Ou elle va
        int[] positionFinal;

        ArrayList<int[]> casesTargets = new ArrayList<int[]>();
        ArrayList<int[]> targets = new ArrayList<int[]>();
        // Si on cherche des ressources
        if(typeDeDeplacement.equals("cherche"))
            targets = getDirectionPossible(this);
        // Si on déja de ressources
        else if(typeDeDeplacement.equals("rentre")){
            targets = rentrer();
            // Si la reine est juste à coté
            if (targets == null){
                nourrir();
                targets = getDirectionPossible(this);
            }
        }
        // Pour chaque déplacements dispo
        for (int[] target: targets) {

            if(testSiCaseslibre(target))       {
                casesTargets.add(target);
            }
        }

        if(casesTargets.size() != 0)
            positionFinal = randCase(casesTargets);
        else
            positionFinal = positionInitial;

        return positionFinal;
    }


    public  boolean testSiCaseslibre(int[] caseATester) {
        try {
            if (fourmiliereCase[caseATester[1]][caseATester[0]].getTypeFourmi() == null )
                return true;
            else if (fourmiliereCase[caseATester[1]][caseATester[0]].getTypeFourmi() == "Reine" && this.inventaire != null) {
                // Si on est a coté de la reine
                nourrir();
                return false;
            }
            else
                return false;
        }catch( ArrayIndexOutOfBoundsException e){
            System.out.println( "Excepetion in testSiCaseslibre :" + e );
            return false;
        }
    }


    @Override
    public void seDeplacer() {
        int[] positionTarget;
        if(this.inventaire != null) {
            if (Reine.getReine(this.faction).getPosition() != null)
                positionTarget = getPositionFinal("rentre");
            else
                positionTarget = getPositionFinal("cherche");
        }else{
            positionTarget = getPositionFinal("cherche");
            if (fourmiliereCase[positionTarget[1]][positionTarget[0]].typeRessource != null) {
                this.inventaire = fourmiliereCase[positionTarget[1]][positionTarget[0]].getTypeRessource();
                fourmiliereCase[positionTarget[1]][positionTarget[0]].typeRessource = null;
            }
        }
        int[] positionInitial = this.getPosition();
        Case.clearCase(positionInitial);

        this.setPosition(positionTarget);
        Case.addFourmiToCase(positionTarget,this);

    }

    /**
     * Va comparer la position x et y de la reine par rapport à la fourmi
     * pour avoir des positions disponibles plus pertinentes
     *
     * @return Liste de déplacements possibles
     */
    public ArrayList<int[]>  rentrer() {

        int[] positionActuel = this.position;
        int[] positionReine = Reine.getReine(this.faction).getPosition();
        ArrayList<int[]> positionDispo= new ArrayList<int[]>();

        if (positionActuel[1] > positionReine[1]-1){
            // "x-1"
            positionDispo.add(new int[]{positionActuel[0],positionActuel[1]-1}); //gauche
        }
        else if(positionActuel[1] < positionReine[1]+1) {
            //  "x+1"
            positionDispo.add(new int []{positionActuel[0], positionActuel[1]+1 }); //droite
        }
        else if(positionActuel[1] == positionReine[1]) {
            // Si il est a coté de la reine i
            if (positionActuel[0] == positionReine[0] - 1 || positionActuel[0] == positionReine[0] + 1) {
                return null;
            }
        }

        if (positionActuel[0] > positionReine[0]-1){
            // "y-1"
            positionDispo.add(new int[]{positionActuel[0]-1,positionActuel[1]}); //haut
        }
        else if(positionActuel[0] < positionReine[0]+1) {
            //  "y+1"
            positionDispo.add(new int []{positionActuel[0]+1, positionActuel[1] }); //bas
        }
        else if(positionActuel[0] == positionReine[0]) {
            // Si il est a coté de la reine i
            if(positionActuel[1] == positionReine[1]-1 || positionActuel[1] == positionReine[1]+1 )
                return null;
        }


        return positionDispo;
    }

    public void nourrir() {
        this.setNbNourrir(getNbNourrir() + 1);
        System.out.println("ON NOURRIT LA REINE AVEC " + this.inventaire);
        Reine reine = Reine.getReine(this.faction);
        if(this.inventaire == "EAU")
            reine.setWater(reine.getWater()+1);
        else if(this.inventaire == "FRUITS")
            reine.setFood(reine.getFood()+1);
        this.inventaire = null;
    }

    public String getInventaire() {
        return inventaire;
    }

    public void setInventaire(String inventaire) {
        this.inventaire = inventaire;
    }

    public int getNbNourrir() {
        return nbNourrir;
    }

    public void setNbNourrir(int nbNourrir) {
        this.nbNourrir = nbNourrir;
    }

    @Override
    public String toString() {
        return "Ouvriere{" +
                "inventaire='" + inventaire + '\'' +
                ", id=" + id +
                ", faction=" + faction +
                ", position=" + Arrays.toString(position) +
                ", alive=" + alive +
                '}';
    }

    /**
     * Compare le nombre fois qu'une ouvrière a nourri sa reine
     */

    public static Comparator<Ouvriere> CompareNourrir = new Comparator<Ouvriere>() {

        @Override
        public int compare(Ouvriere o1, Ouvriere o2) {

            return Integer.compare(o2.getNbNourrir(),o1.getNbNourrir());
        }
    };

    /**
     *
     * @param liste : liste des ouvrières de la liste listFourmi
     * @return Retourne la liste des ouvrières selon le nombre de fois qu'elles ont nourri leurs reine
     */
    public static List<Ouvriere> sortByNourrir(List<Ouvriere> liste) {
        List<Ouvriere> sortedList = liste;

        Collections.sort(sortedList, Ouvriere.CompareNourrir);
        return sortedList;
    }
}
