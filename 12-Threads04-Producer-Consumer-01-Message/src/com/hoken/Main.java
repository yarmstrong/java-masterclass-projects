package com.hoken;

import java.util.Random;

/* SYNCHRONIZATION

   RE-ENTRANT => a thread can acquire an obj's lock
   it already owns (like the thread enters 1 synchronized method, and then
   enters another synchronized one for the same obj, then the thread will
   just be re-acquiring the lock it already has)

   CRITICAL SECTION => the block of codes referencing the shared resource

   THREAD-SAFE => the developer has synchronized all the critical sections
   within the code to prevent THREAD INTERFERENCE for u
*/
public class Main {
    /**
     * STORY OF THE PROCESS:
     *
     1. MESSAGE : an object that is shared between READER and WRITER objects
         which provides methods that write() and read() to update its instance
         variable.
     2. WRITER : holds a list of string for sout will call MESSAGE.write().
         MESSAGE will then update its message component and denotes message
         tray is no longer empty (empty = false) and eligible for reading.
         Once list of string is all used up, write the msg "Finished"
     3. READER : will be informed (in some way via Thread) that message tray
         is not empty so it call for MESSAGE.read() to get the new message.
         Once triggered, MESSAGE will have to denote that its message tray
         is now empty (empty = true). Back to READER, it will just sout
         the retrieved message. READER should be open for reading as long
         as the message received is not "Finished"

     Basically if we are not using threads, we need to run this 3 objects
     in a sequential manner. ie: Writer will call Message.write(), Message
     updates its data, calls Reader to print the new data.

     We need Reader and Writer to be both running at the same time (thus
     implementing Runnable). Writer thread will activate when Message tray
     is empty and Reader will activate when Message tray is not empty.
     However we cant tell JVM when to activate the other's thread. All we
     can do is if JVM activates Writer thread and message tray is still
     full (not empty), then it cant write a new message. Same with Reader.
     If reader thread was activated and Writer has not made a new message
     (message tray is empty), then it cant read new message. That is the
     logic behind the while() loops in the Message.write() and read().
     Message obj holds the message tray info thus it is the one doing
     the while loop. It will not update its message data (WRITE calls)
     when READ is not yet completed (message tray is full, not empty).
     And it will not send new message to READ if WRITE has not done its
     job (fill the message tray, not empty)

     empty, eligible for WRITE only
     !empty, eligible for READ only

     The Random obj added to the RunnableS is to randomize the forced
     Thread.sleep() so that when they have done their job, Writer thread
     has written a msg, do random sleep, which then wakes up Reader thread
     to do its job of reading, once it read a msg it will do random sleep
     and so the process repeats itself

     DEADLOCK : the message tray update is being modified by 2 threads.
     and both are waiting for each other.

     DEADLOCK FIX : use of wait, notify and notifyAll
     where wait calls are usu inside a while() loop checking for a
     particular rule. bec while u have re-awoken from wait, it doesnt
     mean u already have the data u need so its feasible ur going to
     call wait again

     ATOMIC METHODS : JVM CANNOT SUSPEND IN THE MIDDLE OF EXECUTION
     1. reading / writing object variables ie: obj1.equals(obj2)
     2. reading / writing primitive variables except long and double
     ie myInt.equals(10) cant be suspended
        myDouble.equals(10.0) can be
     3. reading / writing volatile objects

     Collections ArrayList is not synchronized.
     Vector class is synchronized so its thread-safe.
     *
     */
    public static void main(String[] args) {
        Message message = new Message();
        Thread t1 = new Thread(new Writer(message));
        Thread t2 = new Thread(new Reader(message));

        t1.setName("Writer Thread");
        t2.setName("Reader Thread");

        t1.start();
        t2.start();
    }
}

class Writer implements Runnable {
    private Message message;

    public Writer(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        String[] messages = {
            "Statement 1",
            "Statement 2",
            "Statement 3",
            "Statement 4",
            "Statement 5",
        };

        Random random = new Random();

        for (String msg : messages) {
            message.write(msg); // Message to update its instance variables based on the info passed by the Writer
            try {
                System.out.println(Thread.currentThread().getName() + " doing forced sleeping, not by JVM.");
                Thread.sleep(random.nextInt(2000)); // sleep randomly up to 2s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        message.write("Finished");
    } // when this ends, runnable will end too
}

class Reader implements Runnable {
    private Message message;

    public Reader(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        Random random = new Random();

        for (String newMessage = message.read(); // initialization of newMessage
             !newMessage.equals("Finished"); // returns boolean so still works, ends loop (no longer triggers statement3 once "Finished" is received)
             newMessage = message.read() // newMessage data will be obtained from this (moves the loop forward)
        ) {
            System.out.println(newMessage);
            try {
                System.out.println(Thread.currentThread().getName() + " doing forced sleeping, not by JVM.");
                Thread.sleep(random.nextInt(2000)); // sleep randomly up to 2s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } // when this ends, runnable will end too
}

class Message {
    private String message;
    private boolean empty = true; // the message tray

    /* wants consumer to read each msg b4 write another one */

    /* method to be used by the Consumer */
    public synchronized String read() {

        /* RULE: empty, eligible for WRITE only; !empty, eligible for READ only :
        for read process to proceed, need message tray to be full, so while empty,
        make this thread sleep (the READER thread) so the other thread will run
        and write/fill in the message tray */

        while(empty) { // loop til we got a msg to read (cant read without msg)
            // DEADLOCK FIX
            try {
                wait(); // an Object method, not Thread's
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        empty = true; // i read the msg, will be sending this to Reader afterwards so message tray is now empty
        notifyAll(); // executed only after we have updated 'empty'
        return message;
    }

    /* method to be used by the Producer */
    public synchronized void write(String message) {

        /* RULE: empty, eligible for WRITE only; !empty, eligible for READ only :
        for write process to proceed, need message tray empty, so while !empty,
        make this thread sleep (the READER thread) so the other thread will run
        and read/empty out the message tray */

        while(!empty) { // loop while not empty (cant write)
            // DEADLOCK FIX
            try {
                wait(); // an Object method, not Thread's
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        empty = false; // i receive a msg from Writer, wrote it in my data, so now message tray is now not empty
        this.message = message;
        notifyAll(); // executed only after we have updated 'empty'
    }
}
