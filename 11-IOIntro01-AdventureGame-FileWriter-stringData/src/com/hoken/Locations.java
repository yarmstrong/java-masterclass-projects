package com.hoken;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
    1. Instead of creating a HashMap for list of locations, we'll create a class
    that implements the interface Map.
    2. Locations now acts like a Map and we implemented all the required methods,
    and now, we can improve its functionality for where its data will come from
*/
public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new HashMap<>();
    private static Locations instance = new Locations();

    public static Locations getInstance() {
        return instance;
    }

    private Locations() {}

    static {
        /* static initialization, which is read from a file, and not har-coded */
        Map<String, Integer> tempExits = new HashMap<>();

        locations.put(0, new Location(0,"You have left the game.", null));

        tempExits.put("W",2);
        tempExits.put("E",3);
        tempExits.put("S",4);
        tempExits.put("N",5);
        locations.put(1, new Location(1,"You are in a road.",tempExits));

        tempExits = new HashMap<>();
        tempExits.put("N",5);
        locations.put(2, new Location(2,"You are at d top of a hill.",tempExits));

        tempExits = new HashMap<>();
        tempExits.put("W",1);
        locations.put(3, new Location(3,"You are inside a building.",tempExits));

        tempExits = new HashMap<>();
        tempExits.put("N",1);
        tempExits.put("W",2);
        locations.put(4, new Location(4,"You are in d valley.",tempExits));

        tempExits = new HashMap<>();
        tempExits.put("S",1);
        tempExits.put("W",2);
        locations.put(5, new Location(5,"You are in the forest.",tempExits));
    }

    public static void main(String[] args) {
        FileWriter locFile = null;
        try {
            locFile = new FileWriter("locations.txt");
            for (Location location : locations.values()) {
                locFile.write(location.getLocationId() + "," + location.getDesc() + '\n');
            }
        } catch (IOException e) { // FileNotFoundException
            System.out.println("error 1st catch");
            e.printStackTrace();
        } finally {
            System.out.println("in finally");
            try { // closing locFile, just like creating needs error handling, so we have double try-catch
                if (locFile != null) { // if not, then our next error is NullPointerException, tho it is not an error compiling, intellij still warns u to do null checking
                    System.out.println("closing the file");
                    locFile.close(); // very important to close: cud lead to leaks, corruption and obj locking
                }
            } catch (IOException e) {
                System.out.println("error 2nd catch");
                e.printStackTrace();
            }
        }
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
