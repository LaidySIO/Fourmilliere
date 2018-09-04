package com.fourmilliere.ihm;

import com.fourmilliere.entities.Fourmi;
import com.fourmilliere.entities.Guerriere;
import com.fourmilliere.entities.Ouvriere;
import com.fourmilliere.entities.Reine;

import javax.swing.*;
import java.util.ArrayList;

import static com.fourmilliere.main.MainFourmilliere.gameOver;
import static com.fourmilliere.main.MainFourmilliere.listFourmis;

public class Start extends JFrame {


    private static Start INSTANCE = null;
    public static int size = 0;

    public static synchronized Start getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Start();
        }
        return INSTANCE;
    }

    public Start() {
        JFrame frame;
        int nbColonies = 0;
        int rare = 0;
        JTextField taille = new JTextField(5);
        JTextField colonies = new JTextField(5);
        JTextField rarete = new JTextField(5);

        JPanel myPanel = new JPanel();

        myPanel.add(new JLabel("Nombre de case :"));
        myPanel.add(taille);


        //myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

        myPanel.add(new JLabel("Nombre de colonies :"));
        myPanel.add(colonies);

        myPanel.add(new JLabel("% de ressources :"));
        myPanel.add(rarete);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Parametres du jeu ", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            size = Integer.parseInt(taille.getText());
            nbColonies = Integer.parseInt(colonies.getText());
            rare = Integer.parseInt(rarete.getText());
            System.out.println("Nombre de lignes : " + taille.getText());
            System.out.println("Nombre de colonies : " + colonies.getText());
            System.out.println("Rareté des ressources : " + rarete.getText());

            //new Grids("Test", (80*size), (80*size), size, size).setVisible(true);
            new GUI(size, nbColonies, rare);
            try {
                // On attend que la partie se lance
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                System.out.println("Exception e : " + e);
            }

            // Reine
            // Au lancement du jeu les reines accouchent
            for (int i = 0; i < listFourmis.size(); i++) {
                String getClass = listFourmis.get(i).getClass().toString();
                if (getClass.equals("class com.fourmilliere.entities.Reine")) {
                    Reine reine = (Reine) listFourmis.get(i);

                    reine.donnerVie();
                    GUI.regenerate();
                }
            }
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
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            System.out.println("Exception e : " + e);
                        }
                    }
                    GUI.regenerate();
                }
            }
        }
    }
    // Maj de la liste
    private void refreshListFourmis() {
        ArrayList<Fourmi> arrayListTemp = listFourmis;
        for (int i = 0; i < arrayListTemp.size(); i++) {
            if (!arrayListTemp.get(i).isAlive()) {
                listFourmis.remove(arrayListTemp);
            }
        }
    }


}
