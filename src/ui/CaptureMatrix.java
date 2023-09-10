package ui;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.ArrayList;

public class CaptureMatrix extends JFrame{
    private JPanel contentPane, contentCenter,contentSouth, contentNorth, contentLeft, contentRight;
    private Font font = new Font("Cascadia Code",Font.BOLD,20);
    public ArrayList<JTextField> squares = new ArrayList<>();
    public ArrayList<JButton> btnsquares = new ArrayList<>();
    public JButton btnFill, btnConfirm, btnReset;
    public int rows, columns;

    public CaptureMatrix(int rows, int columns){
        super("Capture Matrix");
        contentPane = new JPanel(new BorderLayout());
        this.setContentPane(contentPane);
        this.setSize(500,570);
        this.rows = rows;
        this.columns = columns;

        //Se crean los componentes de la interfaz
        fillMatrix();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    // Muestra una matriz para que el usuario rellene los valores desde la interfaz
    public void fillMatrix(){
            //Se crean paneles con layout para agregar componentes visuales de la interfaz
            contentNorth = new JPanel(new FlowLayout());
            contentNorth.setBorder(BorderFactory.createEmptyBorder(15, 5, 0, 5));
            contentCenter = new JPanel(new GridLayout(rows,columns));
            contentCenter.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
            contentSouth = new JPanel(new FlowLayout());
            contentSouth.setBorder(BorderFactory.createEmptyBorder(0, 5, 15, 5));

            //Content North
            JLabel title  = new JLabel("Ingresa los valores de la matriz");
            title.setFont(font);
            title.setForeground(Color.ORANGE);
            contentNorth.add(title);

            //Content Center
            for (int i= 0; i<rows*columns;i++){
                JTextField a = new JTextField("");
                a.setBackground(Color.DARK_GRAY);
                a.setForeground(Color.ORANGE);
                a.setFont(font);
                a.setHorizontalAlignment(0);
                a.setSize(5,5);
                ((AbstractDocument) a.getDocument()).setDocumentFilter(new DocumentFilter() {
                    @Override
                    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                        if (fb.getDocument().getLength() + text.length() - length <= 1) {
                            super.replace(fb, offset, length, text, attrs);
                        }
                    }
                });
                contentCenter.add(a);
                squares.add(a);
            }
            //Content South
            btnFill = new JButton("Rellenar");
            btnFill.setBackground(Color.ORANGE);
            btnFill.setForeground(Color.BLACK);
            btnFill.setFont(font);
            contentSouth.add(btnFill);

            contentPane.add(contentNorth,BorderLayout.NORTH);
            contentPane.add(contentCenter,BorderLayout.CENTER);
            contentPane.add(contentSouth,BorderLayout.SOUTH);
    }

    //Muestra una matriz de botones que permite seleccionar el inicio y el fin
    public void getStartEnd(){
        contentNorth.removeAll();
        contentCenter.removeAll();
        contentSouth.removeAll();

        //Content North
        JLabel title  = new JLabel("Selecciona el inicio y el fin");
        title.setFont(font);
        title.setForeground(Color.ORANGE);
        contentNorth.add(title);

        //Content Center
        for (int i= 0; i<rows*columns;i++){
            JButton a = new JButton(squares.get(i).getText());
            a.setBackground(Color.DARK_GRAY);
            a.setForeground(Color.ORANGE);
            a.setFont(font);
            a.setHorizontalAlignment(0);
            a.setSize(5,5);
            contentCenter.add(a);
            btnsquares.add(a);
        }

        //Content South
        btnConfirm = new JButton("Confirmar");
        btnConfirm.setBackground(Color.ORANGE);
        btnConfirm.setForeground(Color.BLACK);
        btnConfirm.setFont(font);
        contentSouth.add(btnConfirm);

        btnReset = new JButton("Restablecer");
        btnReset.setBackground(Color.ORANGE);
        btnReset.setForeground(Color.BLACK);
        btnReset.setFont(font);
        contentSouth.add(btnReset);

        contentNorth.updateUI();
        contentCenter.updateUI();
        contentSouth.updateUI();
    }

}
