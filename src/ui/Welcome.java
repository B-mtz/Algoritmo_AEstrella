package ui;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class Welcome extends JFrame{
    private JPanel contentPane, contentCenter;
    private JButton create;
    private JTextField txtRows, txtColumns;
    private Font font = new Font("Cascadia Code",Font.BOLD,20);
    //Constructor
    public Welcome(){
        super("Welcome");
        this.setSize(450, 200);
        contentPane = new JPanel(new BorderLayout());
        this.setContentPane(contentPane);

        //Se llama al metodo para crear los componentes de la interfaz
        getRowsColumns();

        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //Se crean componentes para capturar las filas y columnas
    private void getRowsColumns(){
        //Se inicializa el panel del contenido
        contentCenter = new JPanel();
        contentCenter.setLayout(null);
        //Se crean la  etiqueta que acompañan al JTextField Filas
        JLabel lbRows = new JLabel("Filas: ");
        lbRows.setFont(font);
        lbRows.setBounds(70,50,100,25);
        //Se crea el JTextField
        txtRows  = new JTextField();
        txtRows.setFont(font);
        txtRows.setForeground(Color.ORANGE);
        txtRows.setHorizontalAlignment(0);
        txtRows.setBounds(140,49,50,25);
        //Se limitan los caracteres del JTextfield txtRows a 1
        ((AbstractDocument) txtRows.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (fb.getDocument().getLength() + text.length() - length <= 1) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        //Se crean la  etiqueta que acompañan al JTextField Columnas
        JLabel lbColumns = new JLabel("  Columnas: ");
        lbColumns.setFont(font);
        lbColumns.setBounds(190,50,150,25);
        //Se crea el JTextField
        txtColumns = new JTextField();
        txtColumns.setFont(font);
        txtColumns.setForeground(Color.ORANGE);
        txtColumns.setHorizontalAlignment(0);
        txtColumns.setBounds(320,49,50,25);
        //Se limitan los caracteres del JTextfield txtColumns a 1
        ((AbstractDocument) txtColumns.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (fb.getDocument().getLength() + text.length() - length <= 1) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        //Se crea el boton para crear la matriz
        create = new JButton("Create");
        create.setFont(font);
        create.setBackground(Color.ORANGE);
        create.setForeground(Color.BLACK);
        create.setBounds(170,110,110,30);

        //Se añaden los componentes al panel contentCenter
        contentCenter.add(lbRows);
        contentCenter.add(txtRows);
        contentCenter.add(lbColumns);
        contentCenter.add(txtColumns);
        contentCenter.add(create);
        //Se añade el panel ContentCenter al contentPane del Frame
        contentPane.add(contentCenter);
    }

    public JButton getCreate() {
        return create;
    }

    public JTextField getTxtRows() {
        return txtRows;
    }

    public JTextField getTxtColumns() {
        return txtColumns;
    }
}
