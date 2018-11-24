package com.hoken;

import java.util.concurrent.locks.ReentrantLock;

import static com.hoken.ThreadColor.*;


/**
 * THREAD STARVATION : when prioritization starves the other threads
 * solution : use of fair locks instead of synchronized block but it
 *      has far more resource use since there is another layer that
 *      guarantees the fairness (FIFO of objects to get lock, not
 *      JVM to awaken threads)
 */
public class Main {
    // private final static Object lock = new Object(); // there is only one instance of lock that the 5 threads will be competing for
    private final static ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) {
        Thread t1 = new Thread(new Worker(ANSI_RED), "Priority 10");
        Thread t2 = new Thread(new Worker(ANSI_BLUE), "Priority 8");
        Thread t3 = new Thread(new Worker(ANSI_YELLOW), "Priority 6");
        Thread t4 = new Thread(new Worker(ANSI_CYAN), "Priority 4");
        Thread t5 = new Thread(new Worker(ANSI_PURPLE), "Priority 2");

        t1.setPriority(10);
        t2.setPriority(8);
        t3.setPriority(6);
        t4.setPriority(4);
        t5.setPriority(2);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }

    public static class Worker implements Runnable {
        private int runCount = 1;
        private String color;

        Worker(String color) {
            this.color = color;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                // synchronized (lock) {...} // replaced by fair locks to combat thread starvation
                lock.lock();
                try {
                    System.out.format(color + "%s: runCount = %d\n", Thread.currentThread().getName(), runCount++);
                    // execute critical section of code
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
