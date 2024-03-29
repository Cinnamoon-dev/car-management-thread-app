package Libs.View;

import Libs.View.Components.FormInsertVehicle;
import Libs.View.Components.GameViewBoard;
import Libs.View.Components.InformationPanel;

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

    public static GameViewBoard gameViewBoard;
    public static InformationPanel informationViewBoard;

    final int InformationPanelWidth = 512;
    final int InformationPanelHeight = 300;
    final int ControlVehiclePanelWidth = InformationPanelWidth;

    public MainView(){

        GameViewBoard gameViewBoard = new GameViewBoard();
        FormInsertVehicle formInsertVehicle = new FormInsertVehicle(ControlVehiclePanelWidth);
        InformationPanel informationViewBoard = new InformationPanel(ControlVehiclePanelWidth, InformationPanelHeight);

        MainView.gameViewBoard = gameViewBoard;
        MainView.informationViewBoard = informationViewBoard;

        MainView.informationViewBoard.setLocation(0, 0);
        MainView.gameViewBoard.setLocation(MainView.informationViewBoard.getWidth() + 20, 0);
        formInsertVehicle.setLocation(gameViewBoard.getWidth() + MainView.informationViewBoard.getWidth() + 20, 0);
    }

    public static InformationPanel getInformationViewBoard(){
        return informationViewBoard;
    }
}





