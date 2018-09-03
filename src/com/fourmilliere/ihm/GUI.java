package com.fourmilliere.ihm;

import com.fourmilliere.entities.Case;
import com.fourmilliere.entities.Faction;
import com.fourmilliere.entities.Fourmi;
import com.fourmilliere.entities.Reine;
import com.fourmilliere.main.MainFourmilliere;

import java.awt.*;
import javax.swing.*;
import java.lang.*;
import java.util.ArrayList;

import static com.fourmilliere.main.MainFourmilliere.temp;

public class GUI {

    private static String[][] array;
    private static Case[][] sudoku;
    private INVENTAIRE food;
    public ArrayList<Fourmi> listFourmis = new ArrayList();


    public GUI(int size, int nbColonies, int rarete) {
        //frame.getContentPane().add(draw);
        MainFourmilliere.index = new JTextField[size][size];
        MainFourmilliere.frame.setSize(500, 500);
        JPanel panel = new JPanel();
        MainFourmilliere.board.setLayout(new GridLayout(size, size));
        sudoku = generate(size, nbColonies, rarete);
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                MainFourmilliere.index[i][j] = new JTextField(1);
                MainFourmilliere.index[i][j].setForeground(sudoku[i][j].getFaction().getColor());
                if (sudoku[i][j].toString().equals("Reine"))
                    MainFourmilliere.index[i][j].setFont(new Font("Verdana",Font.BOLD,13));
                MainFourmilliere.index[i][j].setText(sudoku[i][j].toString());
                MainFourmilliere.index[i][j].setEditable(false);
                MainFourmilliere.board.add(MainFourmilliere.index[i][j]);
            }
        MainFourmilliere.frame.getContentPane().add(MainFourmilliere.board);
        MainFourmilliere.frame.setVisible(true);
    }

    public Case[][] generate(int size, int nbColonies, int rarete) {
        // Creation du tableau de la grille avec des Case vide

        temp = new Case[size][size];
        for (int y = 0; y < size; y++)
            for (int x = 0; x < size; x++) {
                temp[y][x] = new Case(x, y);
                Faction f = new Faction(0);
                f.setColor(new Color(0,0,0));
                temp[y][x].setFaction(f);
            }
        // Creation du des ressources de la grille
        for (int y = 0; y < size; y++)
            for (int x = 0; x < size; x++) {
                if (temp[y][x].getEmpty()) {
                    int rRare = (int) (Math.random() * 100);
                    if (rRare <= rarete) {
                        int rRessource = (int) (Math.random() * 2);
                        if (rRessource < 1) {
                            temp[y][x].setTypeRessource(INVENTAIRE.EAU.toString());
                        } else {
                            temp[y][x].setTypeRessource(INVENTAIRE.FRUITS.toString());
                        }

                    }
                }
            }

        // Insertion aléatoire des reines dans la grille
        int num = nbColonies;
        while (num > 0) {
            int x = (int) (Math.random() * size);
            int y = (int) (Math.random() * size);
            if (temp[y][x].getEmpty() && temp[y][x].noBorder(x, y , size)) {                    // On génére les reine et leurs affecte déja l'id
                Faction f = new Faction(num);
                MainFourmilliere.listFourmis.add(new Reine(f, new int[]{x, y}));
                temp[y][x].setTypeFourmi("Reine");
                temp[y][x].setId(num);
                temp[y][x].setFaction(f);
                num--;
            }
            System.out.println( "testSiCaseslibre :" +temp[y][x].getTypeFourmi());

        }

        for (Fourmi f : MainFourmilliere.listFourmis) {
            System.out.println(f.toString());
        }
        return temp;
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
        int size = temp.length -1;
        MainFourmilliere.board.removeAll();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                MainFourmilliere.index[i][j] = new JTextField(1);
                MainFourmilliere.index[i][j].setForeground(temp[i][j].getFaction().getColor());
                MainFourmilliere.index[i][j].setText(temp[i][j].toString());
                MainFourmilliere.index[i][j].setEditable(false);
                MainFourmilliere.board.add(MainFourmilliere.index[i][j]);
            }
        MainFourmilliere.board.revalidate();
        MainFourmilliere.board.repaint();

    }

    public static boolean isFreeCase(int y, int x) {
        try {
            if (MainFourmilliere.temp[y][x].getTypeFourmi() == null
                    && MainFourmilliere.temp[y][x].getTypeRessource() == null)
                return true;
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("une fourmi a essayer d'aller a la position :"+MainFourmilliere.temp[1] +" , "+MainFourmilliere.temp[0]+" sans succes car la case n'existe pas");

        }
        return false;
    }

    public static boolean isValidCase(int y, int x) {
        try {
            if (MainFourmilliere.temp[y][x] != null)
                return true;
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("une fourmi a essayer d'aller a la position :"+MainFourmilliere.temp[1] +" , "+MainFourmilliere.temp[0]+" sans succes car la case n'existe pas");
            return false;
        }
        return false;

    }
}
