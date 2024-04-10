package Libs;

import Libs.View.MainView;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.Arrays;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.util.concurrent.Semaphore;


public class Driver implements Runnable {

    public String identifier;
    private final Integer stayDuration;
    private final Integer crossingDuration;
    private final Character originalSide; // 'L' or 'R'

    public static Semaphore queue_mutex = new Semaphore(1);
    private static final ArrayList<String> crossingQueue = new ArrayList<String>();

    public static int left_count = 0;
    public static int right_count = 0;
    public static Semaphore traffic = new Semaphore(1);
    public static Semaphore left_mutex = new Semaphore(1);
    public static Semaphore right_mutex = new Semaphore(1);
    public static Semaphore parking_mutex = new Semaphore(1);

    // User Interface Variables
    public static Semaphore controlDeleteDriver = new Semaphore(1);

    public Image carImage = null;
    public Image carImageOriginal = null;
    private Image carImageReverse = null;

    // Initial Coordinates
    public int xPosition = 0;
    public int yPosition = 192;

    static ArrayList<ParkingPosition> parking_left = new ArrayList<>();
    static ArrayList<ParkingPosition> parking_right = new ArrayList<>();

    static {
        // mapping left parking positions
        for(int x = 0 ; x < 2 ; ++x){
            for(int y = 0 ; y < 5; ++y){
                parking_right.add(new ParkingPosition(416 + x*32, 320 + y*32));
            }
        }

        // mapping parking right positions
        for(int x = 0; x < 2; ++x){
            for(int y = 0 ; y < 5; ++y){
                parking_left.add(new ParkingPosition(32 + x*32, 320 + y*32));
            }
        }
    }

    public Driver(Character originalSide, String identifier, Integer crossingDuration, Integer stayDuration){

        this.identifier = identifier;
        this.originalSide = originalSide;
        this.stayDuration = stayDuration;
        this.crossingDuration = crossingDuration;

        String pathImageOriginal = null;
        int changeImageNumber = Integer.parseInt(this.identifier) % 2;
        String carNameImage = changeImageNumber == 0 ? "Car1" : "Car2";

        if(originalSide == 'L'){
            xPosition = 32;
            pathImageOriginal = "Libs/View/Assets/Cars/Left/" + carNameImage + ".png";

            try  {
                this.carImage = new ImageIcon(getClass().getClassLoader().getResource(pathImageOriginal)).getImage();
            } catch (Exception e)  {
                e.printStackTrace();
            }
        } else {
            xPosition = 448;
            pathImageOriginal = "Libs/View/Assets/Cars/Right/"  + carNameImage + ".png";

            try  {
                this.carImage = new ImageIcon(getClass().getClassLoader().getResource(pathImageOriginal)).getImage();
            } catch (Exception e)  {
                e.printStackTrace();
            }
        }

        this.carImageOriginal = this.carImage;
        this.saveReverseCarImage(pathImageOriginal, carNameImage);
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

    public void message(String message) {
        MainView.informationViewBoard.addMessageEvent(message);
    }

    public void crossBridge(char sideReference, boolean isComingBack) {
        Date arrivalDate = new Date();

        down(queue_mutex);

        crossingQueue.add(0, this.identifier);
        message(this.identifier + " is crossing the bridge from " + this.originalSide);
        message(Arrays.toString(crossingQueue.toArray()));

        up(queue_mutex);

        this.moveCarVertical(1, 0.5F);
        int bridgesBlock = 6;
        float timePerBlock = (float) this.crossingDuration/bridgesBlock;

        while(getAgeInSeconds(arrivalDate) < this.crossingDuration) {

            if(sideReference == 'L' && !isComingBack || sideReference == 'R' && isComingBack){
                this.moveCarHorizontal(bridgesBlock, timePerBlock);
            }

            if(sideReference == 'L' && isComingBack || sideReference == 'R' && !isComingBack){
                this.moveCarHorizontal(-bridgesBlock, timePerBlock);
            }

            new Date().getTime();
        }

        if(sideReference == 'L' && !isComingBack || sideReference == 'R' && isComingBack){
            this.moveCarHorizontal(1, 0.5F);
        }

        if(sideReference == 'L' && isComingBack || sideReference == 'R' && !isComingBack){
            this.moveCarHorizontal(-1, 0.5F);
        }

        down(queue_mutex);
        while (crossingQueue.indexOf(this.identifier) != crossingQueue.size() - 1) {
            up(queue_mutex);
            new Date().getTime();
            down(queue_mutex);
        }

        crossingQueue.remove(this.identifier);
        up(queue_mutex);

        if(this.originalSide =='R') {
            message(this.identifier + " arrived at L");
            message(Arrays.toString(crossingQueue.toArray()));
        } else {
            message(this.identifier + " arrived at R");
            message(Arrays.toString(crossingQueue.toArray()));
        }

        this.moveCarVertical(1, 0.5F);

        up(queue_mutex);
    }

    public void waitInOtherSide() {
        Date arrivalTime = new Date();

        down(parking_mutex);
        if(xPosition <= 192) {
            this.enterParking(parking_left);
            message(this.identifier + " is waiting at side " + "L");
        } else {
            this.enterParking(parking_right);
            message(this.identifier + " is waiting at side " + "R");
        }

        this.forceRepaintGamePanel();
        up(parking_mutex);

        int i = 0;
        // Wait while consuming CPU
        while (getAgeInSeconds(arrivalTime) < this.stayDuration) {
            if(i % 2 == 0){
                this.setReverseCarImage();
            } else {
                this.setOriginalCarImage();
            }
            i++;
            this.forceRepaintGamePanel();
        }

        if(xPosition <= 192){
            this.exitParking(parking_left, 32,192);
        } else {
            this.exitParking(parking_right, 448, 192);
        }

        this.carImage = this.carImageOriginal;

        if(originalSide == 'R' && xPosition <= 192){
            this.setReverseCarImage();
        }

        if(originalSide == 'L' && xPosition > 192){
            this.setReverseCarImage();
        }
        this.forceRepaintGamePanel();

        this.forceRepaintGamePanel();
        message("Time passed: " + getAgeInSeconds(arrivalTime));
    }

    public void saveReverseCarImage(String pathImage, String carNameImage){
        String tempNameCarImage = carNameImage + "Reverse";
        tempNameCarImage = pathImage.replace(carNameImage, tempNameCarImage);
        this.carImageReverse = new ImageIcon(getClass().getClassLoader().getResource(tempNameCarImage)).getImage();
    }

    public void setReverseCarImage(){
        this.carImage = this.carImageReverse;
    }

    public void setOriginalCarImage(){
        this.carImage = this.carImageOriginal;
    }

    public static long getAgeInSeconds(Date initialTime) {
        Date now = new Date();
        return ((now.getTime() - initialTime.getTime()) / 1000);
    }

    public void run() {
        for(;;){
            if(this.originalSide == 'R') {

                this.moveCarHorizontal(-3, 0.5F);
                this.moveCarVertical(5, 0.5F);

                down(right_mutex);
                right_count = right_count + 1;
                message("RC before: " + right_count);
                if(right_count == 1) {
                    down(traffic);
                }
                up(right_mutex);

                this.crossBridge(this.originalSide, false);

                down(right_mutex);
                right_count = right_count - 1;
                message("RC after: " + right_count);
                if(right_count == 0) {
                    up(traffic);
                }
                up(right_mutex);

                this.waitInOtherSide();

                down(left_mutex);
                left_count = left_count + 1;
                message("LC before: " + left_count);
                if(left_count == 1) {
                    down(traffic);
                }
                up(left_mutex);

                this.moveCarHorizontal(3, 0.5F);
                this.moveCarVertical(5, 0.5F);

                this.crossBridge(this.originalSide, true);

                down(left_mutex);
                left_count = left_count - 1;
                message("LC after: " + left_count);
                if(left_count == 0) {
                    up(traffic);
                }
                up(left_mutex);

                this.waitInOtherSide();

            } else {

                this.moveCarHorizontal(3, 0.5F);
                this.moveCarVertical(5, 0.5F);

                down(left_mutex);
                left_count = left_count + 1;
                message("LC before: " + left_count);
                if(left_count == 1) {
                    down(traffic);
                }
                up(left_mutex);

                this.crossBridge(this.originalSide, false);

                down(left_mutex);
                left_count = left_count - 1;
                message("LC after: " + left_count);
                if(left_count == 0) {
                    up(traffic);
                }
                up(left_mutex);

                this.waitInOtherSide();

                down(right_mutex);
                right_count = right_count + 1;
                message("RC before: " + right_count);
                if(right_count == 1) {
                    down(traffic);
                }
                up(right_mutex);

                this.moveCarHorizontal(-3, 0.5F);
                this.moveCarVertical(5, 0.5F);

                this.crossBridge(this.originalSide, true);

                down(right_mutex);
                right_count = right_count - 1;
                message("RC after: " + right_count);
                if(right_count == 0) {
                    up(traffic);
                }
                up(right_mutex);

                this.waitInOtherSide();
            }
            this.forceRepaintGamePanel();
        }
    }


    public void moveCarHorizontal(int quantityBlocks, float delayByMoveSeconds){

        int moveQuantityPixel = 32;
        float delay = delayByMoveSeconds * 1000;

        if(quantityBlocks < 0){
            moveQuantityPixel = -32;
        }
        this.forceRepaintGamePanel();

        long elapsedTime = 0;

        for(int i = 0; i < Math.abs(quantityBlocks); ++i){
            long startTime = System.currentTimeMillis();

            // verifica se a proxima posição está livre.
            while(MainView.gameViewBoard.gameContent.preventCollision(xPosition + moveQuantityPixel, yPosition, this.identifier)){
                new Date().getTime();
            }
            this.xPosition += moveQuantityPixel;
            this.forceRepaintGamePanel();

            while(elapsedTime < delay){
                long currentTime = System.currentTimeMillis();
                elapsedTime = currentTime - startTime;
            }
            elapsedTime = 0;
        }
    }

    public void moveCarVertical(int quantityBlocks, float delayByMoveSeconds){

        int moveQuantityPixel = 32;
        float delay = delayByMoveSeconds * 1000;

        if(quantityBlocks < 0){
            moveQuantityPixel = -32;
        }
        this.forceRepaintGamePanel();

        long elapsedTime = 0;

        for(int i = 0; i < Math.abs(quantityBlocks) ; ++i){
            long startTime = System.currentTimeMillis();

            // verifica se a proxima posição está livre.
            while(MainView.gameViewBoard.gameContent.preventCollision(xPosition, yPosition + moveQuantityPixel, this.identifier)){
                new Date().getTime();
            }

            this.yPosition += moveQuantityPixel;
            this.forceRepaintGamePanel();

            while(elapsedTime < delay){
                long currentTime = System.currentTimeMillis();
                elapsedTime = currentTime - startTime;
            }
            elapsedTime = 0;
        }
    }

    public void enterParking(ArrayList<ParkingPosition> parkingVector){
        for(ParkingPosition parkingPosition : parkingVector){

            Driver driverInVacancy = parkingPosition.driverOcuppied;

            if (driverInVacancy == null) {
                parkingPosition.setDriverOcuppied(this);
                xPosition = parkingPosition.xPosition;
                yPosition = parkingPosition.yPosition;
                break;
            }
        }
    }

    public void exitParking(ArrayList<ParkingPosition> parkingVector, int xExit, int yExit){
        for (ParkingPosition parkingPosition : parkingVector) {
            Driver driverInVacancy = parkingPosition.driverOcuppied;

            if(driverInVacancy == null){ continue; }

            // se o carro que tiver na vaga bater com as coordenadas do veiculo é o meu veiculo
            if (driverInVacancy.xPosition == xPosition && driverInVacancy.yPosition == yPosition) {

                // evita colisão na saida do parking
                while(MainView.gameViewBoard.gameContent.preventCollision(xExit, yExit, this.identifier)){
                    new Date().getTime();
                }
                parkingPosition.vacateVacancy();
                xPosition = xExit;
                yPosition = yExit;
                return ;
            }
        }
    }

    public void forceRepaintGamePanel(){
        MainView.gameViewBoard.gameContent.repaint();
    }
}