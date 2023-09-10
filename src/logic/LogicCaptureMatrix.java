package logic;

import ui.CaptureMatrix;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogicCaptureMatrix implements ActionListener {
    CaptureMatrix captureMatrix;
    int data[][];
    private int rows, columns;
    public LogicCaptureMatrix(CaptureMatrix captureMatrix){
        this.captureMatrix = captureMatrix;
        this.captureMatrix.btnFill.addActionListener(this);
        data = new int[captureMatrix.rows][captureMatrix.columns];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(captureMatrix.btnFill)){
            if (validateData()){
                printSquares();
                fillMatrix();
            }
        }
    }

    //Valida que los datos ingresados sean 1s, 0s o que no este vacio
    public boolean validateData(){
        boolean flag= false;
        try {
            for (int i= 0; i<captureMatrix.rows*captureMatrix.columns;i++){
                if (captureMatrix.squares.get(i).getText().isEmpty()){
                    flag = false;
                    JOptionPane.showMessageDialog(null,"Rellena todos los campos");
                    break;
                }else {
                    int aux = Integer.parseInt(captureMatrix.squares.get(i).getText());
                    if (aux == 1 || aux == 0){
                        flag = true;
                    }else{
                        flag = false;
                        JOptionPane.showMessageDialog(null,"Solo se permiten 0s o 1s");
                        break;
                    }
                }
            }
        }catch (Exception exception){
            flag = false;
            JOptionPane.showMessageDialog(null,"Solo se permiten numeros: "+exception);
        }
        return flag;
    }
    public void fillMatrix(){
        int count= 0;
        System.out.println("Matriz rellenada: ");
        for (int i = 0; i< captureMatrix.rows; i++){
            for (int j = 0; j<captureMatrix.columns; j++){
                data[i][j] = Integer.parseInt(captureMatrix.squares.get(count).getText());
                count++;
                System.out.print("["+data[i][j]+"]");
            }
            System.out.println(" ");
        }
    }
    public void printSquares(){
        System.out.println("Valores del arrayList Squares: ");
        for (int i= 0; i<captureMatrix.rows*captureMatrix.columns;i++){
            System.out.print("["+captureMatrix.squares.get(i).getText()+"]");
        }
        System.out.println(" ");
    }
}
