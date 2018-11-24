package com.hoken;

/**
 DEADLOCKS :
    - happens when different threads try to get objects in different order
    - ie in the example, Thread1 locks lock1 then lock2 while Thread2 locks lock2
        and then lock1. different order of locking so they will end in a deadlock

 PREVENTION :
 1. make thread's retrieval of locks to be of the same order (2 threads running
    both Thread1 runnable, obviously they have the same order of getting lock1
    first and then lock2)
 */
public class Main {

    public static Object lock1 = new Object();
    public static Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread1().start();
        new Thread1().start();
    }

    private static class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (lock1) {
                System.out.println("Thread 1: has lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
                System.out.println("Thread 1: waiting for lock2");
                synchronized (lock2) {
                    System.out.println("Thread 1 has both lock1 and lock2");
                }
                System.out.println("Thread 1: Released lock2");
            }
            System.out.println("Thread 1: Released lock1. Exiting...");
        }
    }

    private static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (lock2) {
                System.out.println("Thread 2: has lock2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
                System.out.println("Thread 2: waiting for lock1");
                synchronized (lock1) {
                    System.out.println("Thread 2 has both lock2 and lock1");
                }
                System.out.println("Thread 2: Released lock1");
            }
            System.out.println("Thread 2: Released lock2. Exiting...");
        }
    }
}
