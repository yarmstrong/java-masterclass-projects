package com.hoken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/*
 1. Instead of creating a HashMap for list of locations, we'll create a class
    that implements the interface Map.
 2. Locations now acts like a Map and we implemented all the required methods,
    and now, we can improve its functionality for where its data will come from

 NOTES: error handling
 1. if ur going to remove catch block, give try block a partner in finally
    block, or remove the try entirely
 2. try-with resources: the finally block which usu have the closing of files
    has its exception suppressed and only the exception thrown in the try block
    would be the start of the stacktrace

 Write OUT (in an outside file) vs Read IN (read whats given in)
 1. BufferedReader reader = new BufferedReader(new FileReader("locations_big.txt"))
    can be replaced with:
    BufferedReader reader = Files.newBufferedReader(Paths.get("locations_big.txt"))
 2. BufferedWriter dirWriter = new BufferedWriter(new FileWriter("directions_testing.txt"))
    can be replaced with:
    BufferedWriter dirWriter = Files.newBufferedWriter(Paths.get("directions_testing.txt"))
*/
public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new HashMap<>();
    private static Locations singleInstance = new Locations(); // singleton

    public static Locations getInstance() { // singleton
        return singleInstance;
    }

    private Locations() {} // singleton

    static {
        /* static initialization, which is read from a file, and not hard-coded */

        /* part 1: read the locations.txt and populate locations variable */
        String line;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("locations_big.txt"))) {
            while ((line = reader.readLine()) != null) {
                int delimiterIndex = line.indexOf(','); // splitting wont work for descr (having , in them)
                int locId = Integer.parseInt(line.substring(0, delimiterIndex));
                // locations.put(locId, new Location(locId, line.substring(delimiterIndex+1), new HashMap<>())); // BBM
                locations.put(locId, new Location(locId, line.substring(delimiterIndex+1), new LinkedHashMap<>()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* part 2: read the directions.txt and populate locations' exits variable */
        try (BufferedReader reader = new BufferedReader(new FileReader("directions_big.txt"))) {
            while ((line = reader.readLine()) != null) {
                String[] input = line.split(",");
                int locId = Integer.parseInt(input[0]);
                locations.get(locId).addExit(input[1], Integer.parseInt(input[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* not using try-resource and still using a scanner
        Scanner s = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader("locations_big.txt")));
            while (s.hasNextLine()) {
                String[] input = s.nextLine().split(",");
                int locId = Integer.parseInt(input[0]);
                locations.put(locId, new Location(locId, input[1], new HashMap<>()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (s != null) s.close();
        } */

        /* not using try-resource and still using a scanner
        try {
            s = new Scanner(new BufferedReader(new FileReader("directions_big.txt")));
            while (s.hasNextLine()) {
                String[] input = s.nextLine().split(",");
                int locId = Integer.parseInt(input[0]);
                locations.get(locId).addExit(input[1], Integer.parseInt(input[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (s != null) s.close();
        } */

        for (Integer locId: locations.keySet()) {
            System.out.println(locId + " =>> " + locations.get(locId));
        }

    }

    public static void main(String[] args) throws IOException {
        try (BufferedWriter locWriter = new BufferedWriter(new FileWriter("locations_testing.txt"));
             BufferedWriter dirWriter = Files.newBufferedWriter(Paths.get("directions_testing.txt"))) {
            for (Location location : locations.values()) {
                locWriter.write(location.getLocationId() + "," + location.getDesc() + '\n');
                for (String direction : location.getExits().keySet()) {
                    if (!direction.equalsIgnoreCase("Q"))
                        dirWriter.write(location.getLocationId() + "," + direction + "," + location.getExits().get(direction) + '\n');
                }
            }
        } // no catch block here coz the method will not handle it and will throw it out

        // try (FileWriter locFile = new FileWriter("locations.txt");
        //      FileWriter dirFile = new FileWriter("directions.txt")) {
        //     for (Location location : locations.values()) {
        //         locFile.write(location.getLocationId() + "," + location.getDesc() + '\n');
        //         // throw new IOException("exception thrown by file writing");
        //         for (String direction : location.getExits().keySet()) {
        //             dirFile.write(location.getLocationId() + "," + direction + "," + location.getExits().get(direction) + '\n');
        //         }
        //     }
        // }

        /* simplified: making the method throw error, and so will remove catch block and retain
        try-finally blocks
        FileWriter locFile = null;
        try {
            locFile = new FileWriter("locations.txt");
            for (Location location : locations.values()) {
                locFile.write(location.getLocationId() + "," + location.getDesc() + '\n');
                throw new IOException("exception thrown by file writing");
            }
        } finally {
            System.out.println("in finally");
            if (locFile != null) { // if not, then our next error is NullPointerException, tho it is not an error compiling, intellij still warns u to do null checking
                System.out.println("closing the file");
                locFile.close(); // very important to close: cud lead to leaks, corruption and obj locking
            }
        } */
    }

    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        return locations.get(key);
    }

    @Override
    public Location put(Integer key, Location value) {
        return locations.put(key, value);
    }

    @Override
    public Location remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> m) {

    }

    @Override
    public void clear() {
        locations.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {
        return locations.entrySet();
    }
}
