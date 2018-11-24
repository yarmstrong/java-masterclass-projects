package com.hoken;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Theatre {
    private final String theatreName;
    List<Seat> seats = new LinkedList<>(); // Seat class will be defined as an inner class. what is a theatre without its audiences/seats // changed to package-private so Main can play with it
    private final int seatsPerRow;

    public Theatre(String theatreName, int numRows, int seatsPerRow) {
        this.theatreName = theatreName;
        this.seatsPerRow = seatsPerRow;

        int lastRow = 'A' + (numRows - 1);
        for (char row = 'A'; row <= lastRow; row++) {
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                Seat seat = new Seat(row + String.format("%02d", seatNum));
                seats.add(seat);
            }
        }
    }

    public String getTheatreName() {
        return theatreName;
    }

    public boolean reserveSeatSlower(String seatNumber) { // A10
        Seat reqSeat = null;
        for (Seat s : seats) {
            if (s.getSeatNumber().equals(seatNumber)) { // A10 == A10
                reqSeat = s; // this objected is the requested seat
                break;
            }
        }
        if (reqSeat  == null) { // this seat is not existing / theres no such seat
            System.out.println("Theres no such seat.");
            return false;
        }

        return reqSeat.reserve(); // let the seat class do the reserve, theatre class only validated if seat is vacant
    }

    public boolean reserveSeatBinary(String seatNumber) {
        /*
            uses the efficient binary search algo when the list is in the asc or desc order
            if List<T> is ASC: T's compareTo() method is default logic,
                -1 if early, 1 if later
            if DESC: earlier is the bigger so need to reverse the logic of T's compareTo(),
                -1 if bigger, 1 if lesser
        */
        int foundSeat = Collections.binarySearch(this.seats, new Seat(seatNumber), null);
        if (foundSeat >= 0) {
            return this.seats.get(foundSeat).reserve();
        } else {
            System.out.println("Theres no such seat.");
            return false;
        }
    }

    public boolean reserveSeatBinaryDissected(String seatNumber) {
        /*
            dissecting the java logic for the binary search (divide and conquer)
            since List is expected to be in order, getting the middle index will
            determine the left and right side to be used. recursive til found.
        */
        int low = 0;
        int high = seats.size()-1; // coz this is an ArrayList starts @0

        while (low <= high) {
            System.out.print(". ");
            int mid = (low + high) / 2; // note that we dont do (mid+1) since the index starts @ 0
            int comp = this.seats.get(mid).seatNumber.compareToIgnoreCase(seatNumber);

            if (comp < 0) { // mid entry is smaller than our target so target must be on the right side, update the low index to be mid+1, since we already know that we can exclude mid index, we just move the new low to 1 more to the right
                low = mid + 1;
            } else if (comp > 0) { // mid entry is bigger that target so target must be on the left side, update the high index to be mid-1, since we already know that we can now exclude mid index, we just move the new high 1 less to the left side
                high = mid - 1;
            } else {
                return this.seats.get(mid).reserve();
            }
        }
        System.out.println("Theres no such seat.");
        return false;
    }

    public void getTheaterSeats() {
        System.out.println("Theatre seats: ...");
        int ctr = 1;
        for (Seat s : this.seats) {
            System.out.print(s.getSeatNumber() + " ");
            if ((ctr++ % seatsPerRow) == 0) {
                // @ every full row count, add new line afterwards
                System.out.println();
            }
        }
    }

    class Seat implements Comparable<Seat>{ // changed to package-private so Main can play with it
        private final String seatNumber;
        private boolean isReserved = false;

        public Seat(String seatNumber) {
            this.seatNumber = seatNumber;
        }

        public String getSeatNumber() {
            return seatNumber;
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
            return this.seatNumber.compareToIgnoreCase(s.getSeatNumber()); // note that the compareTo uses this as the 1st arg vs another arg, not the other way around
        }
    }
}
