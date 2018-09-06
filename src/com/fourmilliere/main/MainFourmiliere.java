package com.fourmilliere.main;

import com.fourmilliere.entities.Case;
import com.fourmilliere.entities.Fourmi;
import com.fourmilliere.ihm.Start;

import javax.swing.*;
import java.util.ArrayList;

public class MainFourmiliere {
    public static Case[][] fourmiliereCase;

    // Fenetre du jeu
    public static JFrame frame = new JFrame("Fourmilière");
    // Grille du jeu
    public static JPanel board = new JPanel();
    // Cases du jeu
    public static JTextField[][] fourmiliereJTextField;
    // Liste des fourmis
    public static ArrayList<Fourmi> listFourmis = new ArrayList<>();
    // Boolean fin de la simulation ?
    public static boolean gameOver = false;

    public static void main(String[] args) {

        System.out.println("Start");
        Start.getInstance();
    }

}
