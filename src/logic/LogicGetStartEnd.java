package logic;

import ui.CaptureMatrix;
import ui.MainIU;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogicGetStartEnd implements ActionListener {
    private  boolean bStart = false, bEnd = false;
    private int data[][];
    private Coordinate  start,end, paintStartEnd;
    private int i = 0;
    private CaptureMatrix captureMatrix;
    private  MainIU mainIU;

    //Constructor
    public LogicGetStartEnd(CaptureMatrix captureMatrix, int data[][]){
        this.captureMatrix = captureMatrix;
        this.data = data;
        this.paintStartEnd = new Coordinate(0,0);
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
            if (start!= null && end != null){
                //Valida que se hayan ingresaron las coordedas de inicio y fin correctamente
                if (getCoordinates()){
                    //Imprime las coordenadas de inicio y fin
                    printCoordinatesStartEnd();
                    // Se ejecuta la interfaz principal MainUI: mandandole el array de botoens y de JtextFilds
                    mainIU = new MainIU(captureMatrix.getBtnsquares(), captureMatrix.getSquares(),captureMatrix.getRows(), captureMatrix.getColumns());
                    //en el LogicMainUi se le manda la interfaz principal, la coordenada de inicio, la coordenada de fin y los datos de la matriz
                    LogicMainUi logicMainUi = new LogicMainUi( mainIU,start,end,data,paintStartEnd);
                    //cambia los colores de inicio y fin
                    captureMatrix.dispose();
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ingresa una pocicion de inicio y fin");
            }
        }//Se restablecen los colores y valores de bStart y bEnd que representan la seleccion de inicio y fin
        else if (e.getSource().equals(captureMatrix.getBtnResetSelection())){
            resetBackground();
            bStart = false;
            bEnd = false;
        }
    }

    //Restablece los colores
    private void resetBackground(){
        for (int i = 0; i<captureMatrix.getBtnsquares().size();i++){
            captureMatrix.getBtnsquares().get(i).setBackground(Color.DARK_GRAY);
            captureMatrix.getBtnsquares().get(i).setForeground(Color.ORANGE);
        }
        start = null;
        end = null;
    }

    //Se obtiene las coordendas de inicio y fin , apartir de x de start y end
    private boolean getCoordinates(){
        //comprueba con el aux el indice del inicio y fin para obtener las coordenadas
        int aux =0;
        boolean  s= false,e= false, correct = false;
        //recorre el arreglo simulando un Matriz
        for (i = 0; i < captureMatrix.getRows(); i++) {
            for (int j = 0; j < captureMatrix.getColumns(); j++) {
                //Comprueba si el indice guardado coincide con aux, para guardar la coordenada en que se encuentra
                if (aux == start.getX()){
                    if (data[i][j] == 0){
                        //Guarda la posición del boton de inicio en X de paintStarEnd
                        paintStartEnd.setX(start.getX());
                        start.setX(i);
                        start.setY(j);
                        s = true;
                    }
                }else if (aux == end.getX()){
                    if (data[i][j] == 0){
                        //Guarda la posición del boton de fin en Y de paintStarEnd
                        paintStartEnd.setY(end.getX());
                        end.setX(i);
                        end.setY(j);
                        e = true;
                    }
                }
                if (s && e){
                    correct =true;
                    break;
                }
                aux++;
            }
        }
        if (!s){
            JOptionPane.showMessageDialog(null,"Ingresa una posicion de incio valida");
        }
        if (!e){
            JOptionPane.showMessageDialog(null,"Ingresa una posicion de fin valida");
        }

        return correct;
    }

    //Imprime las coordenadas
    private void printCoordinatesStartEnd(){
        System.out.println("Coordenadas de inicio: ["+start.getX()+"]["+start.getY()+"]");
        System.out.println("Coordenadas de fin:    ["+end.getX()+"]["+end.getY()+"]");
    }
}
