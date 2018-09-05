package com.fourmilliere.ihm;

import com.fourmilliere.entities.Case;
import com.fourmilliere.entities.Faction;
import com.fourmilliere.entities.Reine;
import com.fourmilliere.main.MainFourmiliere;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.lang.*;
import java.util.ArrayList;

import static com.fourmilliere.main.MainFourmiliere.board;
import static com.fourmilliere.main.MainFourmiliere.fourmiliereCase;
import static com.fourmilliere.main.MainFourmiliere.listFourmis;

public class GUI {

    public GUI(int size, int nbColonies, int rarete) {
        // Init de la liste des fourmis
        listFourmis = new ArrayList<>();
        MainFourmiliere.fourmiliereJLabel = new JLabel[size][size];
        MainFourmiliere.frame.setSize(500, 500);
        board.setLayout(new GridLayout(size, size));
        fourmiliereCase = generate(size, nbColonies, rarete);

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
                            fourmiliereCase[y][x].setIcon(new ImageIcon(getClass().getResource("/com/fourmilliere/Files/water.png")));
                        } else {
                            fourmiliereCase[y][x].setTypeRessource(INVENTAIRE.FRUITS.toString());
                            fourmiliereCase[y][x].setIcon(new ImageIcon(getClass().getResource("/com/fourmilliere/Files/food.png")));
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
                listFourmis.add(new Reine(cptId, f, new int[]{x, y}));
                fourmiliereCase[y][x].setTypeFourmi("Reine");
                fourmiliereCase[y][x].setId(cptId);
                fourmiliereCase[y][x].setFaction(f);
                fourmiliereCase[y][x].setIcon(listFourmis.get(cptId).getIcon());

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
        // create a line border with the specified color and width
        Border border;
        // set the border of this component

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                MainFourmiliere.fourmiliereJLabel[i][j] = new JLabel();
                if (fourmiliereCase[i][j].getIcon() != null) {
                    ImageIcon icon = fourmiliereCase[i][j].getIcon();
                    Image image = icon.getImage();
                    Image newimg = image.getScaledInstance(MainFourmiliere.frame.getWidth() / size, MainFourmiliere.frame.getHeight() /size,  java.awt.Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newimg);

                    MainFourmiliere.fourmiliereJLabel[i][j].setIcon(icon);
                }
                border = BorderFactory.createLineBorder(fourmiliereCase[i][j].getFaction().getColor(), 1);
                MainFourmiliere.fourmiliereJLabel[i][j].setBorder(border);
                board.add(MainFourmiliere.fourmiliereJLabel[i][j]);
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
