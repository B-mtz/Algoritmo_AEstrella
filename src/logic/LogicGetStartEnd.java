package logic;

import ui.CaptureMatrix;
import ui.Welcome;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LogicGetStartEnd implements ActionListener {
    private  boolean bStart = false, bEnd = false, coord;
    private int data[][];
    private ArrayList<Node> openSet = new ArrayList<>(),closeSet = new ArrayList<>();
    private ArrayList<Coordinate> routes = new ArrayList<>();
    private Coordinate  start,end;
    private int i = 0;
    CaptureMatrix captureMatrix;

    //Constructor
    public LogicGetStartEnd(CaptureMatrix captureMatrix, int data[][]){
        this.captureMatrix = captureMatrix;
        this.data = data;
        captureMatrix.getBtnConfirm().addActionListener(this);
        captureMatrix.getBtnResetSelection().addActionListener(this);
        captureAction();
    }

    //Asigna las acciones a los botones y recupera el indice del incio y fin
    public void captureAction(){
        //Recorre btnSquares que contiene los botoenes y añade el evento
        while (i < captureMatrix.getBtnsquares().size()){
            int c = i;
            captureMatrix.getBtnsquares().get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //si bStart = false o bEnd = false, cambia el color del boton y recupera el indice y lo guarda en la coordenada x
                    if (!bStart){
                        captureMatrix.getBtnsquares().get(c).setBackground(Color.GREEN);
                        captureMatrix.getBtnsquares().get(c).setForeground(Color.BLACK);
                        start = new Coordinate(c,0);
                        bStart = true;
                    }else if (!bEnd){
                        captureMatrix.getBtnsquares().get(c).setBackground(Color.red);
                        captureMatrix.getBtnsquares().get(c).setForeground(Color.WHITE);
                        end = new Coordinate(c,0);
                        bEnd = true;
                    }
                }
            });
            i++;
        }
    }

    //Se asignan eventos a los botoenes
    @Override
    public void actionPerformed(ActionEvent e) {
        //Al confirmar las coordenadas seleccionadas se cambia el color del texto en los botones con el indice guardado
        if (e.getSource().equals(captureMatrix.getBtnConfirm())){
            try {
                changeColor();
                //Se obtiene las coordenadas apartir del valor x de start y end
                getCoordinates();
            }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Selecciona un inicio y fin");
            }
            //Si coord es verdad, se seleccionaron las posiciones correctamente
            if (coord == true){
                // se ejcuta el cambio del contenido del panel: para mostrar openSet,closedSet y el recorrido de la ruta
                captureMatrix.executeAlgoritm();
                captureMatrix.getBtnNewMatrix().addActionListener(this);

                //Ejecutamos el algoritmo y recuperamos los valores de openSet, closedSet y las rutas
                LogicExecuteAlgorithm logicExecuteAlgorithm = new LogicExecuteAlgorithm(start, end, data,captureMatrix.getRows(),captureMatrix.getColumns());
                logicExecuteAlgorithm.executeAlgorithm();
                openSet = logicExecuteAlgorithm.getOpenSetRegister();
                closeSet = logicExecuteAlgorithm.getCloseSet();
                routes = logicExecuteAlgorithm.getRoutes();
                //Rellenamos las tablas de openSet y closedSet
                fillTableOpenSet();
                fillTableCloseSet();
                captureMatrix.setRoutes(routes);
                //Si finish == true, existe una ruta
                if (logicExecuteAlgorithm.isFinish()){
                    captureMatrix.animation();
                }else{
                    JOptionPane.showMessageDialog(null,"No se pudo encontrar una ruta");
                }
            }
        }//Se restablecen los colores y valores de bStart y bEnd que representan la seleccion de inicio y fin
        else if (e.getSource().equals(captureMatrix.getBtnResetSelection())){
            resetBackground();
            bStart = false;
            bEnd = false;
        }//Se vuelve a llamar a la ventana para recuperar el tamaño del arreglo y se cierra esta ventana
        else if (e.getSource().equals(captureMatrix.getBtnNewMatrix())){
            captureMatrix.dispose();
            Welcome welcome = new Welcome();
            LogicWelcome logicWelcome = new LogicWelcome(welcome);
        }
    }

    //Restablece los colores
    private void resetBackground(){
        for (int i = 0; i<captureMatrix.getBtnsquares().size();i++){
            captureMatrix.getBtnsquares().get(i).setBackground(Color.DARK_GRAY);
            captureMatrix.getBtnsquares().get(i).setForeground(Color.ORANGE);
        }
    }
    //Pasa el color de fondo al color del texto del inicio y fin
    private void changeColor(){
        captureMatrix.getBtnsquares().get(start.getX()).setBackground(Color.DARK_GRAY);
        captureMatrix.getBtnsquares().get(start.getX()).setForeground(Color.GREEN);
        captureMatrix.getBtnsquares().get(end.getX()).setBackground(Color.DARK_GRAY);
        captureMatrix.getBtnsquares().get(end.getX()).setForeground(Color.RED);
    }

    //Se obtiene las coordendas de inicio y fin , apartir de x de start y end
    private void getCoordinates(){
        //comprueba con el aux el indice del inicio y fin para obtener las coordenadas
        int aux =0;
        //recorre el arreglo simulando un Matriz
        for (int i = 0; i < captureMatrix.getRows(); i++) {
            for (int j = 0; j < captureMatrix.getColumns(); j++) {
                //Comprueba si el indice guardado coincide con aux, para guardar la coordenada en que se encuentra
                if (aux == start.getX()){
                    if (data[i][j] == 0){
                        start.setX(i);
                        start.setY(j);
                    }else{
                        JOptionPane.showMessageDialog(null,"Ingresa una posición de inicio valida");
                        coord = false;//Se asigna un valor a coord indicando que no se selecciono una posicion valida
                    }
                }else if (aux == end.getX()){
                    if (data[i][j] == 0){
                        end.setX(i);
                        end.setY(j);
                        coord = true;
                    }else{
                        JOptionPane.showMessageDialog(null,"Ingresa una posición de fin valida");
                        coord = false;//Se asigna un valor a coord indicando que no se selecciono una posicion valida
                    }
                }
                aux++;
            }
        }
        //Imprime las coordenadas de inicio y fin
        printCoordinatesStartEnd();
    }

    //Se asignan los valores al la tabla OpenSet del panel izquierdo
    public void fillTableOpenSet(){
        DefaultTableModel openSetModel = new DefaultTableModel();
        String[] titles ={"Coordenada","Costo","Heuristica","Total","Origen"};
        openSetModel.setColumnIdentifiers(titles);
        String[][] contentMatrix = new String[openSet.size()][5];
        for (int i = 0; i < openSet.size(); i++) {
            Node node = openSet.get(i);
            contentMatrix[i][0] = node.getCoordinate().retunCoordinate();
            contentMatrix[i][1] = String.valueOf(node.getCoste());
            contentMatrix[i][2] = String.valueOf(node.getHeuristic());
            contentMatrix[i][3] = String.valueOf(node.getTotal());
            contentMatrix[i][4] = node.getOrigin().retunCoordinate();
        }
        openSetModel.setDataVector(contentMatrix,titles);
        captureMatrix.getOpenSet().setModel(openSetModel);
    }
    //Se asignan los valores al la tabla ClosedSet del panel derecho
    public void fillTableCloseSet(){
        DefaultTableModel closeSetModel = new DefaultTableModel();
        String[] titles ={"Coordenada","Costo","Heuristica","Total","Origen"};
        closeSetModel.setColumnIdentifiers(titles);
        String[][] contentMatrix = new String[closeSet.size()][5];
        for (int i = 0; i < closeSet.size(); i++) {
            Node node = closeSet.get(i);
            contentMatrix[i][0] = node.getCoordinate().retunCoordinate();
            contentMatrix[i][1] = String.valueOf(node.getCoste());
            contentMatrix[i][2] = String.valueOf(node.getHeuristic());
            contentMatrix[i][3] = String.valueOf(node.getTotal());
            contentMatrix[i][4] = node.getOrigin().retunCoordinate();
        }
        closeSetModel.setDataVector(contentMatrix,titles);
        captureMatrix.getClosedSet().setModel(closeSetModel);
    }

    //Imprime las coordenadas
    private void printCoordinatesStartEnd(){
        System.out.println("Coordenadas de inicio: ["+start.getX()+"]["+start.getY()+"]");
        System.out.println("Coordenadas de fin:    ["+end.getX()+"]["+end.getY()+"]");
    }
}
