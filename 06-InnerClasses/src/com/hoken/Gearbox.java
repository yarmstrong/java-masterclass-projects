package com.hoken;

import java.util.ArrayList;
import java.util.List;

public class Gearbox {
    private List<Gear> gears;
    private int maxGears;
    private int currentGearNumber = 0;
    private boolean clutchIsIn;

    public Gearbox(int maxGears) {
        this.maxGears = maxGears;
        this.gears = new ArrayList<>();
        Gear neutral = new Gear(0, 0.0);
        this.gears.add(neutral);
    }

    public void operateClutch(boolean in) {
        this.clutchIsIn = in;
    }

    public void addGear(int number, double ratio) {
        if (number > 0 && number <= maxGears)
            this.gears.add(new Gear(number, ratio));
    }

    public void changeGear(int newGear) {
        if ((newGear >= 0 && newGear < this.gears.size()) && this.clutchIsIn) { // from 0 to the List length bec its our only choices for gears
            this.currentGearNumber = newGear;
            System.out.println("Gear " + newGear + " selected.");
        } else {
            System.out.println("Grind!");
            this.currentGearNumber = 0;
        }
    }

    public double wheelSpeed(int revs) {
        if (clutchIsIn) { // car cant move
            System.out.println("Scream!!!");
            return 0.0;
        }
        return revs * this.gears.get(this.currentGearNumber).getRatio(); // translates to the Gearbox's current Gear.ratio
    }

    /**
     * it doesnt makes sense to talk about Gear objects outside the context of Gearbox,
     * thats why Gear was made to be a non-static inner class of the Gearbox class
     */
    private class Gear {
        private int gearNumber;
        private double ratio;

        public Gear(int gearNumber, double ratio) {
            this.gearNumber = gearNumber;
            this.ratio = ratio;
            /**
             * when inner and outer class have 2 instance var named the same, prevent NAME CONFLICT by
             * int outerClassGearNUmb = Gearbox.this.gearNumber; => to access outer class gearNumber member
             * int innerClassGEarNum = this.gearNumber; 'this' keyword refers to the current class we're in
             */
        }

        public double driveSpeed(int revs) {
            return revs * this.ratio;
        }

        public double getRatio() {
            return this.ratio;
        }
    }

}
