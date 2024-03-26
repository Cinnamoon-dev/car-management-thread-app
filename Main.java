import Libs.Driver;
public class Main {
    public static void main(String[] args) {
        Driver d1 = new Driver('L', "3", 10, 10);
        Driver d2 = new Driver('L', "2", 10, 10);
        Driver d3 = new Driver('R', "1", 10, 10);
        Driver d4 = new Driver('R', "4", 10, 10);
        Driver d5 = new Driver('R', "5", 10, 10);

        d1.start();
        d2.start();
        d3.start();
        d4.start();
        d5.start();
    }
}