package logic;

import ui.CaptureMatrix;
import ui.MainIU;
import ui.Welcome;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogicGetStartEnd implements ActionListener {
    private  boolean bStart = false, bEnd = false, coord;
    private int data[][];
    private Coordinate  start,end;
    private int i = 0;
    private CaptureMatrix captureMatrix;

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
            //Valida que se hayan ingresaron las coordedas de inicio y fin correctamente
            if (start != null && end != null){
                //Invierte los colores del background y el foreground
                changeColor();
                //Se obtiene las coordenadas apartir del valor x de start y end
                getCoordinates();
                //Imprime las coordenadas de inicio y fin
                printCoordinatesStartEnd();

                // Se ejecuta la interfaz principal MainUI: mandandole el array de botoens y de JtextFilds
                //en el LogicMainUi se le manda la interfaz principal, la coordenada de inicio, la coordenada de fin y los datos de la matriz
                LogicMainUi logicMainUi = new LogicMainUi(new MainIU(captureMatrix.getBtnsquares(), captureMatrix.getRows(), captureMatrix.getColumns()),
                        start,end,data);
                captureMatrix.dispose();
            }else {
                JOptionPane.showMessageDialog(null,"Selecciona un inicio y fin");
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
                        break;
                    }
                }else if (aux == end.getX()){
                    if (data[i][j] == 0){
                        end.setX(i);
                        end.setY(j);
                        coord = true;
                    }else{
                        JOptionPane.showMessageDialog(null,"Ingresa una posición de fin valida");
                        coord = false;//Se asigna un valor a coord indicando que no se selecciono una posicion valida
                        break;
                    }
                }
                aux++;
            }
        }
    }

    //Imprime las coordenadas
    private void printCoordinatesStartEnd(){
        System.out.println("Coordenadas de inicio: ["+start.getX()+"]["+start.getY()+"]");
        System.out.println("Coordenadas de fin:    ["+end.getX()+"]["+end.getY()+"]");
    }
}
