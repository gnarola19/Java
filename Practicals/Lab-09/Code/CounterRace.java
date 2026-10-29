public class CounterRace {
    static int counter = 0;
    static final int THREADS = 10;
    static final int INCREMENTS = 100000;

    static synchronized void increment() {
        counter++;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    increment();
                }
            }, "Thread-" + (i + 1));

            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Expected counter: " + (THREADS * INCREMENTS));
        System.out.println("Actual counter:   " + counter);
    }
}