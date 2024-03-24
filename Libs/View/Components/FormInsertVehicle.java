package Libs.View.Components;

import Libs.Driver;
import Libs.View.MainView;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormInsertVehicle extends JFrame{

    /*
    * Formulário para pegar os dados de condições de existencia de um determinado veiculo.
    * */

    public JPanel panelMain;
    private JButton submit;
    private JButton cancel;
    private JLabel travessia;
    private JLabel permanenceia;
    private JTextField tempo_travessia;
    private JTextField tempo_permanencia;
    private Character origin_side_vehicle;


    public FormInsertVehicle( char originSideVehicle ){
        setTitle("Inserindo um novo veiculo");
        setSize(500, 500);
        setLocationRelativeTo(null);

        this.origin_side_vehicle = originSideVehicle;

        // submit the values to create a vehicle
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Driver driver = new Driver(
                        originSideVehicle, "1", Integer.getInteger(travessia.getText()), Integer.getInteger(permanenceia.getText()), MainView.getInformationPanel()
                );
                driver.start(); // start the thread
                cancel.doClick();
            }
        });

        // cancel operation
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window[] windows = Window.getWindows();
                for(Window janela_aberta : windows){
                    if( janela_aberta instanceof FormInsertVehicle ){
                        janela_aberta.dispose();
                        break; // fechando apenas uma janela do meu formulario
                    }
                }
            }
        });
    }


}
