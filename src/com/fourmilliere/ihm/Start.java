package com.fourmilliere.ihm;

import com.fourmilliere.entities.Guerriere;
import com.fourmilliere.entities.Ouvriere;
import com.fourmilliere.entities.Reine;

import javax.swing.*;

import static com.fourmilliere.main.MainFourmiliere.gameOver;
import static com.fourmilliere.main.MainFourmiliere.listFourmis;

public class Start extends JFrame {


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

        if(paramsValidate)  {
            // Reine
            // Au lancement du jeu les reines accouchent
            naissance();

            // Boucle du jeu
            gameLoop();
        }
        else {
            System.exit(0);
        }
    }

    public boolean gameParams() {
        // Boite de dialogue paramètres du jeu
        JPanel myPanel = new JPanel();

        myPanel.add(new JLabel("INSTRUCTIONS : "));
        myPanel.add(new JLabel("Veuillez saisir une taille supérieure à 3"));
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Veuillez saisir un nombre de colonie inférieure à la taille"));
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.add(new JLabel("Veuillez saisir un % de ressources max :  100"));
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

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
                    dialogErrorBox("Veuillez suivre les instructions ! ");
                    new Start();
                }


            } else {
                dialogErrorBox("Veuillez saisir des chiffres !");
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
//                GUI.regenerate();
            }
        }
    }

    public void gameLoop() {
        while (!gameOver) {
            for (int i = 0; i < listFourmis.size(); i++) {
                // TODO: MAJ de la liste des fourmis en cas de mort
                if (listFourmis.get(i).isAlive()) {
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
                        Reine reine  = (Reine) listFourmis.get(i);
                        reine.checkRessource();
                        System.out.println("Reine : " + reine.toString());
                    }

                    try {
                        GUI.regenerate();
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        System.out.println("Exception e : " + e);
                    }
                }
            }
        }
    }

    public void dialogErrorBox(String msg) {
        taille.setText("");
        colonies.setText("");
        rarete.setText("");
        JOptionPane.showMessageDialog(null, new JPanel().add(new JLabel(msg)),
                "Erreur de parametre ", JOptionPane.OK_OPTION);
    }
}
