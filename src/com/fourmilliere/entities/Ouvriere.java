package com.fourmilliere.entities;


import java.util.ArrayList;
import java.util.Arrays;

import static com.fourmilliere.main.MainFourmilliere.temp;

public class Ouvriere extends Fourmi{

    String inventaire = null;
    public Ouvriere(Faction faction, int[] position) {
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
        System.out.println( "#################################" );
        System.out.println( "la position initial etait x ="+positionInitial[1]+" , y = "+ positionInitial[0]);
        System.out.println( "#################################" );
        System.out.println( "type de deplacement : " + typeDeDeplacement );
        // Ou elle va
        int[] positionFinal;
        ArrayList<int[]> casesTargets = new ArrayList<int[]>();
        ArrayList<int[]> targets = new ArrayList<int[]>();

        if(typeDeDeplacement.equals("cherche"))                     // Si on cherche des ressources
            targets = getDirectionPossible(this);           // on va recuperer les deplacement possible
        else if(typeDeDeplacement.equals("rentre")){                // Si on a déja des ressources
            targets = rentrer();                                // On rentre
            if (targets == null){                               // Si on est a porter de la reine
                System.out.println( "@@@@@ ON nouris notre reine !!!!!!!!   @@@@@@@@@@" );

                nourrir();                                          // On l'a nourie
                targets = getDirectionPossible(this);       // et on va recuperer les deplacement possible pour repartir
            }
        }

        for (int[] target: targets) {                              // Ici on va recuperer les cases qui ce cache derriere le deplacement

            if(testSiCaseslibre(target))       {             // Si la case est libre (pas de fourmi)
                casesTargets.add(target);                   // On l'ajoute a la liste des cases target possible
                System.out.println( "on a validé la possibilité");

            }else{
                System.out.println( "on a pas validé la possibilité");

            }
        }

        if(casesTargets.size() != 0)                                // Si on a trouver des cases on fait un random dessus
            positionFinal = randCase(casesTargets);
        else                                                        // Sinon elle ne bougera pas
            positionFinal = positionInitial;

        System.out.println( "#################################" );
        System.out.println( "la position Final est    x ="+positionFinal[0]+" , y = "+ positionFinal[1]);
        System.out.println( "#################################" );
        return positionFinal;
    }


    public  boolean testSiCaseslibre(int[] caseATester) {
        try {
            System.out.println( "testSiCaseslibre :" +temp[caseATester[1]][caseATester[0]].getTypeFourmi());
            if (temp[caseATester[1]][caseATester[0]].getTypeFourmi() == null )
                return true;
            else if (temp[caseATester[1]][caseATester[0]].getTypeFourmi() == "Reine") {
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
        if(this.inventaire == null) {
            positionTarget = getPositionFinal("cherche");
            if (temp[positionTarget[1]][positionTarget[0]].typeRessource != null) {
                this.inventaire = temp[positionTarget[1]][positionTarget[0]].getTypeRessource();
                temp[positionTarget[1]][positionTarget[0]].setTypeRessource(null);
                // On remet la couleur en noir
                temp[positionTarget[1]][positionTarget[0]].setFaction(new Faction());
            }
        }else{
            //TODO: Si la reine est morte lancer getPositionFinal("cherche");
            positionTarget = getPositionFinal("rentre");
        }
        int[] positionInitial = this.getPosition();
        temp[positionInitial[1]][positionInitial[0]].setTypeFourmi(null);
        // On remet la couleur en noir
        temp[positionInitial[1]][positionInitial[0]].setFaction(new Faction());

        this.setPosition(positionTarget);
        temp[positionTarget[1]][positionTarget[0]].setTypeFourmi("Ouvriere");
        // On remet la couleur de l'ouvriere
        temp[positionTarget[1]][positionTarget[0]].setFaction(this.faction);

    }

    public ArrayList<int[]>  rentrer() {
        int[] positionActuel = this.position;
        // TODO: if getreine() = NULL ==> errer jusqu'a ce qu'une guerriere la mange
        int[] positionReine = Reine.getReine(this.faction).getPosition();
        ArrayList<int[]> positionDispo= new ArrayList<int[]>();

        if (positionActuel[1] > positionReine[1]-1){
            // "x-1"
            positionDispo.add(new int[]{positionActuel[0],positionActuel[1]-1}); //droite
        }
        else if(positionActuel[1] < positionReine[1]+1) {
            //  "x+1"
            positionDispo.add(new int []{positionActuel[0], positionActuel[1]+1 }); //gauche
        }
        else if(positionActuel[1] == positionReine[1]) {
            if (positionActuel[0] == positionReine[0] - 1 || positionActuel[0] == positionReine[0] + 1) {
                return null;
            }
        }

        if (positionActuel[0] > positionReine[0]-1){
            // "y-1"
            positionDispo.add(new int[]{positionActuel[0]-1,positionActuel[1]}); //droite
        }
        else if(positionActuel[0] < positionReine[0]+1) {
            //  "y+1"
            positionDispo.add(new int []{positionActuel[0]+1, positionActuel[1] }); //gauche
        }
        else if(positionActuel[0] == positionReine[0]) {
            // Si il est a coté de la reine i
            if(positionActuel[1] == positionReine[1]-1 || positionActuel[1] == positionReine[1]+1 )
                return null;
        }


        return positionDispo;
    }

    public void nourrir() {
        Reine reine = Reine.getReine(this.faction);
        if(this.inventaire == "EAU")
            reine.setWater(reine.getWater()+1);
        else if(this.inventaire == "FRUIT")
            reine.setFood(reine.getFood()+1);
        this.inventaire = null;
    }

    public String getInventaire() {
        return inventaire;
    }

    public void setInventaire(String inventaire) {
        this.inventaire = inventaire;
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
}
