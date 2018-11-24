package com.hoken;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Example2 {
    public static void main(String[] args) {
        int result = 0;
        try {
            result = divide();
        } catch (ArithmeticException | NoSuchElementException e) {
            System.out.println("error from main() method");
            throw e;
        }
        System.out.println(result);
        /* the way Exceptions and sout debug msgs is ordered:
            1. ur custom sout debug msgs will happen first
               according to the order of ur method calling
            2. when Exception was thrown and eventually was
               not handled, it will fall to main() method
               to show the Exception error to the world via
               e.printStackTrace() and in this case, the error
               will be all Exception related notes, but follows
               the stack trace*/
    }

    private static int divide() {
        try {
            /* if the scanner's error is not InputMismatchException, then
                will return to this code block and try to catch whats
                the error thrown
               else if will move to the division part which is the
                other possible source of error
               the order of caught exceptions is not important here bec
                getInt() the 1st code will throw NoSuchElementException
                while the division afterwards corresponds only to the
                error ArithmeticException */
            int x = getInt();
            int y = getInt();
            System.out.println("x: " + x + ", y: " + y);
            return x / y;
        } catch (ArithmeticException e) {
            System.out.println("error from divide() method");
            throw new ArithmeticException("Error: cant divide with 0");
        } catch (NoSuchElementException e) {
            System.out.println("error from divide() method");
            throw new NoSuchElementException("Error: no suitable input");
        }
    }

    private static int getInt() {
        Scanner s = new Scanner(System.in);
        System.out.print("Please enter an integer: ");
        while (true) {
            try {
                return s.nextInt();
            } catch (InputMismatchException e) {
                /* this only tries to catch InputMismatchException error, meaning
                    that if u output letters, this will catch it and will
                    reprimand you and ake u enter an input again*/
                System.out.println("Please enter digits from 0 thru 9 only");
                s.nextLine();
            }
        }
    }
}
