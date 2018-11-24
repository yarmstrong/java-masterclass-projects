package com.hoken;

import static com.hoken.ThreadColor.*;

/* THREAD INTERRUPTION
   => we interrupt a thread when we want it to stop doing what it's
   currently doing ie we want to terminate it.

   Two ways for a thread to notice it was interrupted:
   1) catching InterruptedException for those API throwing such
      exception in the .run() method and executing return to exit
   2) when u have no code throwing InterruptedException, then u
      need to call interrupted method periodically to check if it
      has been interrupted ? how ???

   THREAD JOINING
   => thread1 needs to complete/terminate 1st before the joined
   thread2 will wake up and be able eligible for running
   => this is used instead of making thread2 check periodically
   if thread1 has been completed or terminated

   ei: thread1 calls thread2.join() inside its block => means
   that thread1 is going to stop and wait for thread2 to finish
   or terminate for thread1 to join. its like saying,

   thread1: imma call thread2.join(). thread1 is to join thread2
            after he completes.
   thread2: wakey wakey thread1, im already completed. continnue
            what ur doing now
*/
public class Main {
    public static void main(String[] args) {
        System.out.println(ANSI_PURPLE + "Hello from Main thread");

        // need to save thread instance to do something to it later
        Thread t = new ThreadSubClass();
        t.start();

        new Thread() {
            @Override
            public void run() {
                System.out.println(ANSI_CYAN + "Hello from 2nd thread");
            }
        }.start();

        Thread runnableThread = new Thread(() -> {
            try {
                System.out.println(ANSI_RED + "Hello from 3rd thread. Now im calling 1st thread for me to join, kinda like notify me if u done already. so now im in waiting.");
                t.join(2000);
                System.out.println(ANSI_RED + "This is either 1st thread is terminated or timed-out so Im running myself again. No more task. Im terminating.");
            } catch (InterruptedException e) {
                System.out.println(ANSI_RED + "I couldn't wait after all. i was interrupted.");
            }
        });
        runnableThread.start();
        // t.interrupt();

        System.out.println(ANSI_PURPLE + "Hello again from Main thread");
    }
}
