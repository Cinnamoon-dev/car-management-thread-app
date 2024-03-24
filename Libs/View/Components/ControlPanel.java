package Libs.View.Components;

import Libs.OriginSideEnum;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class BotaoInsertVehicle extends JButton implements ActionListener{

    /*
    * Classe responsável por representar visualmente os botões de adição de veiculos.
    * Vai representar duas opções os carros que irão ser criados na esquerdas e direitas.
    * */

    Character origin;

    public BotaoInsertVehicle(OriginSideEnum org, String title){
        this.setText(title);
        this.origin = org.valor;
        this.addActionListener(this::actionPerformed);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
         * Abrir um formulario solicitando as seguintes informações
         *   tempo de travessia
         *   tempo de permanencia
         * */

        System.out.println("Button Clicked " + this.getText());
        FormInsertVehicle insertVehicle = new FormInsertVehicle(this.origin);
        insertVehicle.setContentPane(insertVehicle.panelMain);
        insertVehicle.setVisible(true);
    }
}


public class ControlPanel extends JPanel {
    /*
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

        BotaoInsertVehicle buttonL = new BotaoInsertVehicle(OriginSideEnum.Left, "Insert Left");
        BotaoInsertVehicle buttonR = new BotaoInsertVehicle(OriginSideEnum.Rigth, "Insert Right");

        this.add(buttonL);
        this.add(buttonR);
    }
}
