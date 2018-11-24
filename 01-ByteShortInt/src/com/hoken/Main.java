package com.hoken;

/**
 * this is for javadoc
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE); // 2147483647
        System.out.println("Integer.MIN_VALUE = " + Integer.MIN_VALUE); // -2147483648

        byte b = 127;
        short s = 2345;
        int i = 23;
        short ss = (short) (b + s + i); // numbers by default changes to int so to be able to assign it to short, need to do casting fjskfgjgskfflf
        System.out.println(ss);
        long total = 50_000L + 10 * (b + i + s); // int to long, dont have to cast since int will fit in long
        System.out.println(total);
    }
}
