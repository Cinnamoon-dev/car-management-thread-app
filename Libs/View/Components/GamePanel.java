package Libs.View.Components;

import java.awt.*;
import javax.swing.*;


public class GamePanel extends JPanel {
    /*
    * Componente responsável por renderizar os carros e estacionamento.
    * */

    int boardWith;
    int boardHeight;

    public GamePanel(int boardWith, int boardHeight) {
        this.boardWith = boardWith;
        this.boardHeight = boardHeight;

        this.setPreferredSize(new Dimension(boardWith, boardHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("panel game called");
        ImageIcon imageIcon = new ImageIcon("./Libs/View/Assets/Ponte.png");
        g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
