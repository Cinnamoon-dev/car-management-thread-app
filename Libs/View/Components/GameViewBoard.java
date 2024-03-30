package Libs.View.Components;

import javax.swing.*;


public class GameViewBoard extends JFrame {
    /*
    * Componente responsável por renderizar os carros e estacionamento.
    * */

    public GameContent gameContent;

    public GameViewBoard() {

        gameContent = new GameContent();
        this.add(gameContent);

        this.pack();
        this.setResizable(false);
        this.setTitle("Car Management");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setVisible(true);
    }
}