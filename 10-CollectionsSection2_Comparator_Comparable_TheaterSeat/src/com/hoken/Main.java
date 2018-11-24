package com.hoken;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        /*  PART 1: COMPARATOR AND COMPARABLE */
        Theatre theatre = new Theatre("Olympus", 8, 12);
//        theatre.showTheaterLayout();
//        reserveSeatTest(theatre, "C06");
//        reserveSeatTest(theatre, "H11");
//        reserveSeatTest(theatre, "H11");
//        reserveSeatTest(theatre, "Z11");
//        theatre.showSortedByPrice();

        /* PART 2: verify if new copy of theater seats list will impact or not the obj update
            Main() copy, u can add new entries in their own list with Theatre copy unaffected
            what they are affected is that Main copy has copies Theater's seat object reference
            so any changes in their common obj reference will reflect when queried from Theater
            and Main separately. thats why what shud be done is to disable theatre.getSeats()
            from outside world so that only Theater will be able to access the seat list */
        ArrayList<Theatre.Seat> newCopy = new ArrayList<>(theatre.getSeats());
        Theatre.Seat s = theatre.new Seat("A00", 0.0);

        newCopy.add(s);
        System.out.println("Main() copy that is modified when 1 index entry was added.");
        sortByPrice(newCopy);

        System.out.println("\nTheatre's own seat copy remains unmodified after addition of new index entry.");
        theatre.showSortedByPrice();

        System.out.println("Modifying seat@3 (A03) in Main copy: Success, same seat@2 in Theatre copy: was updated by Main");
        /* upon inspection, u can access the object in the list yes, but the class has encapsulated that
            even if u are able to trigger their methods, their methods only returns data. theres no method
            to modify the object. it was setup that only the theatre class is able to update, and for that
            theatre oly cares about its own copy */
        newCopy.get(3).reserve(); // You have successfully reserved seat number A03.
        theatre.reserveSeat("A03"); // A03 is already reserved.
    }

    public static void sortByPrice(ArrayList<Theatre.Seat> seats) {
        seats.sort(Theatre.PRICE_ORDER_COMPARATOR);
        System.out.println("Theatre seats: ...");
        int ctr = 1;
        for (Theatre.Seat s : seats) {
            System.out.print(s.getSeatNumber() + "(" + s.getPrice() + ")\t");
            if ((ctr++ % 12) == 0) {
                // @ every full row count, add new line afterwards
                System.out.println();
            }
        }
    }

    public static void reserveSeatTest(Theatre theatre, String s) {
        if (theatre.reserveSeat(s)) {
            System.out.println("Please pay this amount to complete reservation of chosen seat.");
        } else {
            System.out.println("Please choose a different seat.");
        }
    }
}
