package Libs.View.Components;

import javax.swing.*;
import java.awt.*;

public class InformationPanel extends JFrame{
    /*
    * Componente responsável por mostrar as informações de eventos ocorridos dentro do jogo.
    *
    * Aqui o uso de LayoutManagers é exagerado para que as informações de atualizações fiquem
    * organizadas da forma correta dentro do ControlPanel, portanto, evitar alterá-las.
    *
    * */

    JScrollPane scrollPane;
    JPanel contentPanel = new JPanel();

    public InformationPanel(int controlVehiclePanelWidth, int controlPanelHeight) {

        setResizable(false);
        setTitle("Log Events");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(controlVehiclePanelWidth + 100, controlPanelHeight);

        this.scrollPane = new JScrollPane(this.contentPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.contentPanel.setBackground(Color.darkGray);
        this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.add(scrollPane);
        this.setVisible(true);
    }

    public void addMessageEvent(String message){

        this.contentPanel.add(this.addLabelEventFormatted(message));

        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = this.scrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        });

        this.revalidate();
        this.repaint();
    }

    public JLabel addLabelEventFormatted(String message){
        JLabel event = new JLabel(message);

        event.setOpaque(true);
        event.setForeground(Color.WHITE);
        event.setBackground(new Color(10, 102, 142));
        event.setFont(getFont().deriveFont(Font.BOLD, 14f));
        event.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        event.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return event;
    }
}
