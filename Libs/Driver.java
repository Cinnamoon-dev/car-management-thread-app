package Libs;

import Libs.View.MainView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Semaphore;


class ParkingPosition{
    public int xPosition;
    public int yPosition;
    Driver driverOcuppied;

    public ParkingPosition(int x, int y){
        this.xPosition = x;
        this.yPosition = y;
    }

    void setDriverOcuppied(Driver driver){
        this.driverOcuppied = driver;
    }
}

public class Driver implements Runnable {

    public String identifier;
    private final Integer stayDuration;
    private final Integer crossingDuration;
    private final Character originalSide; // 'L' or 'R'

    static ArrayList<ParkingPosition> parking_left = new ArrayList<ParkingPosition>();
    static ArrayList<ParkingPosition> parking_right = new ArrayList<ParkingPosition>();

    public static Semaphore queue_mutex = new Semaphore(1);
    private static final ArrayList<String> crossingQueue = new ArrayList<String>();

    public static int left_count = 0;
    public static int right_count = 0;
    public static Semaphore traffic = new Semaphore(1);
    public static Semaphore left_mutex = new Semaphore(1);
    public static Semaphore right_mutex = new Semaphore(1);
    public static Semaphore parking_mutex = new Semaphore(1);


    // paint variables
    public Image carImage = null;

    public int xPosition = 0;
    public int yPosition = 192;

    static {
        // mapped left parking positions
        for(int x = 0 ; x < 2 ; ++x){
            for(int y = 0 ; y < 5; ++y){
                parking_right.add(new ParkingPosition(416 + x*32, 358 + y*32));
            }
        }

        for (ParkingPosition parkingPosition : parking_right){
            System.out.println(parkingPosition.xPosition + " " + parkingPosition.yPosition);
        }


        // mapped parking right pos
        for(int x = 0; x < 2; ++x){
            for(int y = 0 ; y < 5; ++y){
                parking_left.add(new ParkingPosition(32 + x*32, 358 + y*32));
            }
        }

        for (ParkingPosition parkingPosition : parking_left){
            System.out.println(parkingPosition.xPosition + " " + parkingPosition.yPosition);
        }
    }

    public Driver(Character originalSide, String identifier, Integer crossingDuration, Integer stayDuration) {

        try  {
            this.carImage = ImageIO.read(new File("./Libs/View/Assets/TankRed.png"));
        } catch (Exception e)  {
            e.printStackTrace();
        }

        this.identifier = identifier;
        this.originalSide = originalSide;
        this.stayDuration = stayDuration;
        this.crossingDuration = crossingDuration;

        if(originalSide == 'L'){
            // TODO MUDAR A IMAGEM DO VEICULO
            xPosition = 32;
        }else{
            // TODO MUDAR A IMAGEM DO VEICULO
            xPosition = 448;
        }
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

    public void crossBridge() {
        Date arrivalDate = new Date();

        down(queue_mutex);

        crossingQueue.add(0, this.identifier);
        message(this.identifier + " is crossing the bridge from " + this.originalSide);
        message(Arrays.toString(crossingQueue.toArray()));

        up(queue_mutex);

        while(getAgeInSeconds(arrivalDate) < this.crossingDuration) {
            new Date().getTime();
        }

        down(queue_mutex);
        while (crossingQueue.indexOf(this.identifier) != crossingQueue.size() - 1) {
            up(queue_mutex);
            new Date().getTime();
            down(queue_mutex);
        }

        crossingQueue.remove(this.identifier);
        if(this.originalSide.equals('R')) {
            message(this.identifier + " arrived at L");
            message(Arrays.toString(crossingQueue.toArray()));
            up(queue_mutex);
            return;
        }
        message(this.identifier + " arrived at R");
        message(Arrays.toString(crossingQueue.toArray()));
        up(queue_mutex);
    }

    public void waitInOtherSide() {
        Date arrivalTime = new Date();

        if(this.originalSide.equals('R')) {
            message(this.identifier + " is waiting at side " + "L");
            return;
        }
        message(this.identifier + " is waiting at side " + "R");

        // Core logic below
        // Remove prints when done with debugging
        while (getAgeInSeconds(arrivalTime) < this.stayDuration) {
            new Date().getTime();
        }
        message("Time passed: " + getAgeInSeconds(arrivalTime));
    }

    public static long getAgeInSeconds(Date initialTime) {
        Date now = new Date();
        return ((now.getTime() - initialTime.getTime()) / 1000);
    }

    public void run() {
        if(this.originalSide == 'R') {
            down(right_mutex);
            right_count = right_count + 1;
            message("RC before: " + right_count);
            if(right_count == 1) {
                down(traffic);
            }
            up(right_mutex);

            this.crossBridge();

            down(right_mutex);
            right_count = right_count - 1;
            message("RC after: " + right_count);
            if(right_count == 0) {
                up(traffic);
            }
            up(right_mutex);

            this.waitInOtherSide();

            // Calling the left_count must happen after the wait method
            // Because the car will only go back to traffic after the waiting period
            down(left_mutex);
            left_count = left_count + 1;
            message("LC before: " + left_count);
            if(left_count == 1) {
                down(traffic);
            }
            up(left_mutex);

            this.crossBridge();

            down(left_mutex);
            left_count = left_count - 1;
            message("LC after: " + left_count);
            if(left_count == 0) {
                up(traffic);
            }
            up(left_mutex);
        }
        else {
            this.moveCarHorizontal(3, 0.5F);
            this.moveCarVertical(5, 0.5F);

            down(left_mutex);
            left_count = left_count + 1;
            message("LC before: " + left_count);
            if(left_count == 1) {
                down(traffic);
            }
            up(left_mutex);

            // TODO ESSA LOGICA DEVE ESTAR DENTRO DO CROSSBRIDGE PARA ATRAVESSAR JÁ CONTANDO O TEMPO
            this.moveCarVertical(1, 0.5F);
            this.moveCarHorizontal(6, 1); // ESSE TEMPO DEVE VARIAR DE ACORDO COM O TEMPO DE TRAVESSIA

            this.crossBridge();

            down(left_mutex);
            left_count = left_count - 1;
            message("LC after: " + left_count);
            if(left_count == 0) {
                up(traffic);
            }
            up(left_mutex);

            this.moveCarHorizontal(1, 0.5F);
            this.moveCarVertical(1, 0.5F);

            // logica para inserir no parking
            down(parking_mutex);
            this.enterParking(parking_right);
            this.forceRepaintGamePanel();
            up(parking_mutex);

            this.waitInOtherSide();

            down(parking_mutex);
            this.exitParking(parking_right, 480, 192);
            this.forceRepaintGamePanel();
            up(parking_mutex);


            down(right_mutex);
            right_count = right_count + 1;
            message("RC before: " + right_count);
            if(right_count == 1) {
                down(traffic);
            }
            up(right_mutex);

            // mover o carro para o ponto L
            this.moveCarHorizontal(-4, 0.5F);
            this.moveCarVertical(6, 0.5F);

            this.crossBridge();

            this.moveCarHorizontal(-5, 0.5F);

            down(right_mutex);
            right_count = right_count - 1;
            message("RC after: " + right_count);
            if(right_count == 0) {
                up(traffic);
            }
            up(right_mutex);

            this.moveCarHorizontal(-2, 0.5F);
            this.moveCarVertical(1, 0.5F);

            MainView.gameViewBoard.gameContent.removeDriver(this);
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
            while(MainView.gameViewBoard.gameContent.preventColision(xPosition + moveQuantityPixel, yPosition, this.identifier)){
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
            while(MainView.gameViewBoard.gameContent.preventColision(xPosition, yPosition + moveQuantityPixel, this.identifier)){
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
            if(parkingPosition.driverOcuppied == null){
                xPosition = parkingPosition.xPosition;
                yPosition = parkingPosition.yPosition;
                parkingPosition.setDriverOcuppied(this);
            }
        }
        this.forceRepaintGamePanel();
    }

    public void exitParking(ArrayList<ParkingPosition> parkingVector, int xExit, int yExit){
        for(ParkingPosition parkingPosition : parkingVector){
            if(!parkingPosition.driverOcuppied.equals(this)){
                // start right position
                xPosition = xExit;
                yPosition = yExit;
                parkingPosition.setDriverOcuppied(this);
            }
        }
        this.forceRepaintGamePanel();
    }

    public void forceRepaintGamePanel(){
        MainView.gameViewBoard.gameContent.repaint();
    }
}