package Libs;

import java.util.concurrent.Semaphore;

public class Driver extends Thread {
    private Character originalSide;
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

    public void run() {

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
