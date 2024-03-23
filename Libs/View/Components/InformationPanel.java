package Libs.View.Components;

import javax.swing.*;
import java.awt.*;

public class InformationPanel extends JPanel {
    int boardWith;
    int boardHeight;

    public InformationPanel(int boardWith, int boardHeight) {
        this.boardWith = boardWith;
        this.boardHeight = boardHeight;

        setBackground(Color.DARK_GRAY);
        this.setPreferredSize(new Dimension(boardWith, 200));
    }
}
