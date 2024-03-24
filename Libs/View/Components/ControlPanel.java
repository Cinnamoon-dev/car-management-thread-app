package Libs.View.Components;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


enum OriginSideEnum{
    Left('L'), Rigth('R');

    public Character valor;

    OriginSideEnum(Character vl){
        this.valor = vl;
    }
}

class BotaoInsertVehicle extends JButton implements ActionListener{

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
        *   tempo de permanencia
        *   tempo de travessia
        *
        * Dois botões possíveis : Cancel and Submited, submited -> instancia um novo carro
        *
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

        // dois botões que abrem um forms pedindo

        // a diferença entre os botões é a origin do veiculo
        // esses botões quando submited vão instanciar um novo veiculo

        BotaoInsertVehicle buttonL = new BotaoInsertVehicle(OriginSideEnum.Left, "Insert Left");
        BotaoInsertVehicle buttonR = new BotaoInsertVehicle(OriginSideEnum.Rigth, "Insert Right");

        this.add(buttonL);
        this.add(buttonR);
    }


}
