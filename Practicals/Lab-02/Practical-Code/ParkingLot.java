public class ParkingLot {
    private int twoWheelers;
    private int fourWheelers;
    private final int twoCap;
    private final int fourCap;
    private static long revenue = 0;

    public ParkingLot(int twoCap, int fourCap) {
        this.twoCap = twoCap;
        this.fourCap = fourCap;
    }

    public void park(String type) {
        if (type.equals("two")) {
            if (twoWheelers < twoCap) {
                revenue += 20;
                twoWheelers++;
                System.out.println("Two-wheeler Parked");
                System.out.println("Revenue: " + revenue);
            } else {
                System.out.println("Two-wheeler Parking Full");
            }
        } else if (type.equals("four")) {
            if (fourWheelers < fourCap) {
                revenue += 40;
                fourWheelers++;
                System.out.println("Four-wheeler Parked");
                System.out.println("Revenue: " + revenue);
            } else {
                System.out.println("Four-wheeler Parking Full");
            }
        }
    }

    public void leave(String type) {
        if (type.equals("two")) {
            if (twoWheelers > 0) {
                twoWheelers--;
                System.out.println(twoWheelers + " two-wheelers left");
            }
        } else if (type.equals("four")) {
            if (fourWheelers > 0) {
                fourWheelers--;
                System.out.println(fourWheelers + " four-wheelers left");
            }
        }
    }

    public static void main(String[] args) {
        ParkingLot p = new ParkingLot(5, 2);

        p.park("two");
        p.park("two");

        p.leave("two");

        p.park("four");
    }
}