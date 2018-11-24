package com.hoken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // testCharIntValues();
        Theatre theatre = new Theatre("Olympus", 8, 12);
//        theatre.getTheaterSeats();
//        reserveSeatTest(theatre, "C06");
//        reserveSeatTest(theatre, "H11");
//        reserveSeatTest(theatre, "H11");
//        reserveSeatTest(theatre, "Z11");

        List<Theatre.Seat> seatCopy = new ArrayList<>(theatre.seats); // a shallow copy, copied objects are the same original seat objects

        Collections.shuffle(seatCopy);
        System.out.print("SHUFFLE:");
        printSeats(seatCopy);

        System.out.println("Min value: " + Collections.min(seatCopy).getSeatNumber());
        System.out.println("Max value: " + Collections.max(seatCopy).getSeatNumber());

        Collections.sort(seatCopy);
        System.out.print("SORTED:");
        printSeats(seatCopy);

        Collections.reverse(seatCopy);
        System.out.print("REVERSE:");
        printSeats(seatCopy);

        letMeSortItMyWay(seatCopy);
        System.out.print("SORTED:");
        printSeats(seatCopy);
    }

    public static void reserveSeatTest(Theatre theatre, String s) {
        if (theatre.reserveSeatBinaryDissected(s)) {
            System.out.println("Please pay this amount to complete reservation of chosen seat.");
        } else {
            System.out.println("Please choose a different seat.");
        }
    }

    public static void printSeats(List<Theatre.Seat> list) {
        System.out.println("Theatre seats: ...");
        int ctr = 1;
        for (Theatre.Seat s : list) {
            System.out.print(s.getSeatNumber() + " ");
            if ((ctr++ % 12) == 0) {
                // @ every full row count, add new line afterwards
                System.out.println();
            }
        }
    }

    public static void letMeSortItMyWay(List<? extends Theatre.Seat> list) {
        for (int i = 0; i < list.size()-1; i++) {
            for (int j = i+1; j < list.size(); j++) {
                if ( (list.get(i).getSeatNumber().compareToIgnoreCase(list.get(j).getSeatNumber())) > 0 ) {
                    Collections.swap(list, i, j);
                }
            }
        }
    }

    public static void testCharIntValues() {
        int numRows = 1;
        int seatsPerRow = 10;
        int lastRow = 'A' + (numRows - 1); // need 10 seats and seat1 starts @ A so u only need the other 9seats
        for (char row = 'A'; row <= lastRow; row++) { // starts @ A and shud end til the calculated lastSeat (ensured lastSeat is the 10th)
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                System.out.println(row + String.format("%02d", seatNum));
            }
        }
    }
}
