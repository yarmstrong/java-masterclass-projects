package com.hoken;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Location {
    private final int locationId;
    private final String desc;
    private final Map<String, Integer> exits;

    public Location(int locationId, String desc, Map<String, Integer> exits) {
        this.locationId = locationId;
        this.desc = desc;
        // this.exits = (exits == null) ? new HashMap<>() : new HashMap<>(exits); // BBM
        this.exits = (exits == null) ? new LinkedHashMap<>() : new LinkedHashMap<>(exits);
        this.exits.put("Q", 0);
    }

    public int getLocationId() {
        return locationId;
    }

    public String getDesc() {
        return desc;
    }

    public Map<String, Integer> getExits() {
        return new LinkedHashMap<>(exits);
        // return new HashMap<>(exits); // BBM
    }

    protected void addExit(String dir, Integer loc) {
        exits.put(dir, loc);
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", desc='" + desc + '\'' +
                ", exits=" + exits +
                '}';
    }
}
