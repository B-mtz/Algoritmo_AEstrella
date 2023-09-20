package ui;

import logic.Coordinate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainIU extends JFrame implements Runnable{
    private JPanel contentPane,contentNorth,contentCenter,contentSouth,contentLeft,contentRight, contentMatrix;
    private int rows,columns;
    private JButton btnNewMatrix,btnResetContent;
    private Font font = new Font("Cascadia Code",Font.BOLD,20), font1 = new Font("Cascadia Code",Font.BOLD,16);
    private JTable openSet, closedSet;
    private ArrayList<JButton> btnsquares;
    private ArrayList<JTextField> squares;
    private ArrayList<Coordinate> routes = new ArrayList<>();
    //Constructor
    public MainIU(ArrayList<JButton> btnsquares,ArrayList<JTextField> squares, int rows, int columns){
        contentPane = new JPanel(new BorderLayout());
        this.setContentPane(contentPane);
        this.setSize(1280,550);
        this.rows = rows;
        this.columns = columns;
        this.btnsquares = btnsquares;
        this.squares = squares;

        contentNorth = new JPanel();
        contentNorth.setBorder(BorderFactory.createEmptyBorder(2, 5, 0, 5));
        contentCenter = new JPanel(new BorderLayout());
        contentCenter.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        contentSouth = new JPanel();
        contentSouth.setBorder(BorderFactory.createEmptyBorder(0, 5, 20, 5));;
        contentLeft = new JPanel(new BorderLayout());
        contentLeft.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 5));
        contentRight = new JPanel(new BorderLayout());
        contentRight.setBorder(BorderFactory.createEmptyBorder(0, 5, 15, 15));

        //Se crean los componentes para los paneles de cada lado del contentPane BorderLayout
        componentsPanelNorth();
        componentsPanelCenter();
        componentsPanelSouth();
        componentsPanelLefth();
        componentsPanelRight();

        // se añaden los paneles al contentPane dentro del frame
        contentPane.add(contentNorth,BorderLayout.NORTH);
        contentPane.add(contentCenter,BorderLayout.CENTER);
        contentPane.add(contentSouth, BorderLayout.SOUTH);
        contentPane.add(contentLeft, BorderLayout.WEST);
        contentPane.add(contentRight, BorderLayout.EAST);

        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //Crea los componentes del panel Norte
    private void componentsPanelNorth(){
        //se agrega titulo
        JLabel title  = new JLabel("Algoritmo A*");
        title.setFont(new Font("Cascadia Code",Font.BOLD,30));
        title.setForeground(Color.WHITE);
        contentNorth.add(title);
    }
    //Crea los componentes del panel Centro
    private void componentsPanelCenter(){
        //Content Center : se agrega subtitulo
        JLabel subTitle = new JLabel("Recorrido");
        subTitle.setFont(font);
        subTitle.setForeground(Color.ORANGE);
        subTitle.setHorizontalAlignment(0);
        contentCenter.add(subTitle,BorderLayout.NORTH);

        //Content Center: se crean componentes Jbutton con un  GridLayout para simular la matriz
        contentMatrix = new JPanel(new GridLayout(rows,columns));
        for(JButton button : btnsquares){
            contentMatrix.add(button);
        }
        contentCenter.add(contentMatrix,BorderLayout.CENTER);
    }
    //Crea los componentes del panel Sur
    private void componentsPanelSouth(){
        // Se añade los botoenes de accion
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
    }
    //Crea los componentes del panel Izquierdo
    private void componentsPanelLefth(){
        //se crea el titulo del contenido izquierdo
        JPanel topContentLeft = new JPanel(new FlowLayout());
        JLabel lbOpenSet  = new JLabel("OpenSet");
        lbOpenSet.setFont(font);
        lbOpenSet.setForeground(Color.ORANGE);
        topContentLeft.add(lbOpenSet);
        //Se crea la tabla que contendra el openSet, ubicado en el panel izquierdo
        JPanel centerContentLeft = new JPanel(new FlowLayout());
        openSet = new JTable();
        JScrollPane scrollOpenSet = new JScrollPane(openSet);
        centerContentLeft.add(scrollOpenSet);
        contentLeft.add(topContentLeft,BorderLayout.NORTH);
        contentLeft.add(centerContentLeft,BorderLayout.CENTER);
    }
    //Crea los componentes del panel Derecho
    private void componentsPanelRight(){
        //Se crea el titulo del contenido izuqierdo
        JPanel topContentRigth = new JPanel(new FlowLayout());
        JLabel lbCloseSet  = new JLabel("ClosedSet");
        lbCloseSet.setFont(font);
        lbCloseSet.setForeground(Color.ORANGE);
        topContentRigth.add(lbCloseSet);
        //Se crea la tabla que contendra el closedSet, ubicado en el panel derecho
        JPanel centerContentRight = new JPanel(new FlowLayout());
        closedSet = new JTable();
        DefaultTableModel closeSetModel = new DefaultTableModel();
        closedSet.setModel(closeSetModel);
        JScrollPane scrollCloseSet = new JScrollPane(closedSet);
        centerContentRight.add(scrollCloseSet);
        contentRight.add(topContentRigth, BorderLayout.NORTH);
        contentRight.add(centerContentRight, BorderLayout.CENTER);
    }
    //Pasa el color de fondo al color del texto del inicio y fin
    public void changeColor(Coordinate paintStartEnd ){
        btnsquares.get(paintStartEnd.getX()).setBackground(Color.DARK_GRAY);
        btnsquares.get(paintStartEnd.getX()).setForeground(Color.GREEN);
        btnsquares.get(paintStartEnd.getY()).setBackground(Color.DARK_GRAY);
        btnsquares.get(paintStartEnd.getY()).setForeground(Color.RED);
    }

    //Genera una animación de la ruta
    public  void animation(){
        System.out.println("Animacion");
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

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public JButton getBtnNewMatrix() {
        return btnNewMatrix;
    }

    public JButton getBtnResetContent() {
        return btnResetContent;
    }

    public JTable getOpenSet() {
        return openSet;
    }

    public JTable getClosedSet() {
        return closedSet;
    }

    public ArrayList<JTextField> getSquares() {
        return squares;
    }
}
