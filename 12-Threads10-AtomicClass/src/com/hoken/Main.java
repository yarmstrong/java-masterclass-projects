package com.hoken;

/**
 * ATOMIC ACTION :
 * cant be suspended in the middle of doing something
 * guarantees that it wont be suspended until it has
 * completed the action
 *
 * DEFAULT JAVA ATOMIC ACTIONS :
 * 1. reading and setting objects
 * 2. reading and setting of primitives aside from long and
 *      double
 * 3. reading and writing volatile variables
 *
 * MEMORY INCONSISTENCIES AND VOLATILE VARIABLES :
 * due to multiple CPU caching, threads running on diff
 * CPU may have different values cached. but using VOLATILE
 * variables, JVM writes an updated value of variable in
 * the main memory right after updating its cache value.
 * guaranteed to the latest value.
 *
 * VOLATILE STILL NEED SYNCHRONIZATION :
 * when the variable (ie ctr++) is updated based on the
 * latest value, it may be possible that u did get the latest
 * but got suspended and when re-awaken, the value u have is
 * already obsolete / stale
 *
 * USE OF VOLATILE :
 * for non-atomic ie long and double variables
 *
 * when only 1 thread changes the value of shared variable,
 * and none of the other threads update its value based on
 * its current value, then use of VOLATILE is enough and doesnt
 * need to use synchronization
 *
 * java.util.concurrent.atomic package supports lock-free,
 * thread-safe programming on single variables: ie AtomicInteger
 * instead of int ctr++ but this classes are only mostly used
 * 1) in counters inside loops that inc or decrements and also
 * 2) in generating sequence of numbers
 */
public class Main {
    public static void main(String[] args) {
        // no example code run
    }
}