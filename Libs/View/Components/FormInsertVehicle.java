package Libs.View.Components;

import Libs.Driver;
import Libs.View.MainView;

import java.awt.*;
import javax.swing.*;


public class FormInsertVehicle extends JFrame {

    /*
     * Formulário para pegar os dados de condições de existencia de um determinado veiculo.
     * */

    Integer driverIdentifier = 0;

    public FormInsertVehicle(int maxComponentSizeWidth){

        setResizable(false);
        setTitle("Insert New Driver");
        setSize(320, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
        panelContainer.setPreferredSize(new Dimension(maxComponentSizeWidth, 0));

        JButton insertLeft = new JButton("Insert Left");
        JButton insertRigth = new JButton("Insert Rigth");
        JLabel travessiaLabel = new JLabel("Tempo de Travessia:");
        JLabel permanenciaLabel = new JLabel("Tempo de Permanência:");
        JTextField tempo_travessia =  new JTextField();
        JTextField tempo_permanencia = new JTextField();

        insertLeft.setMaximumSize(new Dimension(maxComponentSizeWidth, 75));
        insertRigth.setMaximumSize(new Dimension(maxComponentSizeWidth, 75));

        JPanel ButtonsBox = new JPanel();
        ButtonsBox.setAlignmentX(0);
        ButtonsBox.add(insertLeft);
        ButtonsBox.add(insertRigth);
        ButtonsBox.setVisible(true);

        panelContainer.add(travessiaLabel);
        panelContainer.add(tempo_travessia);
        panelContainer.add(permanenciaLabel);
        panelContainer.add(tempo_permanencia);
        panelContainer.add(ButtonsBox);

        insertLeft.addActionListener(_ -> {
            Driver driver = createDriver('L', tempo_travessia.getText(), tempo_permanencia.getText());
            this.clearState(tempo_permanencia, tempo_travessia);

            MainView.gameViewBoard.gameContent.setDriver(driver);

            Thread driverThread = new Thread(driver);
            driverThread.start(); // Inicia a thread do motorista
        });

        insertRigth.addActionListener(_ -> {
            Driver driver = createDriver('R', tempo_travessia.getText(), tempo_permanencia.getText());
            this.clearState(tempo_permanencia, tempo_travessia);

            MainView.gameViewBoard.gameContent.setDriver(driver);

            Thread driverThread = new Thread(driver);
            driverThread.start(); // Inicia a thread do motorista
        });

        this.add(panelContainer);
        this.setVisible(true);
    }

    private Driver createDriver(char originSideVehicle, String tempo_travessia, String tempo_permanencia){
        driverIdentifier++; // increment identifier
        return new Driver(
                originSideVehicle,
                this.driverIdentifier.toString(),
                Integer.parseInt(tempo_travessia),
                Integer.parseInt(tempo_permanencia)
        );
    }

    private void clearState(JTextField tempoPermanencia, JTextField tempoTravessia){
        tempoTravessia.setText("");
        tempoPermanencia.setText("");
    }
}
