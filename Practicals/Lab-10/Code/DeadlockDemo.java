public class DeadlockDemo {

    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    private static void pause() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static void demonstrateDeadlock() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Thread 1 acquired Lock A");
                pause();

                synchronized (lockB) {
                    System.out.println("Thread 1 acquired Lock B");
                }
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("Thread 2 acquired Lock B");
                pause();

                synchronized (lockA) {
                    System.out.println("Thread 2 acquired Lock A");
                }
            }
        }, "Thread-2");

        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        t2.start();

        Thread.sleep(1000);

        System.out.println("Thread 1 state: " + t1.getState());
        System.out.println("Thread 2 state: " + t2.getState());
        System.out.println("Opposite lock ordering can cause deadlock.");
    }

    static void demonstrateFix() throws InterruptedException {
        Object lockA = new Object();
        Object lockB = new Object();

        Thread t1 = new Thread(() -> transfer(lockA, lockB), "Transfer-A-B");
        Thread t2 = new Thread(() -> transfer(lockA, lockB), "Transfer-B-A");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Fixed version completed without deadlock.");
    }

    static void transfer(Object firstLock, Object secondLock) {
        synchronized (firstLock) {
            synchronized (secondLock) {
                System.out.println(Thread.currentThread().getName()
                        + " completed its operation.");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length > 0 && args[0].equals("deadlock")) {
            demonstrateDeadlock();
        } else {
            demonstrateFix();
        }
    }
}