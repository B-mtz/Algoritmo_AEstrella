package logic;

import ui.CaptureMatrix;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogicGetStartEnd implements ActionListener {
    private  boolean bStart = false, bEnd = false;
    private int data[][];

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
            changeColor();
            //Se obtiene las coordenadas apartir del valor x de start y end
            getCoordinates();
        }//Se restablecen los colores y valores de bStart y bEnd que representan el inicio y fin
        else if (e.getSource().equals(captureMatrix.btnReset)){
            resetBackground();
            bStart = false;
            bEnd = false;
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
                    start.setX(i);
                    start.setY(j);
                }else if (aux == end.getX()){
                    end.setX(i);
                    end.setY(j);
                }
                aux++;
            }
        }
        printCoordinatesStartEnd();
    }

    //Imprime las coordenadas
    private void printCoordinatesStartEnd(){
        System.out.println("Coordenadas de inicio: ["+start.getX()+"]["+start.getY()+"]");
        System.out.println("Coordenadas de fin:    ["+end.getX()+"]["+end.getY()+"]");
    }
}
