package com.fourmilliere.ihm;

import com.fourmilliere.entities.Case;
import com.fourmilliere.entities.Faction;
import com.fourmilliere.entities.Reine;
import com.fourmilliere.main.MainFourmiliere;

import java.awt.*;
import javax.swing.*;
import java.lang.*;

import static com.fourmilliere.main.MainFourmiliere.board;
import static com.fourmilliere.main.MainFourmiliere.fourmiliere;

public class GUI {

    public GUI(int size, int nbColonies, int rarete) {

        MainFourmiliere.index = new JTextField[size][size];
        MainFourmiliere.frame.setSize(500, 500);
        JPanel panel = new JPanel();
        board.setLayout(new GridLayout(size, size));
        fourmiliere = generate(size, nbColonies, rarete);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                MainFourmiliere.index[i][j] = new JTextField(1);
                MainFourmiliere.index[i][j].setForeground(fourmiliere[i][j].getFaction().getColor());
                if (fourmiliere[i][j].toString().equals("Reine"))
                    MainFourmiliere.index[i][j].setFont(new Font("Verdana",Font.BOLD,13));
                MainFourmiliere.index[i][j].setText(fourmiliere[i][j].toString());
                MainFourmiliere.index[i][j].setEditable(false);
                board.add(MainFourmiliere.index[i][j]);
            }
        MainFourmiliere.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MainFourmiliere.frame.getContentPane().add(board);
        MainFourmiliere.frame.setVisible(true);
    }

    public Case[][] generate(int size, int nbColonies, int rarete) {
        // Creation du tableau de la grille avec des Case vide

        fourmiliere = new Case[size][size];
        for (int y = 0; y < size; y++)
            for (int x = 0; x < size; x++) {
                fourmiliere[y][x] = new Case(x, y);
                Faction f = new Faction(0);
                f.setColor(new Color(0,0,0));
                fourmiliere[y][x].setFaction(f);
            }
        // Creation du des ressources de la grille
        for (int y = 0; y < size; y++)
            for (int x = 0; x < size; x++) {
                if (fourmiliere[y][x].getEmpty()) {
                    int rRare = (int) (Math.random() * 100);
                    if (rRare <= rarete) {
                        int rRessource = (int) (Math.random() * 2);
                        if (rRessource < 1) {
                            fourmiliere[y][x].setTypeRessource(INVENTAIRE.EAU.toString());
                        } else {
                            fourmiliere[y][x].setTypeRessource(INVENTAIRE.FRUITS.toString());
                        }

                    }
                }
            }

        // Insertion aléatoire des reines dans la grille
        int num = nbColonies;
        int cptId = 0;
        while (num > 0) {
            int x = (int) (Math.random() * size - 1);
            int y = (int) (Math.random() * size - 1);
            if (fourmiliere[y][x].getEmpty() && fourmiliere[y][x].noBorder(x, y , size)) {     // On génére les reine et leurs affecte déja l'id
                Faction f = new Faction(num);
                MainFourmiliere.listFourmis.add(new Reine(cptId, f, new int[]{x, y}));
                fourmiliere[y][x].setTypeFourmi("Reine");
                fourmiliere[y][x].setId(cptId);
                fourmiliere[y][x].setFaction(f);
                cptId++;
                num--;
            }
        }
        return fourmiliere;
    }

    public enum INVENTAIRE {
        EAU(1),
        FRUITS(2);
        final int value;
        INVENTAIRE(final int newValue) {
            value = newValue;
        }
    }

    public static void regenerate() {
        int size = fourmiliere.length;
        board.removeAll();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                MainFourmiliere.index[i][j] = new JTextField(1);
                MainFourmiliere.index[i][j].setForeground(fourmiliere[i][j].getFaction().getColor());
                MainFourmiliere.index[i][j].setText(fourmiliere[i][j].toString());
                MainFourmiliere.index[i][j].setEditable(false);
                board.add(MainFourmiliere.index[i][j]);
            }
        board.revalidate();
        board.repaint();
    }

    public static boolean isFreeCase(int y, int x) {
        try {
            if (MainFourmiliere.fourmiliere[y][x].getTypeFourmi() == null)
                return true;
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("une fourmi a essayer d'aller a la position :"+MainFourmiliere.fourmiliere[1] +" , "+MainFourmiliere.fourmiliere[0]+" sans succes car la case n'existe pas");

        }
        return false;
    }

    public static boolean isValidCase(int y, int x) {
        try {
            if (MainFourmiliere.fourmiliere[y][x] != null
                    && x < Start.size
                    && y < Start.size)
                return true;
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("une fourmi a essayer d'aller a la position :"+MainFourmiliere.fourmiliere[1] +" , "+MainFourmiliere.fourmiliere[0]+" sans succes car la case n'existe pas");
            return false;
        }
        return false;

    }
}
