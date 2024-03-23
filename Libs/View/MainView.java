package Libs.View;

import Libs.View.Components.ControlPanel;
import Libs.View.Components.GamePanel;
import Libs.View.Components.InformationPanel;

import java.awt.*;
import java.util.concurrent.Flow;
import javax.swing.*;


public class MainView extends JFrame{
    /*
    * View principal do projeto responsável por organizar os componentes de visualização periféricos.
    *
    * Organiza na tela os componentes :
    *   GamePanel - View do jogo que mostrará animações dos veiculos em movimento de um lado para o outro.
    *   InformationPanel - Uma view para visualização da mudança de estado das threads dos veiculos.
    *   ControlVehiclePanel - View responsável por dispor os controllers de inserção de veiculo e seus atributos (tempo de travessia, tempo de permanencia ...)
    *
    * reference : https://excalidraw.com/#json=_wFBU5tuFjNCbSIckWDAa,ax4NLfgzU-rUoPYsYSROug
    * */

    final int GamePanelWidth = 700;
    final int GamePanelHeight = 500;

    final int InformationPanelWidth = 300;
    final int InformationPanelHeight = 700;

    final int ControlVehiclePanelWidth = InformationPanelWidth;
    final int ControlVehiclePanelHeight = GamePanelHeight - InformationPanelHeight;

    final int MainBoardWidth = GamePanelWidth + InformationPanelWidth + 100;
    final int MainBoardHeight = Math.max(GamePanelHeight, InformationPanelHeight) + 100;

    public MainView(){

        // configurações da view main

        setTitle("Car Management");
        setLocationRelativeTo(null);
        setSize(MainBoardWidth, MainBoardHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(MainBoardWidth, MainBoardHeight));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // instanciando outros componentes à tela principal
        GamePanel gamePanel = new GamePanel(GamePanelWidth, GamePanelHeight);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 2.0;
        gbc.gridheight = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(gamePanel, gbc);

        ControlPanel controlPanel = new ControlPanel(ControlVehiclePanelWidth, ControlVehiclePanelHeight);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.5;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(controlPanel, gbc);

        InformationPanel informationPanel = new InformationPanel(InformationPanelWidth, InformationPanelHeight);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.5;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(informationPanel, gbc);

        setVisible(true);
    }
}





