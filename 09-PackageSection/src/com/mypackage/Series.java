package com.mypackage;

public class Series {
    public static long nSum(int n) {
        return n * (n+1) / 2;
    }

    public static long factorial(int n) {
        if (n == 0)
            return 1;
        long fact = 1;
        System.out.print(n + "! :");

        /*
            the for loop that starts from top
            6 * 5 * 4 * 3 * 2 * 1
            goal is to just traverse not including 1, but is not good for outputting each loop result
            System.out.print("factorial(" + n + "): " + 1);
            for (int i = n; i > 1; i--) {
                fact *= i;
                System.out.print(" " + fact);
            }
        */
        for (int i = 1; i <= 6; i++) {
            fact *= i;
            System.out.print(" " + fact);
        }
        System.out.println();
        return fact;
    }

    public static long fibonacci(int n) {
        if (n == 0) {
            return 0;
        } else if (n ==1) {
            return 1;
        }
        long prev = 0;
        long next = 1;
        long curr = 0;
        System.out.print("fib(" + n + "): " + prev + " " + next);
        for (int i = 2; i <= n; i++) { // fibonacci has default 0: 1 and 1: 1 and we start the summation using these 2, starting @2...
            curr = prev + next;
            prev = next;
            next = curr;
            System.out.print(" " + curr);
        }
        System.out.println();
        return curr;
    }
}

