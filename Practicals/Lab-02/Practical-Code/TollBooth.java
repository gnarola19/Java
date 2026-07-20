public class TollBooth {
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked = 0;

    public TollBooth(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        this.seatsAvailable = capacity;
    }

    public TollBooth(String title) {
        this(title, 100);
    }

    public boolean book(int n) {
        if (n <= seatsAvailable) {
            seatsAvailable -= n;
            totalBooked += n;
            return true;
        }
        return false;
    }

    public void cancel(int n) {
        seatsAvailable += n;
        if (seatsAvailable > capacity) {
            seatsAvailable = capacity;
        }
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public static int getTotalBooked() {
        return totalBooked;
    }

    public static void main(String[] args) {
        System.out.println("Creating 'Inception' with 50 capacity.");
        TollBooth show = new TollBooth("Inception", 50);

        System.out.println("Initial seats available: " + show.getSeatsAvailable());
        System.out.println("----------------------------------------");

        System.out.println("Booking 30 seats...");
        boolean res1 = show.book(30);
        System.out.println("Result: " + res1 + " | Seats left: " + show.getSeatsAvailable());

        System.out.println("\nBooking 25 seats (only 20 left)...");
        boolean res2 = show.book(25);
        System.out.println("Result: " + res2 + " | Seats left: " + show.getSeatsAvailable());

        System.out.println("\nCanceling 10 seats...");
        show.cancel(10);
        System.out.println("Seats left: " + show.getSeatsAvailable());

        System.out.println("\nCanceling 40 seats (exceeding initial capacity)...");
        show.cancel(40);
        System.out.println("Seats left (capped at capacity): " + show.getSeatsAvailable());

        System.out.println("----------------------------------------");
        System.out.println("Total successful bookings globally: " + TollBooth.getTotalBooked());
    }
}