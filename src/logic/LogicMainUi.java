package logic;

import ui.CaptureMatrix;
import ui.MainIU;
import ui.Welcome;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LogicMainUi implements ActionListener {
    private ArrayList<Node> openSet = new ArrayList<>(),closeSet = new ArrayList<>();
    private ArrayList<Coordinate> routes = new ArrayList<>();
    private Coordinate  start,end;
    private MainIU mainIU;
    private int data[][];
    public LogicMainUi(MainIU mainIU,Coordinate start,Coordinate end, int[][] data, Coordinate paintStartEnd){
        this.start = start;
        this.end = end;
        this.data = data;
        this.mainIU = mainIU;
        mainIU.getBtnNewMatrix().addActionListener(this);
        mainIU.getBtnResetContent().addActionListener(this);
        mainIU.changeColor(paintStartEnd);
        executeAlgorithm();
    }

    public void executeAlgorithm(){
        //Ejecutamos el algoritmo y recuperamos los valores de openSet, closedSet y las rutas
        LogicExecuteAlgorithm logicExecuteAlgorithm = new LogicExecuteAlgorithm(start, end, data,mainIU.getRows(),mainIU.getColumns());
        logicExecuteAlgorithm.executeAlgorithm();
        openSet = logicExecuteAlgorithm.getOpenSetRegister();
        closeSet = logicExecuteAlgorithm.getCloseSet();
        routes = logicExecuteAlgorithm.getRoutes();
        //Rellenamos las tablas de openSet y closedSet
        fillTableOpenSet();
        fillTableCloseSet();
        mainIU.setRoutes(routes);
        //Si finish == true, existe una ruta
        if (logicExecuteAlgorithm.isFinish()){
            mainIU.animation();
            mainIU.repaint();
        }else{
            JOptionPane.showMessageDialog(null,"No se pudo encontrar una ruta");
        }
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
        mainIU.getOpenSet().setModel(openSetModel);
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
        mainIU.getClosedSet().setModel(closeSetModel);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //Se vuelve a llamar a la ventana welcome para recuperar el tamaño del arreglo y se cierra esta ventana
        if (e.getSource().equals(mainIU.getBtnNewMatrix())){
            mainIU.dispose();
            Welcome welcome = new Welcome();
            LogicWelcome logicWelcome = new LogicWelcome(welcome);
        }else if (e.getSource().equals(mainIU.getBtnResetContent())){
            mainIU.dispose();
            CaptureMatrix captureMatrix = new CaptureMatrix(mainIU.getRows(),mainIU.getColumns());
            captureMatrix.setSquares(mainIU.getSquares());
            LogicCaptureMatrix logicCaptureMatrix = new LogicCaptureMatrix(captureMatrix);
        }
    }
}
