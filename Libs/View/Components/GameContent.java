package Libs.View.Components;

import Libs.Driver;

import java.awt.*;
import javax.swing.*;
import java.util.Objects;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class GameContent extends JPanel{

    final int GAME_CONTENT_WIDTH = 512;
    final int GAME_CONTENT_HEIGHT= 512;

    final Image backgroundImage;
    private ArrayList<Driver> drivers;
    public static Semaphore position_mutex = new Semaphore(1);

    public GameContent(){
        this.setPreferredSize(new Dimension(GAME_CONTENT_WIDTH,GAME_CONTENT_HEIGHT));
        this.setBackground(Color.black);

        //this.backgroundImage = new ImageIcon("./Libs/View/Assets/MapAsset.png").getImage();
        this.backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("./Libs/View/Assets/MapAsset.png")).getImage();
    }

    public void setDriver(Driver driver){
        if(this.drivers == null){
            this.drivers = new ArrayList<>();
        }

        this.drivers.add(driver);
        repaint();
    }

    public static void down(Semaphore semaphore) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void up(Semaphore semaphore) {
        semaphore.release();
    }

    public void removeDriver(Driver driver){
        down(position_mutex);
        this.drivers.remove(driver);
        up(position_mutex);
    }

    public boolean preventCollision(int x , int y, String identificador) {
        down(position_mutex);
        for (Driver driver : this.drivers) {
            if (Objects.equals(driver.identifier, identificador)) {
                continue;
            }
            if (driver.xPosition == x && driver.yPosition == y) {
                up(position_mutex);
                return true;
            }
        }
        up(position_mutex);
        return false;
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(backgroundImage, 0, 0, null);

        if(drivers != null) {
            for(Driver driver : this.drivers){
                g2d.setFont(new Font("Arial", Font.BOLD, 32));
                g2d.setColor(Color.WHITE);
                g2d.drawImage(driver.carImage, driver.xPosition, driver.yPosition, null);
                g2d.drawString(driver.identifier, driver.xPosition + 4, driver.yPosition + 32);
            }
        }
    }
}
