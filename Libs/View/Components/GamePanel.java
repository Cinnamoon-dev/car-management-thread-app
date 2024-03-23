package Libs.View.Components;

import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    int boardWith;
    int boardHeight;

    public GamePanel(int boardWith, int boardHeight) {
        this.boardWith = boardWith;
        this.boardHeight = boardHeight;

        this.setBackground(Color.CYAN);
        this.setPreferredSize(new Dimension(boardWith, boardHeight));
    }
}
