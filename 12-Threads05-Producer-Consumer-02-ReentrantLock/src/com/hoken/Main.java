package com.hoken;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import static com.hoken.Main.EOF;


/**
 WAYS OF SYNCHRONIZATION :

 1. synchronized block
    - synchronized (buffer) {...}
    - blocks are blocking esp if they are a large chunk of block
 2. use of ReentrantLock in replacement of synchronized block
    - lock() must be executed @ the start of the original
      synchronized block
    - unlock() must be executed @ all the exits inside the original
      sync block
 3. use of ReentrantLock with try-finally
    - lock() must be executed @ the start of the original
      synchronized block, then replace the synchronized block with
      try block
    - unlock() must be executed @ the added finally block only,
      must remove the unlock execution in other exits, else error
      IllegalMonitorStateException is thrown bec u cant unlock
      something u haven't lock
 */
public class Main {
    static final String EOF = "EOF";

    public static void main(String[] args) {
        List<String> buffer = new ArrayList<>();
        ReentrantLock bufferLock = new ReentrantLock();

        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_YELLOW, bufferLock);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE, bufferLock);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN, bufferLock);

        Thread p = new Thread(producer);
        Thread c1 = new Thread(consumer1);
        Thread c2 = new Thread(consumer2);

        p.setName("Producer Thread");
        c1.setName("Consumer1 Thread");
        c2.setName("Consumer2 Thread");

        p.start();
        c1.start();
        c2.start();
    }
}

class MyProducer implements Runnable {
    private String color;
    private final List<String> buffer; // shared obj
    private ReentrantLock bufferLock;

    MyProducer(List<String> buffer, String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {
        String[] nums = {"1","2","3","4","5"};
        Random random = new Random();

        for (String num : nums) {
            System.out.println(color + Thread.currentThread().getName() + ": adding... " + num);

            bufferLock.lock(); // gets the lock before modifying buffer
            try {
                buffer.add(num);
            } finally {
                bufferLock.unlock(); // releases the lock for the others
            }

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(color + Thread.currentThread().getName() + ": adding EOF...");

        bufferLock.lock(); // gets the lock before modifying buffer
        try {
            buffer.add("EOF");
        } finally {
            bufferLock.unlock(); // releases the lock for the others
        }
    }
}

class MyConsumer implements Runnable {
    private String color;
    private final List<String> buffer; // shared obj
    private ReentrantLock bufferLock;

    MyConsumer(List<String> buffer,String color, ReentrantLock bufferLock) {
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    @Override
    public void run() {

        int ctr = 0;

        /* a consumer is always in an infinite loop so that whenever it's woken, it can do
           its checking to know if it can now proceed with its process. The consumer always
           checks the 1st element only. bec once processed, it will be removed from the
           buffer. so producer just adds entries, while consumer removes entries. */
        while (true) {

            if (bufferLock.tryLock()) {
                // bufferLock.lock(); // gets the lock before modifying buffer
                try {
                    if (buffer.isEmpty()) {
                        continue; // continue the endless loop til it gets data
                    }

                    System.out.println(color + "Counter when finally given the lock: " + ctr);
                    ctr = 0; //reset

                    if (buffer.get(0).equals(EOF)) {
                        System.out.println(color + Thread.currentThread().getName() + ": reached EOF. Exiting.");
                        break; // ends the loop bec its already the EOF, there is none left in the file
                    } else {
                        System.out.println(color + Thread.currentThread().getName() + ": removes " + buffer.remove(0));
                    }
                } finally {
                    bufferLock.unlock(); // releases the lock for the others
                }
            } else {
                ctr++;
            }
        }
    }
}