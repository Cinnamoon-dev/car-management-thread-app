package Libs;

public class ParkingPosition{
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

    void vacateVacancy(){
        this.driverOcuppied = null;
    }
}
