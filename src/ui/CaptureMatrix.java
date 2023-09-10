package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CaptureMatrix extends JFrame{
    private JPanel contentPane, contentCenter,contentSouth, contentNorth;
    private Font font = new Font("Cascadia Code",Font.BOLD,20);
    private ArrayList<JTextField> squares = new ArrayList<>();
    public JButton btnFill;
    private int rows, columns;

    public CaptureMatrix(int rows, int columns){
        super("Capture Matrix");
        contentPane = new JPanel(new BorderLayout());
        this.setContentPane(contentPane);
        this.setSize(500,570);

        this.rows = rows;
        this.columns = columns;

        fillMatrix();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void fillMatrix(){
            contentNorth = new JPanel(new FlowLayout());
            contentNorth.setBorder(BorderFactory.createEmptyBorder(15, 5, 0, 5));
            contentCenter = new JPanel(new GridLayout(rows,columns));
            contentCenter.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
            //int x = 300/rows;
            //int y = 350/columns;
            //contentCenter.setBorder(BorderFactory.createEmptyBorder(x, y, x, y));
            contentSouth = new JPanel(new FlowLayout());
            contentSouth.setBorder(BorderFactory.createEmptyBorder(0, 5, 15, 5));

            JLabel title  = new JLabel("Ingresa los valores de la matriz");
            title.setFont(font);
            title.setForeground(Color.ORANGE);
            contentNorth.add(title);

            for (int i= 0; i<rows*columns;i++){
                JTextField a = new JTextField("");
                a.setBackground(Color.DARK_GRAY);
                a.setForeground(Color.ORANGE);
                a.setFont(font);
                a.setHorizontalAlignment(0);
                a.setSize(5,5);
                contentCenter.add(a);
                squares.add(a);
            }

            btnFill = new JButton("Rellenar");
            btnFill.setBackground(Color.ORANGE);
            btnFill.setForeground(Color.BLACK);
            btnFill.setFont(font);
            contentSouth.add(btnFill);

            contentPane.add(contentNorth,BorderLayout.NORTH);
            contentPane.add(contentCenter,BorderLayout.CENTER);
            contentPane.add(contentSouth,BorderLayout.SOUTH);
    }
}
