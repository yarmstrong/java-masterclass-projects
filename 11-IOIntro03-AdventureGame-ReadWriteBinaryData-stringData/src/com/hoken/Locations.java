package com.hoken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new HashMap<>();
    private static Locations singleInstance = new Locations(); // singleton

    public static Locations getInstance() { // singleton
        return singleInstance;
    }

    private Locations() {} // singleton

    static {
        /* static initialization, reverse engineer the locations.dat file */
        try (DataInputStream binaryReader = new DataInputStream(new BufferedInputStream(new FileInputStream("locations.dat")))) {
            boolean eof = false;
            while (!eof) {
                try {
                    Map<String, Integer> exits = new LinkedHashMap<>();
                    int locId = binaryReader.readInt(); // line will throw EOFException when no more data so need to handle this and break the loop
                    String desc = binaryReader.readUTF();
                    int size = binaryReader.readInt();
                    System.out.println("Reading location " + locId + " : " + desc);
                    System.out.println("Found " + size + " of exits");
                    for (int i = 0; i < size; i++) {
                        String dir = binaryReader.readUTF();
                        int dest = binaryReader.readInt();
                        exits.put(dir, dest);
                        System.out.println("\t\t" + dir + "," + dest);
                    }
                    locations.put(locId, new Location(locId, desc, exits));
                } catch (EOFException e) {
                    eof = true; // this will catch the error and breaks the loop since no more lines to process
                    // now we dont have EOFException shown in the console logging
                }
            }
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println("IOException during loading of location data from binary file");
        }

        // fix when u truncate the locations.dat:
        // String line;
        // try (BufferedReader reader = Files.newBufferedReader(Paths.get("locations_big.txt"))) {
        //     while ((line = reader.readLine()) != null) {
        //         int delimiterIndex = line.indexOf(','); // splitting wont work for descr (having , in them)
        //         int locId = Integer.parseInt(line.substring(0, delimiterIndex));
        //         // locations.put(locId, new Location(locId, line.substring(delimiterIndex+1), new HashMap<>())); // BBM
        //         locations.put(locId, new Location(locId, line.substring(delimiterIndex+1), new LinkedHashMap<>()));
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        //
        // /* part 2: read the directions.txt and populate locations' exits variable */
        // try (BufferedReader reader = new BufferedReader(new FileReader("directions_big.txt"))) {
        //     while ((line = reader.readLine()) != null) {
        //         String[] input = line.split(",");
        //         int locId = Integer.parseInt(input[0]);
        //         locations.get(locId).addExit(input[1], Integer.parseInt(input[2]));
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

    public static void main(String[] args) throws IOException {
        /* only uses 1 file, order of writing is important to do the reverse engineering
            for reading this file */
        try (DataOutputStream binaryWriter = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("locations.dat")))) {
            for (Location location : locations.values()) {
                binaryWriter.writeInt(location.getLocationId());
                binaryWriter.writeUTF(location.getDesc());
                System.out.println("Writing " + location.getLocationId() + " : " + location.getDesc());
                System.out.println("Writing " + (location.getExits().size()-1) + " exits." );
                binaryWriter.writeInt(location.getExits().size()-1);
                for (String direction : location.getExits().keySet()) {
                    if (!direction.equalsIgnoreCase("Q")) {
                        System.out.println("\t\t" + direction + "," + location.getExits().get(direction));
                        binaryWriter.writeUTF(direction);
                        binaryWriter.writeInt(location.getExits().get(direction));
                    }
                }
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
