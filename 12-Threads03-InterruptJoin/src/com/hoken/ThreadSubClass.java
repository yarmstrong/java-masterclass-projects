package com.hoken;

import static com.hoken.ThreadColor.ANSI_BLUE;

public class ThreadSubClass extends Thread {
    @Override
    public void run() {
        try {
            System.out.println(ANSI_BLUE + "Hello from 1st thread. Sleeping for 3s right now.");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(ANSI_BLUE + "Outsider thread woke me up.");
            return; // once interrupted, return will immediately terminate this thread
        }

        System.out.println(ANSI_BLUE + "Sleep completed. Im now awake, and have completed my task. Now im terminating.");
    }
}
