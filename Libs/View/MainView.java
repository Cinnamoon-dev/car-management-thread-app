package Libs.View;

import Libs.View.Components.FormInsertVehicle;
import Libs.View.Components.GameViewBoard;
import Libs.View.Components.InformationPanel;

import javax.swing.*;
import java.awt.*;


public class MainView extends JFrame{
    /*
    * View principal do projeto responsável por organizar os componentes de visualização periféricos.
    *
    * Organiza na tela os componentes :
    *   GamePanel - View do jogo que mostrará animações dos veiculos em movimento de um lado para o outro.
    *   InformationPanel - Uma view para visualização da mudança de estado das threads dos veiculos.
    *   ControlVehiclePanel - View responsável por dispor os controllers de inserção de veiculo e seus atributos (tempo de travessia, tempo de permanencia ...)
    *
    * reference : https://excalidraw.com/#json=sTCY5mpTyQVPZxNsE7tOZ,ZhLkejZJMLM_zG5cpyEUmA
    * */

    public static GameViewBoard gameViewBoard;
    public static InformationPanel informationViewBoard;

    final int InformationPanelWidth = 512;
    final int InformationPanelHeight = 256;
    final int ControlVehiclePanelWidth = InformationPanelWidth;

    public MainView(){

        JFrame myframe = new JFrame();

        MainView.gameViewBoard = new GameViewBoard();
        FormInsertVehicle formInsertVehicle = new FormInsertVehicle(ControlVehiclePanelWidth);
        MainView.informationViewBoard  = new InformationPanel(ControlVehiclePanelWidth, InformationPanelHeight);

        myframe.setSize(InformationPanelWidth + 150,512);
        myframe.setTitle("Controle Do Jogo");

        JPanel jContentPanel = new JPanel();
        jContentPanel.setAlignmentX(LEFT_ALIGNMENT);

        jContentPanel.setLayout(new BoxLayout(jContentPanel, BoxLayout.Y_AXIS));
        jContentPanel.add(formInsertVehicle);
        jContentPanel.add(MainView.informationViewBoard);

        myframe.add(jContentPanel);
        myframe.setResizable(false);
        myframe.setVisible(true);

        myframe.setLocation(0, 0);
        MainView.gameViewBoard.setLocation(myframe.getWidth() + 20, 0);
    }
}





