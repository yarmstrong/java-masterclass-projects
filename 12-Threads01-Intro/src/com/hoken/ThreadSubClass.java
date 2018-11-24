package com.hoken;

import static com.hoken.ThreadColor.ANSI_BLUE;

public class ThreadSubClass extends Thread {
    @Override
    public void run() {
        System.out.println(ANSI_BLUE + "Hello from 2) " + currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println(ANSI_BLUE + "Outsider thread woke me up.");
        }

        System.out.println(ANSI_BLUE + "Sleep for 3s completed. Im now awake.");
    }
}
