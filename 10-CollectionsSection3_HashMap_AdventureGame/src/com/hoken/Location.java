package com.hoken;

import java.util.HashMap;
import java.util.Map;

public class Location {
    private final int locationId;
    private final String desc;
    private final Map<String, Integer> exits;

    public Location(int locationId, String desc) {
        this.locationId = locationId;
        this.desc = desc;
        this.exits = new HashMap<>();
        this.exits.put("Q", 0);
    }

    public void addExit(String dir, int loc) {
        exits.put(dir,loc);
    }

    public int getLocationId() {
        return locationId;
    }

    public String getDesc() {
        return desc;
    }

    public Map<String, Integer> getExits() {
        return new HashMap<>(exits); // returns a new different copy and giving its reference address outside. so that they dont have to do the new constructor. what they believe is they get a copy.
    }
}
