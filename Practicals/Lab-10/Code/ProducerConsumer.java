import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {

    static class Buffer {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity;

        Buffer(int capacity) {
            this.capacity = capacity;
        }

        public synchronized void produce(int value) throws InterruptedException {
            while (queue.size() >= capacity) {
                wait();
            }

            queue.add(value);
            System.out.println("Produced: " + value);
            notifyAll();
        }

        public synchronized int consume() throws InterruptedException {
            while (queue.isEmpty()) {
                wait();
            }

            int value = queue.remove();
            System.out.println("Consumed: " + value);
            notifyAll();
            return value;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer(3);
        int totalItems = 10;

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) {
                    buffer.produce(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= totalItems; i++) {
                    buffer.consume();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("All items produced and consumed.");
    }
}