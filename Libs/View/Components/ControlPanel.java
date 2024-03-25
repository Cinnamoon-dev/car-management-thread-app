package Libs.View.Components;

import javax.swing.*;
import java.awt.*;


public class ControlPanel extends JPanel{

    /*
    * Classe responsável por criar o box que engloba o
    * */

    public FormInsertVehicle formInsertVehicle;

    public ControlPanel(int boardWidth) {
        this.setBackground(Color.lightGray);
        this.setPreferredSize(new Dimension(boardWidth, 165));

        this.formInsertVehicle = new FormInsertVehicle(boardWidth);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        this.add(formInsertVehicle, gbc);
        this.setVisible(true);
    }
}
