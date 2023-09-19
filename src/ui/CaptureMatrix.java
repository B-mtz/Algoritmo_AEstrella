package ui;

import logic.Coordinate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.ArrayList;

public class CaptureMatrix extends JFrame{
    private JPanel contentPane, contentMatrix,contentCenter,contentSouth, contentNorth;
    private Font font = new Font("Cascadia Code",Font.BOLD,20);
    private ArrayList<JTextField> squares = new ArrayList<>();
    private ArrayList<JButton> btnsquares = new ArrayList<>();
    private JButton btnFill, btnConfirm, btnResetSelection;
    private int rows, columns;

    //Constructor
    public CaptureMatrix(int rows, int columns){
        contentPane = new JPanel(new BorderLayout());
        this.setContentPane(contentPane);
        this.setSize(500,570);
        this.rows = rows;
        this.columns = columns;

        //Se crean los componentes de la interfaz
        fillMatrix();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    // Se crean los componentes para recibir los datos de la matriz
    public void fillMatrix(){
            //Se crean paneles que ocuparan un espacio en el contentPane que usa un FlowLayout
            contentNorth = new JPanel(new FlowLayout());
            contentNorth.setBorder(BorderFactory.createEmptyBorder(2, 5, 0, 5));
            contentCenter = new JPanel(new BorderLayout());
            contentCenter.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
            contentSouth = new JPanel(new FlowLayout());
            contentSouth.setBorder(BorderFactory.createEmptyBorder(0, 5, 20, 5));

            //Content North : se crea el titulo
            JLabel title  = new JLabel("Ingresa los valores de la matriz");
            title.setFont(font);
            title.setForeground(Color.ORANGE);
            contentNorth.add(title);

            //Content Center : se crean componentes JtextField usando un GridLayout para simular la matriz
            contentMatrix = new JPanel(new GridLayout(rows,columns));
            for (int i= 0; i<rows*columns;i++){
                JTextField a = new JTextField("");
                a.setBackground(Color.DARK_GRAY);
                a.setForeground(Color.ORANGE);
                a.setFont(font);
                a.setHorizontalAlignment(0);
                a.setSize(5,5);
                //Caca JTextField solo permite 1 numero
                ((AbstractDocument) a.getDocument()).setDocumentFilter(new DocumentFilter() {
                    @Override
                    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                        if (fb.getDocument().getLength() + text.length() - length <= 1) {
                            super.replace(fb, offset, length, text, attrs);
                        }
                    }
                });
                contentMatrix.add(a);
                squares.add(a);
            }
            contentCenter.add(contentMatrix, BorderLayout.CENTER);

            //Content South: se crea el boton para los cambios
            btnFill = new JButton("Rellenar");
            btnFill.setBackground(Color.ORANGE);
            btnFill.setForeground(Color.BLACK);
            btnFill.setFont(font);
            contentSouth.add(btnFill);

            contentPane.add(contentNorth,BorderLayout.NORTH);
            contentPane.add(contentCenter,BorderLayout.CENTER);
            contentPane.add(contentSouth,BorderLayout.SOUTH);
    }

    //Se crean los componentes para recuperar el inicio y el fin
    public void getStartEnd(){
        //Se vacia los panel para agregar nuevo contenido
        contentNorth.removeAll();
        contentCenter.removeAll();
        contentMatrix.removeAll();
        contentSouth.removeAll();

        //Content North: se agrega nuevo titulo
        JLabel title  = new JLabel("Selecciona el inicio y el fin");
        title.setFont(font);
        title.setForeground(Color.ORANGE);
        contentNorth.add(title);

        //Content Center: se crean componentes Jbutton con el mismo layout GridLayout para simular la matriz
        for (int i= 0; i<rows*columns;i++){
            JButton a = new JButton(squares.get(i).getText());
            a.setBackground(Color.DARK_GRAY);
            a.setForeground(Color.ORANGE);
            a.setFont(font);
            a.setHorizontalAlignment(0);
            a.setSize(5,5);
            contentMatrix.add(a);
            btnsquares.add(a);
        }
        contentCenter.add(contentMatrix, BorderLayout.CENTER);

        btnResetSelection = new JButton("Restablecer");
        btnResetSelection.setBackground(Color.ORANGE);
        btnResetSelection.setForeground(Color.BLACK);
        btnResetSelection.setFont(font);
        contentSouth.add(btnResetSelection);

        //Content South: se añaden dos botones de accion
        btnConfirm = new JButton("Confirmar");
        btnConfirm.setBackground(Color.ORANGE);
        btnConfirm.setForeground(Color.BLACK);
        btnConfirm.setFont(font);
        contentSouth.add(btnConfirm);

        //Se actualizan los paneles
        contentNorth.updateUI();
        contentCenter.updateUI();
        contentSouth.updateUI();
    }


    public ArrayList<JTextField> getSquares() {
        return squares;
    }

    public ArrayList<JButton> getBtnsquares() {
        return btnsquares;
    }

    public JButton getBtnFill() {
        return btnFill;
    }

    public JButton getBtnConfirm() {
        return btnConfirm;
    }

    public JButton getBtnResetSelection() {
        return btnResetSelection;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

}