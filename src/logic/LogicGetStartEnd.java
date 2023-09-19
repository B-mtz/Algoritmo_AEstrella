package logic;

import ui.CaptureMatrix;
import ui.Welcome;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ColorConvertOp;
import java.util.ArrayList;

public class LogicGetStartEnd implements ActionListener {
    private  boolean bStart = false, bEnd = false, coord;
    private int data[][];
    private ArrayList<Node> openSet = new ArrayList<>(),closeSet = new ArrayList<>();
    private ArrayList<Coordinate> routes = new ArrayList<>();
    private Coordinate  start,end;
    private int i = 0;
    CaptureMatrix captureMatrix;
    public LogicGetStartEnd(CaptureMatrix captureMatrix, int data[][]){
        this.captureMatrix = captureMatrix;
        this.data = data;
        captureMatrix.btnConfirm.addActionListener(this);
        captureMatrix.btnReset.addActionListener(this);
        captureAction();
    }

    //Asigna las acciones a los botones y recupera el indice del incio y fin
    public void captureAction(){
        while (i < captureMatrix.btnsquares.size()){
            int c = i;
            captureMatrix.btnsquares.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //si bStart = false o bEnd = false, cambia el color del boton y recupera el indice y lo guarda en x
                    if (!bStart){
                        captureMatrix.btnsquares.get(c).setBackground(Color.GREEN);
                        captureMatrix.btnsquares.get(c).setForeground(Color.BLACK);
                        start = new Coordinate(c,0);
                        bStart = true;
                    }else if (!bEnd){
                        captureMatrix.btnsquares.get(c).setBackground(Color.red);
                        captureMatrix.btnsquares.get(c).setForeground(Color.WHITE);
                        end = new Coordinate(c,0);
                        bEnd = true;
                    }
                }
            });
            i++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Al confirmar las coordenadas seleccionadas se cambia el color del texto en los botones con el indice guardado
        if (e.getSource().equals(captureMatrix.btnConfirm)){
            try {
                changeColor();
                //Se obtiene las coordenadas apartir del valor x de start y end
                getCoordinates();
            }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Selecciona un inicio y fin");
            }

            if (coord == true){
                captureMatrix.executeAlgoritm();
                captureMatrix.btnNewMatrix.addActionListener(this);

                //Ejecutamos el algoritmo y obtenemos los valores
                LogicExecuteAlgorithm logicExecuteAlgorithm = new LogicExecuteAlgorithm(start, end, data,captureMatrix.rows,captureMatrix.columns);
                logicExecuteAlgorithm.executeAlgorithm();
                openSet = logicExecuteAlgorithm.getOpenSetRegister();
                closeSet = logicExecuteAlgorithm.getCloseSet();
                routes = logicExecuteAlgorithm.getRoutes();
                fillTableOpenSet();
                fillTableCloseSet();
                captureMatrix.setRoutes(routes);
                if (logicExecuteAlgorithm.finish){
                    captureMatrix.animation();
                }else{
                    JOptionPane.showMessageDialog(null,"No se pudo encontrar una ruta corta");
                }
            }
        }//Se restablecen los colores y valores de bStart y bEnd que representan el inicio y fin
        else if (e.getSource().equals(captureMatrix.btnReset)){
            resetBackground();
            bStart = false;
            bEnd = false;
        }else if (e.getSource().equals(captureMatrix.btnNewMatrix)){
            System.out.println("Pasa por aqui");
            captureMatrix.dispose();
            Welcome welcome = new Welcome();
            LogicWelcome logicWelcome = new LogicWelcome(welcome);
        }
    }

    //Restablece los colores
    private void resetBackground(){
        if (bStart && bEnd){
            for (int i = 0; i<captureMatrix.btnsquares.size();i++){
                captureMatrix.btnsquares.get(i).setBackground(Color.DARK_GRAY);
                captureMatrix.btnsquares.get(i).setForeground(Color.ORANGE);
            }
        }
    }
    //Invierte los colores del inicio y fin
    private void changeColor(){
        captureMatrix.btnsquares.get(start.getX()).setBackground(Color.DARK_GRAY);
        captureMatrix.btnsquares.get(start.getX()).setForeground(Color.GREEN);
        captureMatrix.btnsquares.get(end.getX()).setBackground(Color.DARK_GRAY);
        captureMatrix.btnsquares.get(end.getX()).setForeground(Color.RED);
    }

    //Se obtiene las coordendas, apartir de x de start y end
    private void getCoordinates(){
        //comprueba con el aux el indice del inicio y fin para obtener las coordenadas
        int aux =0;
        //recorre el arreglo
        for (int i = 0; i < captureMatrix.rows; i++) {
            for (int j = 0; j < captureMatrix.columns; j++) {
                //Comprueba si el indice guardado coincide con aux, para guardar la coordenada en que se encuentra
                if (aux == start.getX()){
                    if (data[i][j] == 0){
                        start.setX(i);
                        start.setY(j);
                    }else{
                        JOptionPane.showMessageDialog(null,"Ingresa una posición de inicio valida");
                        coord = false;
                    }
                }else if (aux == end.getX()){
                    if (data[i][j] == 0){
                        end.setX(i);
                        end.setY(j);
                        coord = true;
                        printCoordinatesStartEnd();
                    }else{
                        JOptionPane.showMessageDialog(null,"Ingresa una posición de fin valida");
                        coord = false;
                    }
                }
                aux++;
            }
        }
    }

    //Se asignan los valores al las tablas del panel
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
        captureMatrix.openSet.setModel(openSetModel);
    }
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
        captureMatrix.closSet.setModel(closeSetModel);
    }

    //Imprime las coordenadas
    private void printCoordinatesStartEnd(){
        System.out.println("Coordenadas de inicio: ["+start.getX()+"]["+start.getY()+"]");
        System.out.println("Coordenadas de fin:    ["+end.getX()+"]["+end.getY()+"]");
    }
}
