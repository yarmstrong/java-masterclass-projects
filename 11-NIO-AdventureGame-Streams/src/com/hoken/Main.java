package com.hoken;

import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Locations locList = Locations.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int loc = 64;
        while (true) {
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
        scanner.close();
    }
}
