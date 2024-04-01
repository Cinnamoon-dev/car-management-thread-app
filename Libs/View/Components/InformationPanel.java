package Libs.View.Components;

import javax.swing.*;
import java.awt.*;

public class InformationPanel extends JPanel {

    JPanel contentPanel = new JPanel();
    JScrollPane jScrollPane = null;

    public InformationPanel(int controlVehiclePanelWidth, int controlPanelHeight) {

        this.contentPanel.setBackground(Color.darkGray);
        this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.jScrollPane = new JScrollPane(contentPanel);
        this.jScrollPane.setPreferredSize(new Dimension(controlVehiclePanelWidth + 52, controlPanelHeight));
        this.jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(jScrollPane);
    }

    public void addMessageEvent(String message){

        JLabel jLabel = this.addLabelEventFormatted(message);
        this.contentPanel.add(jLabel);

        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = this.jScrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        });

        this.contentPanel.revalidate();
        this.jScrollPane.revalidate();

        this.jScrollPane.repaint();
        this.contentPanel.repaint();

        this.repaint();
    }

    public JLabel addLabelEventFormatted(String message){
        JLabel event = new JLabel(message);
        System.out.println(" Event Formatted Called");

        event.setOpaque(true);
        event.setForeground(Color.WHITE);
        event.setBackground(new Color(10, 102, 142));
        event.setFont(getFont().deriveFont(Font.BOLD, 14f));
        event.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        event.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return event;
    }
}
