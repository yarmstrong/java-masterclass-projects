package com.hoken;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static com.hoken.Main.EOF;


/**
 ArrayBlockingQueue
    - FIFO, dont need to use ReentrantLock
    - bounded array so need to initialize with index number
    - uses put() / take() need to catch InterruptedException
    - replaces add() / remove() bec they throws an exception
        when cant be performed immediately bec other threads
        got the queue locked
    - put() / take() will block when queue is locked, or when
        queue is empty
    - put and remove are thread-safe thats why we can remove
        the ReentrantLock but can still be interrupted thus
        the need to catch InterruptedException
    - remove() and peek() dont need an index, bec it is a FIFO
        no need to inform it what it needs to do
    - peek() doesnt block when the queue is empty but rahther
        returns null and also lets us check the next element
        without removing it from the queue (needed for EOF to
        stay in the queue)
 */
public class Main {
    static final String EOF = "EOF";

    public static void main(String[] args) {
        ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<>(6); // bounded not like ArrayList
        ReentrantLock bufferLock = new ReentrantLock();

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_YELLOW);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN);

        Thread p = new Thread(producer);
        Thread c1 = new Thread(consumer1);
        Thread c2 = new Thread(consumer2);

        p.setName("Producer Thread");
        c1.setName("Consumer1 Thread");
        c2.setName("Consumer2 Thread");

        executorService.execute(p); // overrides given setName => pool-1-thread-1
        executorService.execute(c1); // overrides given setName => pool-1-thread-2
        executorService.execute(c2); // overrides given setName => pool-1-thread-3

        Future<String> future = executorService.submit(() -> {
            System.out.println(ThreadColor.ANSI_GREEN + "from the callable class");
            return "This is the callable result.";
        });

        try {
            System.out.println(ThreadColor.ANSI_GREEN + future.get());
            /* this will only be executed when there is available thread to use.
                for this case where we only have 3 threads in the pool and all
                3 of them is used for producer and consumer, once 1 of them has
                finished, ES will allocate that free thread to the callable */
        } catch (InterruptedException e) {
            System.out.println("future callable got interrupted.");
        } catch (ExecutionException e) {
            System.out.println("future callable execution went wrong.");
        }

        executorService.shutdown();
    }
}

class MyProducer implements Runnable {
    private String color;
    private final ArrayBlockingQueue<String> buffer; // shared obj

    MyProducer(ArrayBlockingQueue<String> buffer, String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        String[] nums = {"1","2","3","4","5"};
        Random random = new Random();

        for (String num : nums) {
            System.out.println(color + Thread.currentThread().getName() + ": adding... " + num);
            try {
                buffer.put(num);
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(color + Thread.currentThread().getName() + ": adding EOF...");
        try {
            buffer.put("EOF");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyConsumer implements Runnable {
    private String color;
    private final ArrayBlockingQueue<String> buffer; // shared obj

    MyConsumer(ArrayBlockingQueue<String> buffer,String color) {
        this.buffer = buffer;
        this.color = color;
    }

    @Override
    public void run() {
        /* a consumer is always in an infinite loop so that whenever it's woken, it can do
           its checking to know if it can now proceed with its process. The consumer always
           checks the 1st element only. bec once processed, it will be removed from the
           buffer. so producer just adds entries, while consumer removes entries. */
        while (true) {
            synchronized (buffer) {
                try {
                    if (buffer.isEmpty()) {
                        continue; // continue the endless loop til it gets data
                    }

                    if (buffer.peek().equals(EOF)) { // TODO: 11/12/18 debug: buffer.isEmpty(0 and buffer.peek() are both thread-safe but the call in between can be interrupted. so the buffer may not be empty so it does the peek, but before it got there, this thread was suspended and the other thread deleted that said buffer entry. so when this thread re-awaken, buffer is already empty and so will throw a NullPointerException when the .equals() was called. so the fix is to add sync block to the inside of while loop
                        System.out.println(color + Thread.currentThread().getName() + ": reached EOF. Exiting.");
                        break; // ends the loop bec its already the EOF, there is none left in the file
                    } else {
                        System.out.println(color + Thread.currentThread().getName() + ": removes " + buffer.take());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}