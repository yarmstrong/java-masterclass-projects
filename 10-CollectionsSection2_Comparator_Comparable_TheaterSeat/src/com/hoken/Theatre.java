package com.hoken;

import java.util.*;

/**
    NOTE: GOAL OF THIS CLASS:
    1. USE COMPARABLE (seaNumber comparison) for Collections.binarySearch()) ==> done
    2. USE COMPARATOR TO SORT SEAT OBJECTS 2 WAYS (by seatNumber or by pricing
    or combination(arrange by pricing then seatNumber)) ==> price only
    3. USE COMPARATOR (seatNumber comparison) instead for Collections.binarySearch())

    >> FOR SEARCHING SEAT NUMBER IN A BIG LIST, MAKE USE OF JAVA COLLECTIONS BINARY SEARCH:
        MORE EFFICIENT AND U DONT NEED TO CODE IT URSELF
            uses the efficient binary search algo (divide and conquer) with the assumption
            that the list is in order (beware how u apply ur comparison to get the
            desired asc or desc order).
        WHY U HAVE TO SET UP FOR COMPARISON IN A BINARY SEARCH??
            bec the assumption is the list is already in order, the logic will recursively
            decide which side of the middle index should be searched. therefore this search
            is not just checking for equality, but also if greater or less than.

    >> THE USUAL LOGIC FOR THE COMPARE RESULT U NEED TO RETURN:
        if ASC: earlier has lesser value
            return -1 if want to save it early,
            return 1 if later
        if DESC: earlier has bigger value so need to reverse the logic
            return -1 if bigger want to save early,
            return 1 if lesser want to save later

    >> USE OF COMPARABLE
        Collections.binarySearch(this.seats, new Seat(seatNumber, 0.0));
        >> when u know ur only going to sort according to 1 variable only:
        implement Comparable interface's compareTo() method to the class ur
        going to sort. java's default Collections utilities will trigger the
        class' compareTo() method if ur class have the Comparable ability.

    >> USE OF COMPARATOR
        Collections.binarySearch(this.seats, new Seat(seatNumber, 0.0), new ComparatorInnerClass());
        >> when u have to sort according to more than 1 variable only:
        create an inner class implementing Comparator interface's compare method
        in the class where u will do the sorting (ie Theatre class will sort its
        Seat objects NOT Seat class will sort itself).

    >> DIFFERENCE:
        COMPARABLE: in the execution of binarySearch(), ur just proving the
        list and obj to find. internally java will trigger the compareTo()
        for u, thats why u implement it to the Seat class itself

        COMPARATOR: in the execution of binarySearch(), aside from list and
        to find, u need to provide an instance of Comparator. COZ u did not
        implement that in the obj itself. this arg can be a new instance, or
        use anonymous class (not sure if this is lambda expression??)
*/
public class Theatre {
    private final String theatreName;
    private List<Seat> seats = new ArrayList<>(); // Seat class will be defined as an inner class. what is a theatre without its audiences/seats // changed to package-private so Main can play with it
    private final int seatsPerRow;
    private final Comparator<Seat> PRICE_ORDER_ANON;
    private final Comparator<Seat> PRICE_ORDER_LAMBDA;
    static final Comparator<Seat> PRICE_ORDER_COMPARATOR;

    static {
        PRICE_ORDER_COMPARATOR = Comparator.comparingDouble(Seat::getPrice);
    }

    {
        PRICE_ORDER_ANON = new Comparator<Seat>() {
            @Override
            public int compare(Seat s1, Seat s2) {
                return Double.compare(s1.getPrice(), s2.getPrice());
            }
        };
        PRICE_ORDER_LAMBDA = (s1, s2) -> Double.compare(s1.getPrice(), s2.getPrice());
    }

    public Theatre(String theatreName, int numRows, int seatsPerRow) {
        this.theatreName = theatreName;
        this.seatsPerRow = seatsPerRow;

        int lastRow = 'A' + (numRows - 1);
        for (char row = 'A'; row <= lastRow; row++) {
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                double price = 12.00; // normal pricing
                if ((row < 'D') && (seatNum >= 4 && seatNum <= 9)) { // premium seats are from rows A-C seats 4-9
                    price = 14.00;
                } else if ((row > 'F') || (seatNum < 4 || seatNum > 9)) { // discounted seats are all rows edges (1-3 and 10-12) and all seats beyond row F
                    price = 7.00;
                }
                Seat seat = new Seat(row + String.format("%02d", seatNum), price);
                seats.add(seat);
            }
        }
    }

    public String getTheatreName() {
        return theatreName;
    }

    public Collection<Seat> getSeats() {
        return seats;
    }

    public void showTheaterLayout() {
        System.out.println("Theatre seats: ...");
        int ctr = 1;
        /*
            LAMBDA EXPRESSION (1) VS STATEMENT (MANY)
            may be ok to use the list.forEach(), but the lambda can only have final variables so we cant really use it here
            this.seats.forEach(
                (s) -> { System.out.print(s.seatNumber + "(" + s.price + ")\t"); }
                // an expression lambda (minimalist one-liner with no return (if there is) and semicolon to end the one-liner): () -> line
                // a statement lambda has more than 1 line so u need to follow this format: () -> { line1; return line2; }
            );
        */
        for (Seat s : this.seats) {
            System.out.print(s.seatNumber + "(" + s.price + ")\t");
            if ((ctr++ % seatsPerRow) == 0) {
                // @ every full row count, add new line afterwards
                System.out.println();
            }
        }
    }

    public void showSortedByPrice() {
        /* we need to preserve the sort of this.seats by seatNumber ASC order since
            it is used by binarySearch(), we cant mixed up the arrangement by price */
        List<Seat> newCopy = new ArrayList<>(this.seats);
        newCopy.sort(PRICE_ORDER_LAMBDA);
        System.out.println("Theatre seats: ...");
        int ctr = 1;
        for (Seat s : newCopy) {
            System.out.print(s.seatNumber + "(" + s.price + ")\t");
            if ((ctr++ % 12) == 0) {
                // @ every full row count, add new line afterwards
                System.out.println();
            }
        }
    }

    public boolean reserveSeat(String seatNumber) {
        int foundSeat = Collections.binarySearch(this.seats, new Seat(seatNumber, 0.0)); //
        if (foundSeat >= 0) {
            return this.seats.get(foundSeat).reserve();
        } else {
            System.out.println("Theres no such seat.");
            return false;
        }
    }

    class Seat implements Comparable<Seat>{ // changed to package-private so Main can play with it
        private final String seatNumber;
        private double price;
        private boolean isReserved = false;
        /* outer class have access to any private fields, so outer class wont use any getters, but wud be needed outside of this class */

        public Seat(String seatNumber, double price) {
            this.seatNumber = seatNumber;
            this.price = price;
        }

        public String getSeatNumber() {
            return seatNumber;
        }

        public double getPrice() {
            return price;
        }

        public boolean reserve() {
            if (isReserved) {
                System.out.println(seatNumber + " is already reserved.");
                return false;
            }
            System.out.println("You have successfully reserved seat number " + seatNumber + '.');
            isReserved = true;
            return true;
        }

        public boolean cancel() {
            if (isReserved) {
                isReserved = false;
                System.out.println("Reservation for seat number " + seatNumber + " has now been cancelled.");
                return true;
            }
            return false; // nothing to cancel
        }

        @Override
        public int compareTo(Seat s) {
            return this.seatNumber.compareToIgnoreCase(s.seatNumber); // note that the compareTo uses this as the 1st arg vs another arg, not the other way around
        }
    }
}
