package com.fourmilliere.ihm;

import com.fourmilliere.entities.Fourmi;
import com.fourmilliere.entities.Ouvriere;
import com.fourmilliere.entities.Reine;
import com.fourmilliere.main.MainFourmilliere;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
            }catch(InterruptedException e) {
                System.out.println("Exception e : " + e);
            }

            // Reine
            //
            for(int i = 0; i < listFourmis.size();i++)
            {
                String getClass = listFourmis.get(i).getClass().toString();
                if( getClass.equals( "class com.fourmilliere.entities.Reine")){
                    Reine reine = (Reine) listFourmis.get(i);
                    try {
                        Thread.sleep(1000);
                    }catch(InterruptedException e) {
                        System.out.println("Exception e : " + e);
                    }

                    reine.donnerVie();
                    GUI.regenerate();
                }
                else if( getClass.equals( "class com.fourmilliere.entities.Ouvriere")){
                    Ouvriere ouvriere = (Ouvriere) listFourmis.get(i);
                        ouvriere.seDeplacer();
                        GUI.regenerate();
                        System.out.println("ouvriere : " + ouvriere.toString());
                    }
                }

            for(int i = 0; i < listFourmis.size();i++)
            {
                String getClass = listFourmis.get(i).getClass().toString();
                if( getClass.equals( "class com.fourmilliere.entities.Ouvriere")){
                    Ouvriere ouvriere = (Ouvriere) listFourmis.get(i);
                    while(true) {
                        ouvriere.seDeplacer();
                        GUI.regenerate();

                        System.out.println("ouvriere : " + ouvriere.toString());
                        try {
                            Thread.sleep(1000);
                        }catch(InterruptedException e) {
                            System.out.println("Exception e : " + e);
                        }

                    }
                }
            }



        }
    }


}
