package Libs;

import java.util.concurrent.Semaphore;

public class Driver extends Thread {
    private Character originalSide; // 'L' or 'R'
    private String identifier;
    private Integer crossingDuration;
    private Integer stayDuration;

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
        System.out.println(this.identifier + " is crossing the bridge from " + this.originalSide);
        if(this.originalSide.equals('R')) {
            System.out.println(this.identifier + " arrived at L");
            return;
        }
        System.out.println(this.identifier + " arrived at R");
    }

    public void waitInOtherSide() {
        if(this.originalSide.equals('R')) {
            System.out.println(this.identifier + " is waiting at side " + "L");
            return;
        }
        System.out.println(this.identifier + " is waiting at side " + "R");
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
