package com.hoken;

import java.util.concurrent.Callable;

import static com.hoken.ThreadColor.*;

/* ISSUES IN RUNNING MULTIPLE THREADS THAT PLAYS WITH THE SAME OBJECT
   story of husband and wife sharing 1 account number
   scene1: hubby queried the current balance intending to withdraw
           but jvm chose that time for his thread to sleep.
   scene2: wife-y queried the current balance and had gotten the same
           amount as his hubby. and luckily, jvm allowed her to
           complete her transaction and so now the balance is 0.
   scene3: hubby's thread was activated again but then the balance
           he have now is no longer correct. and so his transaction
           will fail and he's none the wiser.


   HEAP: the application memory that all threads share.
   A thread has its own thread stack and that is the memory that only
      that thread can access. its thread stack will be the stacking of
      methods run by code u put in its run() method).
   Threads dont share thread stacks. Only their own run() is the starter
      in their own stacks. BUT THEY SHARE THE SAME APPLICATION HEAP.
      and all objects and their instance variables are created in the
      heap.

   for CountdownInstanceVar object, when it was created, its timer which is
      an instance variable is created in the heap and accessible both
      to Thread 1 and 2. So change in thread 1 will impact how thread
      2 will process the countdown timer. it cud be that thread 2 read
      the timer @10, then thread 1 runs and run the timer up to @0. so
      when thread 2 activates, it will only be able to print 10 and
      then end of timer already coz thread 1 changed the timer to @0
      already.
   for CountdownLocalVar object, when it was created, there is no
      instance variable but the timer method has its own. this local
      variable is not created in the heap but created on the thread
      stack. so Thread 1 and 2 have their own separate copies of the
      timer and they cant manipulate each others timer.

   called THREAD INTERFERENCE or RACE CONDITION to who will be the first
   to write (update) the shared resource

   fight with SYNCHRONIZATION:
   when a thread is executing a synchronized method, other threads calling
   the same synchronized method (or any other synchronized method in that
   class) will be suspended til the winning thread exits it

   either 1) u synchronized obj's methods that modifies the instance var
   u want protected OR surround multiple statements with the synchronized
   block

   SYNCHRONIZATION => is RE-ENTRANT => a thread can acquire an obj's lock
   it already owns (like the thread enters 1 synchronized method, and then
   enters another synchronized one for the same obj, then the thread will
   just be re-acquiring the lock it already has)

   CRITICAL SECTION => the block of codes referencing the shared resource

   THREAD-SAFE => the developer has synchronized all the critical sections
   within the code to prevent THREAD INTERFERENCE for u
*/
public class Main {
    public static void main(String[] args) {
        Countdown countdown1 = new CountdownInstanceVar(); // modifies unique instance variable
        Countdown countdown2 = new CountdownInstanceVar(); // modifies unique instance variable

        Countdown onlyOneInTheWorldCountdown = new CountdownInstanceVar();
        /* its non-sense to apply multiple threads for diff objects. it defeats the purpose
           of multi-threading as what we-ve done above. objects must be allowed for multiple
           threads to change it but application must prevent race condition VIA SYNCHRONIZATION
           */

        Thread t1 = new CountdownThread(onlyOneInTheWorldCountdown);
        t1.setName("Thread 1");

        Thread t2 = new CountdownThread(onlyOneInTheWorldCountdown);
        t2.setName("Thread 2");

        t1.start();
        t2.start();
    }
}

abstract class Countdown {
    abstract void doCountdown();
}

class CountdownInstanceVar extends Countdown {
    private int i;

    public void doCountdown() { // not protected from RACE CONDITION
    // public synchronized void doCountdown() { // preventing RACE CONDITION
        String color;

        /* u cant put this on constructor bec ur all creating objects
           for this class in the Main thread which u then pass into
           a CountdownThread class. so only when a particular thread
           is executing, will it call for this method, and so the
           appropriate thread name will be obtained and not Main */
        switch(Thread.currentThread().getName()) {
            case "Thread 1":
                color = ANSI_CYAN;
                break;
            case "Thread 2":
                color = ANSI_PURPLE;
                break;
            default:
                color = ANSI_GREEN;
                break;
        }

        /* surrounding the block of statements that leads to RACE CONDITION
           with synchronized() block. its non-sense to use local var, u
           shud use the instance variable. NOTE: however String color here,
           being a local var, sometimes used for synchronization. String
           being an immutable class, all instances created by JVM is being
           collected in a String pool and may possibly be used. the only
           reason its non-sense to use String here, is bec it is changed
           based on the current thread, which is the expected logic. the
           only issue we have is in the shared increment of the instance
           var i. but since i is a primitive, use 'this' and it will work
           since 'this' refers to the object itself. meaning synchronize
           this block of statements in respect to me the object

           the synchronization holds the lock of 'this' obj, when u
           synchronize the non-static method class or use synchronize(this).
           u can also synchronize static methods and objects, only that the
           lock belongs to the class and not the obj */
        synchronized (this) {
            for (i = 10; i >= 0; i--) {
                System.out.println(color + Thread.currentThread().getName()
                    + ": " + i);
            }
        }


    }
}

class CountdownLocalVar extends Countdown {
    public void doCountdown() {
        String color;

        /* u cant put this on constructor bec ur all creating objects
           for this class in the Main thread which u then pass into
           a CountdownThread class. so only when a particular thread
           is executing, will it call for this method, and so the
           appropriate thread name will be obtained and not Main */
        switch(Thread.currentThread().getName()) {
            case "Thread 1":
                color = ANSI_CYAN;
                break;
            case "Thread 2":
                color = ANSI_PURPLE;
                break;
            default:
                color = ANSI_GREEN;
                break;
        }

        /* i is instantiated in this method only. its not an instance
           variable */
        for (int i = 10; i >= 0; i--) {
            System.out.println(color + Thread.currentThread().getName()
                    + ": " + i);
        }
    }
}

class CountdownThread extends Thread {
    private Countdown countdown;

    public CountdownThread(Countdown countdown) {
        this.countdown = countdown;
    }

    @Override
    public void run() {
        countdown.doCountdown();
    }
}