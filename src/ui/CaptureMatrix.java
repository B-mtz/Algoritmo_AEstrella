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

public class CaptureMatrix extends JFrame implements Runnable{
    private JPanel contentPane, contentMatrix,contentCenter,contentSouth, contentNorth, contentLeft, contentRight;
    private Font font = new Font("Cascadia Code",Font.BOLD,20), font1 = new Font("Cascadia Code",Font.BOLD,16);
    private ArrayList<JTextField> squares = new ArrayList<>();
    private ArrayList<JButton> btnsquares = new ArrayList<>();
    private ArrayList<Coordinate> routes = new ArrayList<>();
    private JButton btnFill, btnConfirm, btnResetSelection, btnNewMatrix, btnResetContent;
    private int rows, columns;
    private JTable openSet, closedSet;

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

        //Content South: se añaden dos botones de accion
        btnConfirm = new JButton("Confirmar");
        btnConfirm.setBackground(Color.ORANGE);
        btnConfirm.setForeground(Color.BLACK);
        btnConfirm.setFont(font);
        contentSouth.add(btnConfirm);

        btnResetSelection = new JButton("Restablecer");
        btnResetSelection.setBackground(Color.ORANGE);
        btnResetSelection.setForeground(Color.BLACK);
        btnResetSelection.setFont(font);
        contentSouth.add(btnResetSelection);

        //Se actualizan los paneles
        contentNorth.updateUI();
        contentCenter.updateUI();
        contentSouth.updateUI();
    }

    //Se crean los componentes que mostraran el OpenSet, ClosedSet y el recorrido de la ruta
    public void executeAlgoritm(){
        //Se redimenciona el tamaño del frame y se vacia el contenido de los paneles, tambien se añaden bordes
        this.setSize(1280,550);
        contentNorth.removeAll();
        contentCenter.removeAll();
        contentSouth.removeAll();
        contentLeft = new JPanel(new BorderLayout());
        contentLeft.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 5));
        contentRight = new JPanel(new BorderLayout());
        contentRight.setBorder(BorderFactory.createEmptyBorder(0, 5, 15, 15));

        //Content North : se agrega titulo
        JLabel title  = new JLabel("Algoritmo A*");
        title.setFont(new Font("Cascadia Code",Font.BOLD,30));
        title.setForeground(Color.WHITE);
        contentNorth.add(title);

        //Content Center : se agrega subtitulo
        JLabel subTitle = new JLabel("Recorrido");
        subTitle.setFont(font);
        subTitle.setForeground(Color.ORANGE);
        subTitle.setHorizontalAlignment(0);
        contentCenter.add(subTitle,BorderLayout.NORTH);
        contentCenter.add(contentMatrix,BorderLayout.CENTER);

        //Content South: Se añade un boton de accion
        btnNewMatrix = new JButton("Nueva Matrix");
        btnNewMatrix.setBackground(Color.ORANGE);
        btnNewMatrix.setForeground(Color.BLACK);
        btnNewMatrix.setFont(font1);
        btnResetContent = new JButton("Restablecer");
        btnResetContent.setBackground(Color.ORANGE);
        btnResetContent.setForeground(Color.BLACK);
        btnResetContent.setFont(font1);
        contentSouth.add(btnResetContent);
        contentSouth.add(btnNewMatrix);

        //Content Left: se crea el titulo del contenido izquierdo
        JPanel topContentLeft = new JPanel(new FlowLayout());
        JLabel lbOpenSet  = new JLabel("OpenSet");
        lbOpenSet.setFont(font);
        lbOpenSet.setForeground(Color.ORANGE);
        topContentLeft.add(lbOpenSet);
        //Se crea la tabla que contendra el openSet, ubicado en el centro del panel izquierdo
        JPanel centerContentLeft = new JPanel(new FlowLayout());
        openSet = new JTable();
        JScrollPane scrollOpenSet = new JScrollPane(openSet);
        centerContentLeft.add(scrollOpenSet);
        contentLeft.add(topContentLeft,BorderLayout.NORTH);
        contentLeft.add(centerContentLeft,BorderLayout.CENTER);

        //Content Right: se crea el titulo del contenido izuqierdo
        JPanel topContentRigth = new JPanel(new FlowLayout());
        JLabel lbCloseSet  = new JLabel("ClosedSet");
        lbCloseSet.setFont(font);
        lbCloseSet.setForeground(Color.ORANGE);
        topContentRigth.add(lbCloseSet);
        //Se crea la tabla que contendra el closedSet, ubicado en el centro del panel derecho
        JPanel centerContentRight = new JPanel(new FlowLayout());
        closedSet = new JTable();
        DefaultTableModel closeSetModel = new DefaultTableModel();
        closedSet.setModel(closeSetModel);
        JScrollPane scrollCloseSet = new JScrollPane(closedSet);
        centerContentRight.add(scrollCloseSet);
        contentRight.add(topContentRigth, BorderLayout.NORTH);
        contentRight.add(centerContentRight, BorderLayout.CENTER);

        //Se actualizan el contenido de los paneles
        contentNorth.updateUI();
        contentSouth.updateUI();
        contentCenter.updateUI();
        // se añaden nuevos paneles: izquierdo y derecho
        contentPane.add(contentLeft, BorderLayout.WEST);
        contentPane.add(contentRight, BorderLayout.EAST);
        //se establece la ubicación del centro para el contenido.
        this.setLocationRelativeTo(null);
    }

    //Genera una animación de la ruta
    public  void animation(){
        Thread thread = new Thread(this);
        thread.start();
    }
    //se sobreEscribe el metodo run para pintar los botones simulando el recorrido de la ruta
    @Override
    public void run() {
        //Se recorre la ruta recuperrando sus coordenadas
        for (int i = routes.size()-1; i>=0; i--){
            // Obtener las coordenadas x, y de la ruta
            int x = routes.get(i).getX();
            int y = routes.get(i).getY();

            // Calcular el índice en el ArrayList de botones
            int indice = x * columns + y;

            // Verificar si el índice está dentro del rango válido
            if (indice >= 0 && indice < btnsquares.size()) {
                // Obtener el botón correspondiente al índice
                JButton boton = btnsquares.get(indice);

                // Cambiar el color de fondo del botón
                boton.setBackground(Color.WHITE); // Cambia el color como desees
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void setRoutes(ArrayList<Coordinate> routes) {
        this.routes = routes;
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

    public JButton getBtnNewMatrix() {
        return btnNewMatrix;
    }

    public JTable getOpenSet() {
        return openSet;
    }

    public JTable getClosedSet() {
        return closedSet;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}