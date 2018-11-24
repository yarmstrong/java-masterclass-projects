package com.hoken;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Example1 {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(getIntLBYL() + " is your positive integer.");
        System.out.println(getIntEAFP() + " is your positive integer.");
        sc.close();
    }

    public static int getIntLBYL() {
        // LBYL approach: look before you leap
        // => lots of checking just to be wrong
        System.out.print("LBYL approach: Enter a positive integer:");
        String input = sc.next();
        boolean isInt = true;
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                isInt = false;
                break;
            }
        }
        return isInt ? Integer.parseInt(input) : 0;
    }

    public static int getIntEAFP() {
        // EAFP approach: easier to ask for forgiveness than permission
        // => if it fails, it fails
        System.out.print("EAFP approach: Enter a positive integer:");
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            return 0;
        }
    }
}
