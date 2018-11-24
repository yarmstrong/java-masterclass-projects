package com.hoken;

/**
 * LIVE LOCK ISSUES :
 * 2 threads is not locking each other but they call each other back
 * and forth, and so its just a continuous loop so beware of such
 * happenings and modify code accordingly
 *
 * SLIPPED CONDITION :
 * specific type of a thread interference
 * when thread is suspended between reading and acting upon it
 * ie : checking and setting condition then suspends and then
 * act upon obsolete data like the example of thread
 * interference made before (EOF buffer reading and remove),
 * 1 account 2 withdrawals thread
 *
 * SOLUTION : synchronized blocks OR use locks on critical
 * section of code
 */
public class Main {
    public static void main(String[] args) {
        final Worker w1 = new Worker("Worker 1", true);
        final Worker w2 = new Worker("Worker 2", true);

        final SharedResource sharedResource = new SharedResource(w1);

        new Thread(() -> w1.work(sharedResource, w2)).start();
        new Thread(() -> w2.work(sharedResource, w1)).start();
    }
}
