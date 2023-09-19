package logic;

import ui.CaptureMatrix;
import ui.Welcome;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogicWelcome implements ActionListener {
    private  Welcome welcome;
    private  int rows,columns;

    //Constructor
    public LogicWelcome(Welcome welcome){
        this.welcome = welcome;
        this.welcome.getCreate().addActionListener(this);
    }

    //Evento del boton
    @Override
    public void actionPerformed(ActionEvent e) {
        //ejecuta la siguiente pantalla al obtener las filas y columnas
        if (e.getSource().equals(welcome.getCreate())){
            //Se verifica que no exista excepción al capturar un dato de los campos de texto
            try {
                //Se verifica que no esten vacillos loc campos de texto
                if (welcome.getTxtRows().getText().isEmpty() || welcome.getTxtColumns().getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Rellena todos los campos");
                }else {
                    //Se capturan en numero de filas y columnas
                    rows = Integer.parseInt(welcome.getTxtRows().getText());
                    columns = Integer.parseInt(welcome.getTxtColumns().getText());

                    if (rows != 1 && columns !=1){
                        //Ejecuta la interfaz para capturar datos de la matriz
                        CaptureMatrix captureMatrix = new CaptureMatrix(rows,columns);
                        LogicCaptureMatrix logicCaptureMatrix = new LogicCaptureMatrix(captureMatrix);
                        welcome.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null,"La matriz minima es de 2x2");
                    }
                }
            }catch (Exception exception ){
                JOptionPane.showMessageDialog(null,"Solo se permiten numeros: "+exception);
            }
        }
    }
}
