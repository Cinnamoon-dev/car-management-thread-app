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

    public void crossBridge() {
        System.out.println(this.identifier + " is crossing the bridge from " + this.originalSide);
    }

    public void waitInOtherSide() {
        System.out.println(this.identifier + " arrived at the other side from " + this.originalSide);
    }

    public void run() {
        if(this.originalSide == 'R') {
            try {
                right_mutex.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(right_count == 1) {
                try {
                    traffic.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            right_mutex.release();

            this.crossBridge();

            try {
                right_mutex.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            right_count = right_count + 1;
            if(right_count == 0) {
                traffic.release();
            }

            this.waitInOtherSide();
        }
        else {
            try {
                left_mutex.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            left_count = left_count + 1;
            if(left_count == 1) {
                try {
                    traffic.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            left_mutex.release();

            this.crossBridge();

            try {
                left_mutex.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            left_count = left_count - 1;
            if(left_count == 0) {
                traffic.release();
            }
            left_mutex.release();

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