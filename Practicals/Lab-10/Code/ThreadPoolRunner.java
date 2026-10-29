import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRunner {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++) {
            final int taskId = i;

            pool.execute(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Task " + taskId + " started by " + threadName);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                System.out.println("Task " + taskId + " completed by " + threadName);
            });
        }

        pool.shutdown();

        if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
            pool.shutdownNow();
            System.out.println("Timeout: unfinished tasks interrupted.");
        } else {
            System.out.println("All tasks completed.");
        }
    }
}