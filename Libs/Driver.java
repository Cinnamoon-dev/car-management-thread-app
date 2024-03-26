package Libs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class Driver extends Thread {
    private Character originalSide; // 'L' or 'R'
    private String identifier;
    private Integer crossingDuration;
    private Integer stayDuration;

    public static Semaphore queue_mutex = new Semaphore(1);
    private static ArrayList<String> crossingQueue = new ArrayList<String>();

    public static Semaphore right_mutex = new Semaphore(1);
    public static Semaphore left_mutex = new Semaphore(1);
    public static Semaphore traffic = new Semaphore(1);
    public static int right_count = 0;
    public static int left_count = 0;

    public Driver(Character originalSide, String identifier, Integer crossingDuration, Integer stayDuration) {
        this.originalSide = originalSide;
        this.identifier = identifier;
        this.crossingDuration = crossingDuration;
        this.stayDuration = stayDuration;
    }

    public static void down(Semaphore semaphore) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void down(Semaphore semaphore, Integer permits) {
        try {
            semaphore.acquire(permits);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void up(Semaphore semaphore) {
        semaphore.release();
    }

    public static void up(Semaphore semaphore, Integer permits) {
        semaphore.release(permits);
    }

    public void crossBridge() {
        Date arrivalDate = new Date();

        down(queue_mutex);
        crossingQueue.add(0, this.identifier);
        System.out.println(this.identifier + " is crossing the bridge from " + this.originalSide);
        System.out.println(Arrays.toString(crossingQueue.toArray()));
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
            System.out.println(this.identifier + " arrived at L");
            System.out.println(Arrays.toString(crossingQueue.toArray()));
            up(queue_mutex);
            return;
        }
        System.out.println(this.identifier + " arrived at R");
        System.out.println(Arrays.toString(crossingQueue.toArray()));
        up(queue_mutex);
    }

    public void waitInOtherSide() {
        Date arrivalTime = new Date();
        if(this.originalSide.equals('R')) {
            System.out.println(this.identifier + " is waiting at side " + "L");
            return;
        }
        System.out.println(this.identifier + " is waiting at side " + "R");

        // Core logic below
        // Remove prints when done with debugging
        while (getAgeInSeconds(arrivalTime) < this.stayDuration) {
            new Date().getTime();
        }
        System.out.println("Time passed: " + getAgeInSeconds(arrivalTime));
    }

    public static long getAgeInSeconds(Date initialTime) {
        Date now = new Date();
        return ((now.getTime() - initialTime.getTime()) / 1000);
    }
    
    public void run() {
        if(this.originalSide == 'R') {
            down(right_mutex);
            right_count = right_count + 1;
            System.out.println("RC before: " + right_count);
            if(right_count == 1) {
                down(traffic);
            }
            up(right_mutex);

            this.crossBridge();

            down(right_mutex);
            right_count = right_count - 1;
            System.out.println("RC after: " + right_count);
            if(right_count == 0) {
                up(traffic);
            }
            up(right_mutex);

            this.waitInOtherSide();

            // Calling the left_count must happen after the wait method
            // Because the car will only go back to traffic after the waiting period
            down(left_mutex);
            left_count = left_count + 1;
            System.out.println("LC before: " + left_count);
            if(left_count == 1) {
                down(traffic);
            }
            up(left_mutex);

            this.crossBridge();

            down(left_mutex);
            left_count = left_count - 1;
            System.out.println("LC after: " + left_count);
            if(left_count == 0) {
                up(traffic);
            }
            up(left_mutex);
        }
        else {
            down(left_mutex);
            left_count = left_count + 1;
            System.out.println("LC before: " + left_count);
            if(left_count == 1) {
                down(traffic);
            }
            up(left_mutex);

            this.crossBridge();

            down(left_mutex);
            left_count = left_count - 1;
            System.out.println("LC after: " + left_count);
            if(left_count == 0) {
                up(traffic);
            }
            up(left_mutex);

            this.waitInOtherSide();

            down(right_mutex);
            right_count = right_count + 1;
            System.out.println("RC before: " + right_count);
            if(right_count == 1) {
                down(traffic);
            }
            up(right_mutex);
            
            this.crossBridge();

            down(right_mutex);
            right_count = right_count - 1;
            System.out.println("RC after: " + right_count);
            if(right_count == 0) {
                up(traffic);
            }
            up(right_mutex);
        }
    }

    public Character getOriginalSide() {
        return originalSide;
    }

    public Integer getCrossingDuration() {
        return crossingDuration;
    }

    public Integer getStayDuration() {
        return stayDuration;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setCrossingDuration(Integer crossingDuration) {
        this.crossingDuration = crossingDuration;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setOriginalSide(Character originalSide) {
        this.originalSide = originalSide;
    }

    public void setStayDuration(Integer stayDuration) {
        this.stayDuration = stayDuration;
    }
}
