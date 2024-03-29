package Libs.View.Components;

import Libs.Driver;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;


public class GameContent extends JPanel{

    final int GAME_CONTENT_WIDTH = 512;
    final int GAME_CONTENT_HEIGHT= 512;

    final Image backgroundImage;
    private ArrayList<Driver> drivers;

    public GameContent(){
        this.setPreferredSize(new Dimension(GAME_CONTENT_WIDTH,GAME_CONTENT_HEIGHT));
        this.setBackground(Color.black);

        this.backgroundImage = new ImageIcon("./Libs/View/Assets/MapAsset.png").getImage();
    }

    public void setDriver(Driver driver){
        if(this.drivers == null){
            this.drivers = new ArrayList<>();
        }

        this.drivers.add(driver);
        repaint();
    }

    public void removeDriver(Driver driver){
        // Deve ser protegido por um mutex ??
        this.drivers.remove(driver);
    }

    public boolean preventColision(int x ,int y, String identificador){
        for(Driver driver : this.drivers){

            if(Objects.equals(driver.identifier, identificador)){
                continue;
            }

            if(driver.xPosition == x && driver.yPosition == y){
                return true;
            }
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(backgroundImage, 0, 0, null);

        if(drivers != null) {
            for(Driver driver : this.drivers){
                g2d.drawImage(driver.carImage, driver.xPosition, driver.yPosition, null);
            }
        }
    }
}
