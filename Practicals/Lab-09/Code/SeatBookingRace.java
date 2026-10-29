import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SeatBookingRace {
    static class BookingSystem {
        int seatsLeft = 5;
        int successfulBookings = 0;
        boolean synchronizedBooking;

        BookingSystem(boolean synchronizedBooking) {
            this.synchronizedBooking = synchronizedBooking;
        }

        boolean book(CountDownLatch ready, CountDownLatch start) {
            try {
                ready.countDown();
                start.await();

                if (synchronizedBooking) {
                    return synchronizedBook();
                }

                if (seatsLeft > 0) {
                    Thread.yield();
                    seatsLeft--;
                    successfulBookings++;
                    return true;
                }
                return false;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        synchronized boolean synchronizedBook() {
            if (seatsLeft > 0) {
                seatsLeft--;
                successfulBookings++;
                return true;
            }
            return false;
        }
    }

    static void runTest(boolean synchronizedBooking) throws InterruptedException {
        BookingSystem system = new BookingSystem(synchronizedBooking);
        CountDownLatch ready = new CountDownLatch(10);
        CountDownLatch start = new CountDownLatch(1);
        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                boolean booked = system.book(ready, start);
                System.out.println(Thread.currentThread().getName()
                        + (booked ? " booked a seat." : " could not book."));
            }, "Customer-" + (i + 1));
            threads[i].start();
        }

        ready.await();
        start.countDown();

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Synchronization: " + synchronizedBooking);
        System.out.println("Successful bookings: " + system.successfulBookings);
        System.out.println("Seats left: " + system.seatsLeft);
        System.out.println("--------------------------------");
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("WITHOUT SYNCHRONIZATION");
        runTest(false);

        System.out.println("WITH SYNCHRONIZATION");
        runTest(true);
    }
}