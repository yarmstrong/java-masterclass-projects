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

    private static Map<Integer, IndexRecord> index = new LinkedHashMap<>();
    private  static RandomAccessFile ra;

    public static Locations getInstance() { // singleton
        return singleInstance;
    }

    private Locations() {} // singleton

    static {
        /* static initialization, needs to read the index from locations_rand.dat file,
           the process now wont be loading the locations from file into the memory
           we only load locations on demand. (since we are no longer loading our instance
           variable, it wud be blank and the saving of locations data into random file
           is no longer feasible) */

        /* now the location is no longer loaded in memory. so when a player move to a new
           location, we need to load the location from the random access file we've created */

        try {
            ra = new RandomAccessFile("locations_rand.dat", "rwd");
            int numLocations = ra.readInt(); //
            long locationStartPoint = ra.readInt(); // offset

            while (ra.getFilePointer() < locationStartPoint) {
                int locationId = ra.readInt();
                int locationStart = ra.readInt();
                int locationLength = ra.readInt();
                IndexRecord record = new IndexRecord(locationStart, locationLength);
                index.put(locationId, record);
            }

            // ra.close();
            /* ra file needs to be open, so that when Main triggers getLocation(),
               the ra.seek() and other ra methods will work since we are accessing
               ra for data instead of reading data loaded into memory */
        } catch (IOException e) {
            System.out.println("IOException random file " + e.getMessage());
        }



        // RESET: INITIALIZE READ THE OLD STRING DATA FILE AND RELOAD THE MEMORY, AND THEN RUN MAIN TO DO THE REWRITE OF _RAND,DAT FILE AGAIN

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

    public Location getLocation(int locId) throws IOException {
        IndexRecord record = index.get(locId);
        ra.seek(record.getStartByte()); // access the file and pointing to the right offset
        int id = ra.readInt();
        String desc = ra.readUTF();
        String exits = ra.readUTF();
        String[] exitParts = exits.split(",");

        Location loc = new Location(id, desc, null);

        if (locId != 0) {
            for (int i = 0; i < exitParts.length; i++) {
                System.out.println("exitPart[i] dir= " + exitParts[i]);
                System.out.println("exitPart[i+1] dest = " + exitParts[i+1]);
                String dir = exitParts[i];
                int dest = Integer.parseInt(exitParts[++i]); // ++i will be on top of the actual i++ in the for loop so we will only 3 looping for i = 6
                loc.addExit(dir, dest);
            }
        }
        return loc;
    }

    /* Creating the random access file steps:
       1. the 1st 4 bytes will contain the number of locations (bytes 0-3)
       2. the next 4 bytes will contain the start offset of the locations section (bytes 4-7)
       3. the next section of the file will contain the index (the index is 1692
       bytes long, starting at byte 8 and ending in byte 1700
       4. final section will always contain the location records (the data)
       and will start at 1700
       */
    public static void main(String[] args) throws IOException {
        // try (RandomAccessFile randFile = new RandomAccessFile("locations_rand.dat", "rwd")) {
        //     randFile.writeInt(locations.size());
        //     int indexSize = locations.size() * 3 * Integer.BYTES;
        //     int locationStart = (int) (indexSize + randFile.getFilePointer() + Integer.BYTES);
        //     randFile.writeInt(locationStart);
        //
        //     long indexStart = randFile.getFilePointer();
        //
        //     int startPointer = locationStart;
        //     randFile.seek(startPointer);
        //
        //     for (Location loc : locations.values()) {
        //         randFile.writeInt(loc.getLocationId());
        //         randFile.writeUTF(loc.getDesc());
        //         StringJoiner sj = new StringJoiner(",");
        //         for (String dir : loc.getExits().keySet()) {
        //             if (!dir.equalsIgnoreCase("Q")) {
        //                 sj.add(dir).add(loc.getExits().get(dir).toString());
        //             }
        //         }
        //         System.out.println("locId" + loc.getLocationId() + " >> StrJoiner: " + sj.toString());
        //         randFile.writeUTF(sj.toString());
        //
        //         IndexRecord record = new IndexRecord(startPointer, (int) (randFile.getFilePointer() - startPointer));
        //         index.put(loc.getLocationId(), record);
        //         startPointer = (int) randFile.getFilePointer();
        //     }
        //
        //     randFile.seek(indexStart);
        //     for (Integer locId : index.keySet()) {
        //         randFile.writeInt(locId);
        //         randFile.writeInt(index.get(locId).getStartByte());
        //         randFile.writeInt(index.get(locId).getLength());
        //     }
        // }
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

    public void close() throws IOException {
        ra.close(); // closes the random file when player quits the game
    }
}
