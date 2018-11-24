package com.hoken;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new HashMap<>();
    private static Locations singleInstance = new Locations(); // singleton

    public static Locations getInstance() { // singleton
        return singleInstance;
    }

    private Locations() {
    } // singleton

    static {
        /* static initialization - read from source input */

        ioReadInObjectSerial();
        nioReadInObjectSerial();
        nioReadInStrings();
    }

    public static void main(String[] args) throws IOException {
        /* writing data into an output file */

        // nioWriteOutStrings();
        // nioWriteOutObjectSerial();
    }

    private static void nioWriteOutObjectSerial() throws IOException {
        /* java.io.* uses FileOutputStream to create ObjectOutputStream but
           java.nio.* uses static Files.newOutputStream() */

        /* ObjectOutputStream serialOutStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream("locations.dat"))); */

        Path locPath = FileSystems.getDefault().getPath("locations_nio.dat");

        try (ObjectOutputStream serialOutStream = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(locPath)))) {
            for (Location location : locations.values()) {
                serialOutStream.writeObject(location);
            }
        } /*catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private static void nioWriteOutStrings() throws IOException {
        Path locPath = FileSystems.getDefault().getPath("locations_testing_nio.txt");
        Path dirPath = FileSystems.getDefault().getPath("directions_testing_nio.txt");

        try (BufferedWriter locWriter = Files.newBufferedWriter(locPath);
             BufferedWriter dirWriter = Files.newBufferedWriter(dirPath)) {
            for (Location location : locations.values()) {
                locWriter.write(location.getLocationId() + "," + location.getDesc() + "\n");
                for (String dir : location.getExits().keySet()) {
                    if (!dir.equalsIgnoreCase("Q"))
                        dirWriter.write(location.getLocationId() + "," + dir + "," + +location.getExits().get(dir) + "\n");
                }
            }
        } /*catch (IOException e) {
            System.out.println("IOException writing to output file" + e.getMessage());
        }*/
    }

    private static void ioReadInObjectSerial() {
        try (ObjectInputStream readIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream("locations.dat")))) {
            boolean eof = false;
            while (!eof) {
                try {
                    Location loc = (Location) readIn.readObject();
                    System.out.println(loc);
                    locations.put(loc.getLocationId(), loc);
                } catch (EOFException e) {
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

    private static void nioReadInObjectSerial() {
        Path locFile = FileSystems.getDefault().getPath("locations_nio.dat");
        try (ObjectInputStream serialInStream = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(locFile)))) {
            boolean eof = false;
            while (!eof) {
                try {
                    Location loc = (Location) serialInStream.readObject();
                    locations.put(loc.getLocationId(), loc);
                } catch (EOFException e) {
                    eof = true;
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException during object serial reading " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException during loading of location data from binary file " + e.getMessage());
        }
    }

    private static void nioReadInStrings() {
        Path locPath = FileSystems.getDefault().getPath("locations_testing_nio.txt");
        Path dirPath = FileSystems.getDefault().getPath("directions_testing_nio.txt");

        String line;
        try (BufferedReader locReader = Files.newBufferedReader(locPath)) {
            while ((line = locReader.readLine()) != null) {
                int delimiterIndex = line.indexOf(",");
                int locId = Integer.parseInt(line.substring(0, delimiterIndex));
                locations.put(locId, new Location(locId, line.substring(delimiterIndex + 1), null));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader dirReader = Files.newBufferedReader(dirPath)) {
            while ((line = dirReader.readLine()) != null) {
                String[] splits = line.split(",");
                int locId = Integer.parseInt(splits[0]);
                locations.get(locId).addExit(splits[1], Integer.parseInt(splits[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
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
