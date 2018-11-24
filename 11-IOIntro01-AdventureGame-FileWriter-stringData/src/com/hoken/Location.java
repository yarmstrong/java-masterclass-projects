package com.hoken;

import java.util.HashMap;
import java.util.Map;

public class Location {
    private final int locationId;
    private final String desc;
    private final Map<String, Integer> exits;

    public Location(int locationId, String desc, Map<String, Integer> exits) {
        this.locationId = locationId;
        this.desc = desc;
        this.exits = (exits == null) ? new HashMap<>() : new HashMap<>(exits);
        this.exits.put("Q", 0);
    }

    public int getLocationId() {
        return locationId;
    }

    public String getDesc() {
        return desc;
    }

    public Map<String, Integer> getExits() {
        return new HashMap<>(exits);
    }
}
