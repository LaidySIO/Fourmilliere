package com.fourmilliere.ihm;

import com.company.Test;

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

public class IhmFourmilliere extends JFrame {

    private static IhmFourmilliere INSTANCE = null;
    JFrame frame;


    public static synchronized IhmFourmilliere getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IhmFourmilliere();
        }
        return INSTANCE;
    }

    public IhmFourmilliere() {
    start();

    }

    private void start() {
        int size = 0;
        JTextField taille = new JTextField(5);
        JTextField colonies = new JTextField(5);
        JTextField rarete = new JTextField(5);

        JPanel myPanel = new JPanel();
        //myPanel.add(new JLabel("Nombre de case :"));
        myPanel.add(new JLabel("Nombre de case :"));
        myPanel.add(taille);


        //myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

        //myPanel.add(new JLabel("Nombre de colonies :"));
        //myPanel.add(colonies);

        //myPanel.add(new JLabel("% de ressources :"));
        //myPanel.add(rarete);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Parametres du jeu ", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            size = Integer.parseInt(taille.getText());
            System.out.println("Nombre de lignes : " + taille.getText());
            //System.out.println("Nombre de colonies : " + colonies.getText());
            //System.out.println("Rareté des ressources : " + rarete.getText());

            //new Grids("Test", (80*size), (80*size), size, size).setVisible(true);
            SudokuGrid s = new SudokuGrid(size);
        }
    }


}

class GridsCanvas extends Canvas {
    int width, height;

    int rows;

    int cols;

    GridsCanvas(int w, int h, int r, int c) {
        setSize(width = w, height = h);
        rows = r;
        cols = c;
    }

    public void paint(Graphics g) {
        int i;
        width = getSize().width;
        height = getSize().height;

        // draw the rows
        int rowHt = height / (rows);
        for (i = 0; i < rows; i++)
            g.drawLine(0, i * rowHt, width, i * rowHt);

        // draw the columns
        int rowWid = width / (cols);
        for (i = 0; i < cols; i++)
            g.drawLine(i * rowWid, 0, i * rowWid, height);
    }
}

/** This is the demo class. */

 class Grids extends Frame {
    /*
     * Construct a GfxDemo2 given its title, width and height. Uses a
     * GridBagLayout to make the Canvas resize properly.
     */
    Grids(String title, int w, int h, int rows, int cols) {
        setTitle(title);

        // Now create a Canvas and add it to the Frame.
        GridsCanvas xyz = new GridsCanvas(w, h, rows, cols);
        add(xyz);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
                dispose();
                System.exit(0);
            }
        });

        // Normal end ... pack it up!
        pack();
    }
}

final class SudokuGrid extends JPanel {
    private final JFrame frame = new JFrame("My Test");
    private SudokuGrid grid2;
    private static final Font FONT = new Font("Verdana",
            Font.CENTER_BASELINE,
            20);

    private final JTextField[][] grid;
    private final Map<JTextField, Point> mapFieldToCoordinates =
            new HashMap<>();

    private final int dimension;
    private final JPanel gridPanel;
    private final JPanel buttonPanel;
    private final JButton solveButton;
    private final JButton clearButton;
    private final JPanel[][] minisquarePanels;

    SudokuGrid(int dimension) {
        this.grid = new JTextField[dimension][dimension];
        this.dimension = dimension;

        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                JTextField field = new JTextField();
                mapFieldToCoordinates.put(field, new Point(x, y));
                grid[y][x] = field;
            }
        }

        this.gridPanel   = new JPanel();
        this.buttonPanel = new JPanel();

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        Dimension fieldDimension = new Dimension(30, 30);

        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                JTextField field = grid[y][x];
                field.setBorder(border);
                field.setFont(FONT);
                field.setPreferredSize(fieldDimension);
            }
        }

        int minisquareDimension = (int) Math.sqrt(dimension);
        this.gridPanel.setLayout(new GridLayout(minisquareDimension,
                minisquareDimension));

        this.minisquarePanels = new JPanel[minisquareDimension]
                [minisquareDimension];

        Border minisquareBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int y = 0; y < minisquareDimension; ++y) {
            for (int x = 0; x < minisquareDimension; ++x) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(minisquareDimension,
                        minisquareDimension));
                panel.setBorder(minisquareBorder);
                minisquarePanels[y][x] = panel;
                gridPanel.add(panel);
            }
        }

        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                int minisquareX = x / minisquareDimension;
                int minisquareY = y / minisquareDimension;

                minisquarePanels[minisquareY][minisquareX].add(grid[y][x]);
            }
        }

        this.gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,
                2));
        this.clearButton = new JButton("Clear");
        this.solveButton = new JButton("Solve");

        this.buttonPanel.setLayout(new BorderLayout());
        this.buttonPanel.add(clearButton, BorderLayout.WEST);
        this.buttonPanel.add(solveButton, BorderLayout.EAST);

        this.setLayout(new BorderLayout());
        this.add(gridPanel,   BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);

        clearButton.addActionListener((ActionEvent e) -> {
            clearAll();
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(grid2 = new SudokuGrid(4));
        frame.pack();
        centerView();
        frame.setResizable(false);
        frame.setVisible(true);

    }

    private void centerView() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();

        frame.setLocation((screen.width - frameSize.width) >> 1,
                (screen.height - frameSize.height) >> 1);
    }

    public void openFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            clearAll();

            int cells = dimension * dimension;
            int y = 0;
            int x = 0;

            while (cells > 0) {
                if (scanner.hasNext()) {
                    String text = scanner.next();

                    try {
                        int number = Integer.parseInt(text);

                        if (number > 0 && number <= dimension) {
                            grid[y][x].setText(" " + number);
                        }
                    } catch (NumberFormatException ex) {

                    }

                    ++x;

                    if (x == dimension) {
                        x = 0;
                        ++y;
                    }
                } else {
                    break;
                }

                --cells;
            }
        } catch (FileNotFoundException ex) {

        }
    }

    private void addSpace(JTextField field) {
        if (field.getText().isEmpty()) {
            field.setText(" ");
        }
    }

    void moveCursor(JTextField field, char c) {
        Point coordinates = mapFieldToCoordinates.get(field);
        field.setBackground(Color.WHITE);

        switch (c) {
            case 'w':
            case 'W':

                if (coordinates.y > 0) {
                    grid[coordinates.y - 1][coordinates.x].requestFocus();
                    addSpace(grid[coordinates.y - 1][coordinates.x]);
                }

                break;

            case 'd':
            case 'D':

                if (coordinates.x < dimension - 1) {
                    grid[coordinates.y][coordinates.x + 1].requestFocus();
                    addSpace(grid[coordinates.y][coordinates.x + 1]);
                }

                break;

            case 's':
            case 'S':

                if (coordinates.y < dimension - 1) {
                    grid[coordinates.y + 1][coordinates.x].requestFocus();
                    addSpace(grid[coordinates.y + 1][coordinates.x]);
                }

                break;

            case 'a':
            case 'A':

                if (coordinates.x > 0) {
                    grid[coordinates.y][coordinates.x - 1].requestFocus();
                    addSpace(grid[coordinates.y][coordinates.x - 1]);
                }

                break;
        }
    }

    void clearAll() {
        for (JTextField[] row : grid) {
            for (JTextField field : row) {
                field.setText("");
            }
        }
    }


}


