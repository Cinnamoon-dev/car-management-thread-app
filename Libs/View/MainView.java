package Libs.View;

import Libs.View.Components.GamePanel;
import Libs.View.Components.InformationPanel;

import java.awt.*;
import javax.swing.*;


public class MainView extends JFrame{
    /*
    * View principal do projeto vai englobar tanto o game em si quanto a tela de
    * informações do game que informará atualizações dentro do game.
    *
    * reference : https://excalidraw.com/#json=_wFBU5tuFjNCbSIckWDAa,ax4NLfgzU-rUoPYsYSROug
    * */

    final int GamePanelWidht = 800;
    final int GamePanelHeight = 700;

    final int InformationPanelWidth = 400;
    final int InformationPanelHeight = 700;

    final int MainBoardWidth = GamePanelWidht + InformationPanelWidth;
    final int MainBoardHeight = Math.max(GamePanelHeight, InformationPanelHeight);


    public MainView(){
        // configurações do view main

        setVisible(true);
        setTitle("Car Management");
        setLocationRelativeTo(null);
        setSize(MainBoardWidth, MainBoardHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.LEADING));

        // instanciando a outras telas
        GamePanel gamePanel = new GamePanel(GamePanelWidht, GamePanelHeight);
        InformationPanel informationPanel = new InformationPanel(InformationPanelWidth, InformationPanelHeight);

        this.add(gamePanel);
        this.add(informationPanel);
    }
}





