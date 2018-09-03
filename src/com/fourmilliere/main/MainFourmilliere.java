package com.fourmilliere.main;

import com.fourmilliere.entities.Case;
import com.fourmilliere.entities.Fourmi;
import com.fourmilliere.entities.Guerriere;
import com.fourmilliere.entities.Ouvriere;
import com.fourmilliere.ihm.GUI;
import com.fourmilliere.ihm.IhmFourmilliere;
import com.fourmilliere.ihm.Start;

import javax.swing.*;
import java.util.ArrayList;

public class MainFourmilliere {
    public static Case[][] temp;

    // Fenetre pour toute la durée du jeu
    public static JFrame frame = new JFrame("Fourmilière");
    public static JPanel board = new JPanel();
    // Cases du jeu
    public static JTextField[][] index;
    // Liste des fourmis pour toute la durée du jeu
    public static ArrayList<Fourmi> listFourmis = new ArrayList<>();;



    public static void main(String[] args) {

        //IhmFourmilliere i = new IhmFourmilliere();
        System.out.println("Start");
        new Start();

    }

}
