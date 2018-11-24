package com.hoken;

import static com.hoken.ThreadColor.*;

/* Implementing Runnable is more flexible than extending the Thread class
   Also, most APIs are calling for a Runnable argument, not Thread

   Thread.start() not .run() => need to call .start() first for JVM to
       create a new heap for u while .run() will just trigger the
       execution of the modified .run() in the very current heap

   U can only start a thread once. u cant start it again. like how wud u
       even know it completed running in the 1st place??!!!
*/
public class Main {
    public static void main(String[] args) {
        System.out.println(ANSI_PURPLE + "Hello from 1) Main thread");

        /* extending from Thread class: explicit subclassing, and then starting
           this thread instance */
        Thread t = new ThreadSubClass();
        t.setName("== Thread Subclass ==");
        t.start();

        /* extending from Thread class: anonymous subclassing, and then starting
           this thread instance */
        new Thread() {
            @Override
            public void run() {
                System.out.println(ANSI_CYAN + "Hello from 3) anonymous Thread()");
            }
        }.start();

        /* passing an anonymous Runnable class (long process, it would be a Runnable
           subclass instance) to a Thread instance, and then start the said thread */
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(ANSI_GREEN + "Hello from 4) anonymous Runnable()");
            }
        }).start();

        /* passing an lambda Runnable instead of an anonymous Runnable class to a
           Thread instance, and then start the said thread */
        new Thread(() -> System.out.println(ANSI_RED + "Hello from 5) Runnable() lambda expression"))
                .start();

        System.out.println(ANSI_PURPLE + "Hello again from 1) Main thread");
    }
}
