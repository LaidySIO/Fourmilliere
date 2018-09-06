package com.fourmilliere.ihm;

import com.fourmilliere.entities.Case;
import com.fourmilliere.entities.Faction;
import com.fourmilliere.entities.Reine;
import com.fourmilliere.main.MainFourmiliere;

import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.util.ArrayList;

import static com.fourmilliere.main.MainFourmiliere.*;

public class GUI {

    public GUI(int size, int nbColonies, int rarete) {
        // Init de la liste des fourmis
        listFourmis = new ArrayList<>();
        fourmiliereJTextField = new JTextField[size][size];
        MainFourmiliere.frame.setSize(500, 500);
        JPanel panel = new JPanel();
        board.setLayout(new GridLayout(size, size));
        fourmiliereCase = generate(size, nbColonies, rarete);

        // Première génération de la carte
        regenerate();

        MainFourmiliere.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        MainFourmiliere.frame.getContentPane().add(board);
        MainFourmiliere.frame.setVisible(true);
    }

    public Case[][] generate(int size, int nbColonies, int rarete) {
        // Creation du tableau de la grille avec des Case vide

        fourmiliereCase = new Case[size][size];
        for (int y = 0; y < size; y++)
            for (int x = 0; x < size; x++) {
                fourmiliereCase[y][x] = new Case(x, y);
                Faction f = new Faction(0);
                f.setColor(new Color(0,0,0));
                fourmiliereCase[y][x].setFaction(f);
            }
        // Creation du des ressources de la grille
        for (int y = 0; y < size; y++)
            for (int x = 0; x < size; x++) {
                if (fourmiliereCase[y][x].getEmpty()) {
                    int rRare = (int) (Math.random() * 100);
                    if (rRare <= rarete) {
                        int rRessource = (int) (Math.random() * 2);
                        if (rRessource < 1) {
                            fourmiliereCase[y][x].setTypeRessource(INVENTAIRE.EAU.toString());
                        } else {
                            fourmiliereCase[y][x].setTypeRessource(INVENTAIRE.FRUITS.toString());
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
            if (fourmiliereCase[y][x].getEmpty() && fourmiliereCase[y][x].noBorder(x, y , size)) {     // On génére les reine et leurs affecte déja l'id
                Faction f = new Faction(num);
                MainFourmiliere.listFourmis.add(new Reine(cptId, f, new int[]{x, y}));
                fourmiliereCase[y][x].setTypeFourmi("Reine");
                fourmiliereCase[y][x].setId(cptId);
                fourmiliereCase[y][x].setFaction(f);
                cptId++;
                num--;
            }
        }
        return fourmiliereCase;
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
        int size = fourmiliereCase.length;
        board.removeAll();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                fourmiliereJTextField[i][j] = new JTextField(1);
                if (fourmiliereCase[i][j].getTypeFourmi() != null) {
                    fourmiliereJTextField[i][j].setFont(new Font("Verdana",Font.BOLD,12));
                }
                fourmiliereJTextField[i][j].setForeground(fourmiliereCase[i][j].getFaction().getColor());
                fourmiliereJTextField[i][j].setText(fourmiliereCase[i][j].toString());
                fourmiliereJTextField[i][j].setHorizontalAlignment(JTextField.CENTER);
                fourmiliereJTextField[i][j].setEditable(false);
                board.add(fourmiliereJTextField[i][j]);
            }
        board.revalidate();
        board.repaint();
    }

    public static boolean isFreeCase(int y, int x) {
        try {
            if (MainFourmiliere.fourmiliereCase[y][x].getTypeFourmi() == null)
                return true;
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("une fourmi a essayer d'aller a la position :"+MainFourmiliere.fourmiliereCase[1] +" , "+MainFourmiliere.fourmiliereCase[0]+" sans succes car la case n'existe pas");

        }
        return false;
    }

    public static boolean isValidCase(int y, int x) {
        try {
            if (MainFourmiliere.fourmiliereCase[y][x] != null
                    && x < Start.size
                    && y < Start.size)
                return true;
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("une fourmi a essayer d'aller a la position :"+MainFourmiliere.fourmiliereCase[1] +" , "+MainFourmiliere.fourmiliereCase[0]+" sans succes car la case n'existe pas");
            return false;
        }
        return false;

    }
}
