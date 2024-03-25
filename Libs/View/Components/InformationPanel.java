package Libs.View.Components;

import javax.swing.*;
import java.awt.*;

public class InformationPanel extends JScrollPane{
    /*
    * Componente responsável por mostrar as informações de eventos ocorridos dentro do jogo.
    *
    * Aqui o uso de LayoutManagers é exagerado para que as informações de atualizações fiquem
    * organizadas da forma correta dentro do ControlPanel, portanto, evitar alterá-las.
    *
    * */

    JPanel contentPanel = new JPanel();

    public InformationPanel() {

        this.setViewportView(contentPanel);
        this.setPreferredSize(new Dimension(300, 0));
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.contentPanel.setBackground(Color.darkGray);
        this.contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.setVisible(true);
    }

    public void addMessageEvent(String message){
        // método para adicionar mudanças de eventos dentro da caixa de texto

        this.contentPanel.add(this.addLabelEventFormatted(message));

        // forçando o scroll até o ultimo evento ocorrido
        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalScrollBar = getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        });

        this.revalidate();
        this.repaint();
    }

    public JLabel addLabelEventFormatted(String message){
        // adicionar formatação das labels, pois elas são plain text

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
