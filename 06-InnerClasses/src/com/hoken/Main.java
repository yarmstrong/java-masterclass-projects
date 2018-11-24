package com.hoken;

/**
 * Lesson for Inner Classes
 * 1. static nested class => to associate a class with its outer class, cant access the outer class' nonstatic members/methods
 * 2. nonstatic nested class => inner class
 * 3. local class => inner class defined inside a scope block (ie method)
 * 4. anonymous class => nested class without class name
 */
public class Main {

    public static void main(String[] args) {

        /**
         * initializing a public inner class via its outer class:
         * Gearbox mcLaren = new Gearbox(6);
         * Gearbox.Gear first = mcLaren.new Gear(1, 12.3);
         * outerClass.innerClass varName = outerInstance.new innerConstructor();
         * >> but usu inner classes are set to private since whatever members a class is usu hidden from user
         */

        Gearbox mcLaren = new Gearbox(6);
        mcLaren.addGear(1, 5.3);
        mcLaren.addGear(2, 10.6);
        mcLaren.addGear(3, 15.9);

        System.out.println("OPERATE CLUTCH TO TRUE (NOT MOVE), CHANGE GEAR SUCCESS, OPERATE CLUTCH BACK TO FALSE (MOVE), SPEED IS: ...");
        mcLaren.operateClutch(true); // car not move
        mcLaren.changeGear(1); // car not moving so able to change gear
        mcLaren.operateClutch(false); // make car move
        System.out.println(mcLaren.wheelSpeed(1000)); // when car moving check speed
        System.out.println();

        /**
         * when we forgot to operate the clutch
         * a: (clutch is true meaning car cant move) when changing gears, it'll either grind the gears OR
         * b: (clutch is false meaning car can move) will cause engine to scream when it hits the red line and bounces off the limiter
         */
        System.out.println("OPERATE CLUTCH STILL FALSE (MOVE),CHANGE GEAR FAILS (GEAR DEFAULTS TO NEUTRAL), CLUTCH STILL FALSE (MOVE) SO SPEED IS: ...");
        mcLaren.changeGear(2); // change gear fail bec car still moving
        System.out.println(mcLaren.wheelSpeed(3000));
        System.out.println();

        System.out.println("OPERATE CLUTCH TO TRUE (NOT MOVE), CHANGE GEAR SUCCESS, CLUTCH NOT TURNED TO FALSE (STILL TRUE NOT MOVING), SPEED IS: ...");
        mcLaren.operateClutch(true); // car not move
        mcLaren.changeGear(3); // car not moving so able to change gear
        System.out.println(mcLaren.wheelSpeed(5000));
        System.out.println();

        System.out.println("OPERATE CLUTCH BACK TO FALSE (MOVE), SPEED IS: ...");
        mcLaren.operateClutch(false); // make car move
        System.out.println(mcLaren.wheelSpeed(6000)); // when car moving check speed

        /**
         * think of clutch as stopper, unclutch means car able to move
         * 1. clutch, change gear OK, unclutch, move
         * 2. unclutch, change gear Error change automatic to neutral (grind), still unclutch but movement is 0.0
         * 3. clutch, change gear OK, still clutched and u try to move, it screams and speed will automatically sets to 0.0 (since clutched means no move)
         * 4. lastly to correct our previous, we unclutch first then move, os using current gear (3), we use that to calculate the speed
         */

    }
}
