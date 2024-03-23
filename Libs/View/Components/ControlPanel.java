package Libs.View.Components;

import java.awt.*;
import javax.swing.*;

public class ControlPanel extends JPanel {
    /*
    *
    * Classe que representa o controle de opções para inserção de novos carros na ponte.
    * Funcionalidade : Da a opção de inserir veiculos, na direita ou na esquerda, ao usuario.
    * */

    int boardWith;
    int boardHeight;

    public ControlPanel(int boardWith, int boardHeight) {
        this.boardWith = boardWith;
        this.boardHeight = boardHeight;

        setBackground(Color.lightGray);
        this.setPreferredSize(new Dimension(boardWith, boardHeight));
    }
}
