package com.hoken;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static final Map<Integer, Location> locList = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // testPersistenceOfCopy(); /* just like with Seat ArrayList in Theatre class, the Main copy can manipulate its own list and not affect the original, tho if u got a copy to the same Seat obj, then u wud still be able to modify it via Main so u need to put up safeguards if outside world shud be able to manipulate the obj */

        /* PART 2: using HashMap to save location with available directions from that location to other locations (called exits) -- my issue here is that the Location.locationId u know, so what even is the point */

        /* setup the game locations where one fo their default exits is 'Q' quit */
        locList.put(0, new Location(0,"You have left the game."));
        locList.put(1, new Location(1,"You are in a road."));
        locList.put(2, new Location(2,"You are at d top of a hill."));
        locList.put(3, new Location(3,"You are inside a building."));
        locList.put(4, new Location(4,"You are in d valley."));
        locList.put(5, new Location(5,"You are in the forest"));


        /* setup each locations exit directions */
        locList.get(1).addExit("W",2);
        locList.get(1).addExit("E",3);
        locList.get(1).addExit("S",4);
        locList.get(1).addExit("N",5);

        locList.get(2).addExit("N",5);

        locList.get(3).addExit("W",1);

        locList.get(4).addExit("N",1);
        locList.get(4).addExit("W",2);

        locList.get(5).addExit("S",1);
        locList.get(5).addExit("W",2);

        /* GAME PROCESS: create an endless loop where the user is shown their current location desc and show the available exit directions from that location. once user chooses, u need to determine the location corresponding to that exit direction. loop will end when user quits (0). we just let the loop continues even if they chose an invalid direction. the directions will be presented to them again so its all good. */

        int loc = 1;
        while (true) { // endless loop til i break if by quitting
            System.out.println(locList.get(loc).getDesc());
            if (loc == 0)
                break;

            System.out.print("Please choose your route: ");
            Map<String, Integer> exits = locList.get(loc).getExits();
            for (String dir: exits.keySet()) {
                System.out.print(dir+" ");
            }
            System.out.println();

            String selectDir = scanner.nextLine().toUpperCase();
            if(exits.containsKey(selectDir)) {
                loc = exits.get(selectDir);
            } else {
                System.out.println("That is not an valid route.");
            }
        }
    }

    static void testPersistenceOfCopy() {
        Location castle = new Location(123,"castle");
        castle.addExit("seaside", 111);
        castle.addExit("westside", 112);

        Map newCopy = castle.getExits();
        printMe(newCopy);
        newCopy.remove("seaside");
        printMe(newCopy);
        System.out.println("newCopy with 1 key removed");
        printMe(castle.getExits());
        System.out.println("does not affect the castle's own copy");
    }

    static void printMe(Map<String, Integer> map) {
        System.out.println("=================================");
        for (String key : map.keySet()) {
            System.out.println(key + ": " +map.get(key));
        }
    }
}
