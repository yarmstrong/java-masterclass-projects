package com.hoken;

import java.util.Map;
import java.util.Scanner;

/*
    WHAT DATA TO USE IN READING / WRITING:
    1. use char string for: text file / spreadsheet report / json / xml
    2. use binary format for: variables / classes

    SERIAL OR SEQUENTIAL ACCESS VS RANDOM ACCESS
    1. serial: stream of data that arrives in ur program or sent out from it
       in a defined order with each piece of data following in sequence
    2. random: applies to files, allow u to jump within the file retrieving
       or overriding any data in any location that the file choose (same
       like how a database wud work using index of data, so u can access
       randomly without having to go in sequence
 */
public class Main {
    private static final Locations locList = Locations.getInstance(); /* intellij is
    pacified when i made the class a singleton bec it was used as a read-only variable */
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int loc = 1;
        while (true) { // endless loop til i break if by quitting
            System.out.println(locList.get(loc).getDesc());
            if (loc == 0)
                break;

            System.out.print("Please choose your route: ");
            Map<String, Integer> exits = locList.get(loc).getExits();
            for (String dir: exits.keySet()) {
                System.out.print(dir+" ");
                System.out.println();
            }

            String selectDir = scanner.nextLine().toUpperCase();
            if(exits.containsKey(selectDir)) {
                loc = exits.get(selectDir);
            } else {
                System.out.println("That is not an valid route.");
            }
        }
        scanner.close();
    }
}
