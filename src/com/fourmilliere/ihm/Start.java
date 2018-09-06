package com.fourmilliere.ihm;

import com.fourmilliere.entities.*;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import static com.fourmilliere.main.MainFourmiliere.gameOver;
import static com.fourmilliere.main.MainFourmiliere.listFourmis;

public class Start extends JFrame {

    public static final int vitesseJeu = 100;
    private static Start INSTANCE = null;
    private int nbColonies = 0;
    private int rare = 0;

    // Init des inputs
    JTextField taille = new JTextField(5);
    JTextField colonies = new JTextField(5);
    JTextField rarete = new JTextField(5);

    public static int size = 0;

    public static synchronized Start getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Start();
        }
        return INSTANCE;
    }

    public Start() {

        boolean paramsValidate;

        // Init des parametres du jeu
        paramsValidate = gameParams();

        if (paramsValidate) {
            // Reine
            // Au lancement du jeu les reines accouchent
            naissance();

            // Boucle du jeu
            gameLoop();
        } else {
            System.exit(0);
        }
    }

    public boolean gameParams() {

        // Boite de dialogue paramètres du jeu

        String instructions = "<HTML>" +
                "INSTRUCTIONS : <BR><BR>" +
                "Veuillez saisir un nombre de case supérieure à 3 <BR>" +
                "Veuillez saisir un nombre de colonie inférieure au nombre de case <BR>" +
                "Veuillez saisir un % de ressources max :  100 <BR>" +
                "</HTML>";
        JPanel myPanel = new JPanel();
        JLabel myLabel = new JLabel(instructions);
        myLabel.setForeground(Color.BLUE);
        myPanel.add(myLabel);
        myPanel.add(Box.createVerticalStrut(20));

        myPanel.add(new JLabel("Nombre de case :"));
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(taille);
        myPanel.add(Box.createVerticalStrut(20));


        myPanel.add(new JLabel("Nombre de colonies :"));
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(colonies);
        myPanel.add(Box.createVerticalStrut(20));

        myPanel.add(new JLabel("% de ressources :"));
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(rarete);
        myPanel.add(Box.createVerticalStrut(20));

        // Si parametres validés
        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Parametres du jeu ", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (isNumeric(taille.getText())
                    && isNumeric(colonies.getText())
                    && isNumeric(rarete.getText())) {

                size = Integer.parseInt(taille.getText());
                nbColonies = Integer.parseInt(colonies.getText());
                rare = Integer.parseInt(rarete.getText());

                if (size < 3
                        || nbColonies > size
                        || rare > 100) {
                    dialogBox("Veuillez suivre les instructions ! ", "Erreur de paramètre", false);
                }


            } else {
                dialogBox("Veuillez saisir des chiffres !", "Erreur de paramètre", false);
                new Start();

            }

            System.out.println("Nombre de lignes : " + taille.getText());
            System.out.println("Nombre de colonies : " + colonies.getText());
            System.out.println("Rareté des ressources : " + rarete.getText());

            new GUI(size, nbColonies, rare);

            try {
                // On attend que la partie se lance
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                System.out.println("Exception e : " + e);
            }
            return true;
        }
        return false;
    }

    public boolean isNumeric(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public void naissance() {

        for (int i = 0; i < listFourmis.size(); i++) {
            String getClass = listFourmis.get(i).getClass().toString();
            if (getClass.equals("class com.fourmilliere.entities.Reine")) {
                Reine reine = (Reine) listFourmis.get(i);
                reine.donnerVie();
            }
        }
    }

    public void gameLoop() {
        HashSet<Faction> faction = new HashSet();

        while (!gameOver) {
            faction = new HashSet();
            for (int i = 0; i < listFourmis.size(); i++) {
                if (listFourmis.get(i).isAlive()) {
                    faction.add(listFourmis.get(i).getFaction());
                    String getClass = listFourmis.get(i).getClass().toString();
                    if (getClass.equals("class com.fourmilliere.entities.Ouvriere")) {
                        Ouvriere ouvriere = (Ouvriere) listFourmis.get(i);
                        ouvriere.seDeplacer();

                        System.out.println("ouvriere : " + ouvriere.toString());
                    }
                    if (getClass.equals("class com.fourmilliere.entities.Guerriere")) {
                        Guerriere guerriere = (Guerriere) listFourmis.get(i);
                        guerriere.seDeplacer();

                        System.out.println("guerriere : " + guerriere.toString());
                    }

                    if (getClass.equals("class com.fourmilliere.entities.Reine")) {
                        Reine reine = (Reine) listFourmis.get(i);
                        reine.checkRessource();
                        System.out.println("Reine : " + reine.toString());
                    }

                    try {
                        GUI.regenerate();
                        // Vitesse du jeu
                        Thread.sleep(vitesseJeu);
                    } catch (InterruptedException e) {
                        System.out.println("Exception e : " + e);
                    }
                }
            }
            if (faction.size() == 1 && Integer.parseInt(colonies.getText()) > 1) {
                break;
            }

        }
        try {
            Thread.sleep(1000);
            // Pour parcourir le Set<>
            Iterator<Faction> itr = faction.iterator();

            while (itr.hasNext()) {
                Faction gagnant = itr.next();
                dialogWinBox("Bravo a la colonie : " + gagnant.getId(), gagnant);
                break;
            }

            System.exit(0);
        } catch (InterruptedException e) {
            System.out.println("Exception e : " + e);
        }
    }

    public void dialogBox(String msg, String title, boolean win) {
        taille.setText("");
        colonies.setText("");
        rarete.setText("");
        // typeMessage = 0
        int typeMessage = JOptionPane.OK_OPTION;

        if (win) {
            typeMessage = JOptionPane.INFORMATION_MESSAGE;
        }

        JOptionPane.showMessageDialog(null, new JPanel().add(new JLabel(msg)),
                title, typeMessage);
        if (!win) {
            new Start();
        }

    }

    public void dialogWinBox(String title, Faction faction) {

        String statsMessage = getStatMessage(faction);

        dialogBox(statsMessage, title, true);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Souhaitez-vous relancer une simulation ? "));

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Victoire", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            dialogBox("C'est reparti ! ", "Fin de la simulation. ", false);
        } else {
            dialogBox("Au revoir ", "Fin de la simulation.", true);
            System.exit(0);
        }
    }

    public String getStatMessage(Faction f) {

        String msg = "<HTML>";
        msg += "La Faction N°" + f.getId() + " a gagné !! <BR><BR>";
        msg += getStatGuerriereMessage();
        msg += getStatReineMessage();
        msg += getStatOuvriereMessage();

        msg += "</HTML>";

        return msg;

    }

    /*private String getStatMessage(String fourmiType) {

        String msg = String msg = "Classement des " + fourmiType + " : <BR>";

        for (int i = 0; i < 3; i++) {

            if (fourmiType.equals("Guerriere")) {
                ArrayList<Guerriere> bestFourmi = getBestGuerriere();
            }
            if (fourmiType.equals("Ouvriere")) {
                ArrayList<Ouvriere> bestFourmi = getBestOuvriere();
            }
            else {
                ArrayList<Reine> bestFourmi = getBestReine();
            }

            msg += "N°" + (i + 1)
                    + " La guerrière " + bestFourmi.get(i).getId()
                    + " de la Faction " + bestGuerriere.get(i).getFaction().getId()

        }
    }*/

    public String getStatGuerriereMessage() {

        ArrayList<Guerriere> bestGuerriere = getBestGuerriere();

        String msg = "Classement des Guerrières : <BR>";
        for (int i = 0; i < bestGuerriere.size(); i++) {
            msg += "N°" + (i + 1)
                    + " La guerrière " + bestGuerriere.get(i).getId()
                    + " de la Faction " + bestGuerriere.get(i).getFaction().getId()
                    + " avec " + bestGuerriere.get(i).getVictimes() + " victimes <br>";

            // Si on a les 3 meilleurs Guerrières
            if (i == 2) {
                break;
            }
        }
        msg += "<BR><BR>";
        return msg;
    }

    public String getStatReineMessage() {

        ArrayList<Reine> bestReine = getBestReine();

        String msg = "Classement des Reines : <BR>";
        for (int i = 0; i < bestReine.size(); i++) {
            msg += "N°" + (i + 1)
                    + " La Reine " + bestReine.get(i).getId()
                    + " de la Faction " + bestReine.get(i).getFaction().getId()
                    + " a donné vie à " + bestReine.get(i).getNbNaissance() + "  fourmis  <br>";

            // Si on a les 3 meilleurs Reines
            if (i == 2) {
                break;
            }
        }
        msg += "<BR><BR>";
        return msg;
    }

    public String getStatOuvriereMessage() {

        ArrayList<Ouvriere> bestOuvriere = getBestOuvriere();

        String msg = "Classement des Ouvriere : <BR>";
        for (int i = 0; i < bestOuvriere.size(); i++) {
            msg += "N°" + (i + 1)
                    + " L'ouvriere " + bestOuvriere.get(i).getId()
                    + " de la Faction " + bestOuvriere.get(i).getFaction().getId()
                    + " a nourri " + bestOuvriere.get(i).getNbNourrir() + "  fois sa reine  <br>";

            // Si on a les 3 meilleurs Reines
            if (i == 2) {
                break;
            }
        }
        msg += "<BR><BR>";
        return msg;
    }

    public ArrayList<Guerriere> getBestGuerriere() {

        ArrayList<Guerriere> listGuerriere = new ArrayList<>();

        for (Fourmi f : listFourmis) {
            if (f.getClass().toString().equals("class com.fourmilliere.entities.Guerriere")) {
                System.out.println(f);
                listGuerriere.add((Guerriere) f);
            }
        }

        Guerriere.sortByKills(listGuerriere);

        return listGuerriere;

    }

    public ArrayList<Reine> getBestReine() {

        ArrayList<Reine> listReine = new ArrayList<>();

        for (Fourmi f : listFourmis) {
            if (f.getClass().toString().equals("class com.fourmilliere.entities.Reine")) {
                System.out.println(f);
                listReine.add((Reine) f);
            }
        }

        Reine.sortByNaissance(listReine);

        return listReine;

    }

    public ArrayList<Ouvriere> getBestOuvriere() {

        ArrayList<Ouvriere> listOuvriere = new ArrayList<>();

        for (Fourmi f : listFourmis) {
            if (f.getClass().toString().equals("class com.fourmilliere.entities.Ouvriere")) {
                System.out.println(f);
                listOuvriere.add((Ouvriere) f);
            }
        }

        Ouvriere.sortByNourrir(listOuvriere);

        return listOuvriere;

    }
}
