package logic;

import ui.CaptureMatrix;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogicCaptureMatrix implements ActionListener {
    private CaptureMatrix captureMatrix;
    private int data[][];
    //Constructor
    public LogicCaptureMatrix(CaptureMatrix captureMatrix){
        this.captureMatrix = captureMatrix;
        this.captureMatrix.getBtnFill().addActionListener(this);
        //Se le asigna el tamaño a la matriz de datos
        data = new int[captureMatrix.getRows()][captureMatrix.getColumns()];
    }

    //Eventos de botoenes
    @Override
    public void actionPerformed(ActionEvent e) {
        //Efectua la accion para btnFill
        if (e.getSource().equals(captureMatrix.getBtnFill())){
            //Si el metodo retorna true, los datos ingresados son correctos
            if (validateData()){
                //Imprime los valores ingresados en los JTexField contenidos en el ArrayList Squares
                printSquares();
                //Rellena la matriz data con los valores de los JTexfFields contenidos en el ArrayList Squares
                fillMatrix();
                //Cambia los componentes dentro de los paneles para que se pueda seleccionar el inicio y fin
                captureMatrix.getStartEnd();
                //Llama a la clase logica para obtener el inicio y el fin
                LogicGetStartEnd logicGetStartEnd = new LogicGetStartEnd(captureMatrix, data);
            }
        }
    }

    //Valida que los datos ingresados sean 1s, 0s o que no este vacio
    public boolean validateData(){
        int  movementSpace = 0;
        boolean flag= false;
        //Compruba que no exista excepción al parsear el valor ingresado a int
        try {
            //Recorre el ArrayList Square
            for (int i= 0; i<captureMatrix.getRows()*captureMatrix.getColumns();i++){
                //Verifica que no exista campos vacios
                if (captureMatrix.getSquares().get(i).getText().isEmpty()){
                    flag = false;
                    JOptionPane.showMessageDialog(null,"Rellena todos los campos");
                    break;
                }else {
                    //Recupera el valor convirtiendolo en int
                    int aux = Integer.parseInt(captureMatrix.getSquares().get(i).getText());
                    //Comprueba que solo se ingresen 1s o 0s
                    if (aux == 1 || aux == 0){
                        //valida cuantos espacios hay para moverse en la matriz
                        if (aux == 0){
                            movementSpace++;
                        }
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
            JOptionPane.showMessageDialog(null,"Solo se permiten numeros: ");
        }
        //Valida que existan mas de 2 espacios para moverse en la matriz
        if (movementSpace >2){
            flag = true;
        }else {
            JOptionPane.showMessageDialog(null,"Ingresa al menos 3 0s");
            flag = false;
        }
        return flag;
    }
    //Rellena la matriz data con los datos del ArrayList Squares y los imprime en consola
    public void fillMatrix(){
        int count= 0;
        System.out.println("\nMatriz rellenada: ");
        for (int i = 0; i< captureMatrix.getRows(); i++){
            for (int j = 0; j<captureMatrix.getColumns(); j++){
                data[i][j] = Integer.parseInt(captureMatrix.getSquares().get(count).getText());
                count++;
                System.out.print("["+data[i][j]+"]");
            }
            System.out.println(" ");
        }
    }

    //Imprime los datos de los JTextField contenidos en el ArrayList Squares
    public void printSquares(){
        System.out.println("\nValores del arrayList Squares: ");
        for (int i= 0; i<captureMatrix.getRows()*captureMatrix.getColumns();i++){
            System.out.print("["+captureMatrix.getSquares().get(i).getText()+"]");
        }
        System.out.println(" ");
    }
}
