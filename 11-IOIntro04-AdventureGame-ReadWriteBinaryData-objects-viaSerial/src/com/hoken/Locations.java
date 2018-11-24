package com.hoken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/*
  After making the Location class serializable, we now use 'ObjectOutputStream'
    instead of 'DataOutputStream' bec we're now be saving objects, not merely
    normal 'data' of primitive types (including the String class). Now we have
    a file where mixture of object and primitive types are in binary form
  A serialized file will ever only contain 1 copy of the same instance of
    the serialized object. But is only unique within a file, but not across
    multiple files. If 2 files are read back into the application, 2 distinct
    instances will be created.
*/
public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new HashMap<>();
    private static Locations singleInstance = new Locations(); // singleton

    public static Locations getInstance() { // singleton
        return singleInstance;
    }

    private Locations() {} // singleton

    static {
        /* static initialization, reverse engineer the locations.dat file */
        try (ObjectInputStream readIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream("locations.dat")))) {
            boolean eof = false;
            while (!eof) {
                try {
                    Location loc = (Location) readIn.readObject();
                    System.out.println(loc);
                    locations.put(loc.getLocationId(), loc);
                } catch(EOFException e) {
                    eof = true;
                }
            }
        } catch (InvalidClassException e) {
            System.out.println("InvalidClassException during loading of location data from binary file " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException during loading of location data from binary file " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException during object serial reading " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        try (ObjectOutputStream writeOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("locations.dat")))) {
            for (Location location : locations.values()) {
                writeOut.writeObject(location);
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
