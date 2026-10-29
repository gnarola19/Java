import java.util.concurrent.atomic.AtomicLong;

public class AdvancedArraySum {
    static final int SIZE = 1_000_000;
    static final int THREADS = 8;

    static long unsafeTotal = 0;
    static long safeTotal = 0;
    static final AtomicLong atomicTotal = new AtomicLong(0);

    static final Object lock = new Object();
    static int[] numbers = new int[SIZE];

    static class SumWorker implements Runnable {
        private final int start;
        private final int end;
        private final int mode;

        SumWorker(int start, int end, int mode) {
            this.start = start;
            this.end = end;
            this.mode = mode;
        }

        public void run() {
            long localSum = 0;

            for (int i = start; i < end; i++) {
                localSum += numbers[i];
            }

            if (mode == 1) {
                unsafeTotal += localSum;
            } else if (mode == 2) {
                synchronized (lock) {
                    safeTotal += localSum;
                }
            } else {
                atomicTotal.addAndGet(localSum);
            }
        }
    }

    static long runTest(int mode) throws InterruptedException {
        Thread[] threads = new Thread[THREADS];
        long startTime = System.nanoTime();

        for (int i = 0; i < THREADS; i++) {
            int start = i * SIZE / THREADS;
            int end = (i + 1) * SIZE / THREADS;

            threads[i] = new Thread(new SumWorker(start, end, mode));
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long elapsed = System.nanoTime() - startTime;

        if (mode == 1) {
            System.out.println("Without synchronization: " + unsafeTotal);
        } else if (mode == 2) {
            System.out.println("With synchronization: " + safeTotal);
        } else {
            System.out.println("Using AtomicLong: " + atomicTotal.get());
        }

        System.out.printf("Execution time: %.3f ms%n", elapsed / 1_000_000.0);
        return elapsed;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = 1;
        }

        System.out.println("Expected sum: " + SIZE);
        System.out.println();

        runTest(1);
        runTest(2);
        runTest(3);
    }
}