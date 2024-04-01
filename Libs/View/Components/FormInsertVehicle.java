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

    public FormInsertVehicle(int maxComponentSizeWidth){

        setPreferredSize(new Dimension(maxComponentSizeWidth, 150));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JButton insertLeft = new JButton("Insert Left");
        JButton insertRigth = new JButton("Insert Right");
        JLabel travessiaLabel = new JLabel("Tempo de Travessia:");
        JLabel permanenciaLabel = new JLabel("Tempo de Permanência:");
        JTextField tempo_travessia =  new JTextField();
        JTextField tempo_permanencia = new JTextField();

        JPanel buttonsBox = new JPanel();
        buttonsBox.setLayout(new BoxLayout(buttonsBox, BoxLayout.X_AXIS));
        buttonsBox.setPreferredSize(new Dimension(maxComponentSizeWidth, 50));
        buttonsBox.setAlignmentX(0);
        buttonsBox.add(insertLeft);
        buttonsBox.add(insertRigth);

        this.add(travessiaLabel);
        this.add(tempo_travessia);
        this.add(permanenciaLabel);
        this.add(tempo_permanencia);
        this.add(buttonsBox);

        insertLeft.addActionListener( e -> {
            Driver driver = createDriver('L', tempo_travessia.getText(), tempo_permanencia.getText());
            this.clearState(tempo_permanencia, tempo_travessia);

            MainView.gameViewBoard.gameContent.setDriver(driver);

            Thread driverThread = new Thread(driver);
            driverThread.start(); // Inicia a thread do motorista
        });

        insertRigth.addActionListener( e -> {
            Driver driver = createDriver('R', tempo_travessia.getText(), tempo_permanencia.getText());
            this.clearState(tempo_permanencia, tempo_travessia);

            MainView.gameViewBoard.gameContent.setDriver(driver);

            Thread driverThread = new Thread(driver);
            driverThread.start(); // Inicia a thread do motorista
        });
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
