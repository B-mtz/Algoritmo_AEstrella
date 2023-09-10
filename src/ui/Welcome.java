package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Welcome extends JFrame{
    JPanel contentPane, contentCenter,contentSouth;
    JButton create;
    JTextField txtRows, txtColumns;
    int columns,rows;
    private Font font = new Font("Cascadia Code",Font.BOLD,20);
    public Welcome(){
        super("Welcome");
        this.setSize(450, 200);
        contentPane = new JPanel(new BorderLayout());
        this.setContentPane(contentPane);

        getRowsColumns();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void getRowsColumns(){
        contentCenter = new JPanel();
        contentCenter.setLayout(null);

        JLabel lbRows = new JLabel("Filas: ");
        lbRows.setFont(font);
        lbRows.setBounds(70,50,100,25);

        txtRows  = new JTextField();
        txtRows.setFont(font);
        txtRows.setForeground(Color.ORANGE);
        txtRows.setHorizontalAlignment(0);
        txtRows.setBounds(140,49,50,25);

        JLabel lbColumns = new JLabel("  Columnas: ");
        lbColumns.setFont(font);
        lbColumns.setBounds(190,50,150,25);

        txtColumns = new JTextField();
        txtColumns.setFont(font);
        txtColumns.setForeground(Color.ORANGE);
        txtColumns.setHorizontalAlignment(0);
        txtColumns.setBounds(320,49,50,25);

        create = new JButton("Create");
        create.setFont(font);
        create.setBackground(Color.ORANGE);
        create.setForeground(Color.BLACK);
        create.setBounds(170,110,110,30);

        contentCenter.add(lbRows);
        contentCenter.add(txtRows);
        contentCenter.add(lbColumns);
        contentCenter.add(txtColumns);
        contentCenter.add(create);
        contentPane.add(contentCenter);
    }
}
