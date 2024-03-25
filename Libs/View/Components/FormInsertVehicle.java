package Libs.View.Components;

import Libs.Driver;
import Libs.View.MainView;

import java.awt.*;
import javax.swing.*;


public class FormInsertVehicle extends JPanel {

    /*
     * Formulário para pegar os dados de condições de existencia de um determinado veiculo.
     * */

    Integer driverIdentifier = 0;

    public FormInsertVehicle(int maxComponentSize){

        setPreferredSize(new Dimension(maxComponentSize, 150));

        this.setAlignmentY(0);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton insertLeft = new JButton("Insert Left");
        JButton insertRigth = new JButton("Insert Rigth");
        JLabel travessiaLabel = new JLabel("Tempo de Travessia:");
        JLabel permanenciaLabel = new JLabel("Tempo de Permanência:");
        JTextField tempo_travessia =  new JTextField();
        JTextField tempo_permanencia = new JTextField();

        insertLeft.setMaximumSize(new Dimension(maxComponentSize, 75));
        insertRigth.setMaximumSize(new Dimension(maxComponentSize, 75));

        JPanel ButtonsBox = new JPanel();
        ButtonsBox.setAlignmentX(0);
        ButtonsBox.add(insertLeft);
        ButtonsBox.add(insertRigth);
        ButtonsBox.setVisible(true);

        this.add(travessiaLabel);
        this.add(tempo_travessia);
        this.add(permanenciaLabel);
        this.add(tempo_permanencia);
        this.add(ButtonsBox);

        insertLeft.addActionListener(e -> {
            Driver driver = createDriver('L', tempo_travessia.getText(), tempo_permanencia.getText());
            this.clearState(tempo_permanencia, tempo_travessia);
            driver.start(); // Inicia a thread do motorista
        });

        insertRigth.addActionListener(e -> {
            Driver driver = createDriver('R', tempo_travessia.getText(), tempo_permanencia.getText());
            this.clearState(tempo_permanencia, tempo_travessia);
            driver.start(); // inicia a thread do motorista
        });
    }

    private Driver createDriver(char originSideVehicle, String tempo_travessia, String tempo_permanencia){
        driverIdentifier++; // increment identifier
        return new Driver(
                originSideVehicle,
                this.driverIdentifier.toString(),
                Integer.parseInt(tempo_travessia),
                Integer.parseInt(tempo_permanencia),
                MainView.getInformationPanel()
        );
    }

    private void clearState(JTextField tempoPermanencia, JTextField tempoTravessia){
        tempoTravessia.setText("");
        tempoPermanencia.setText("");
    }
}
