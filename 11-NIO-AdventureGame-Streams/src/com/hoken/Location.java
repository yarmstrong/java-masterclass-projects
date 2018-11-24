package com.hoken;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Location implements Serializable {
    private final int locationId;
    private final String desc;
    private final Map<String, Integer> exits;

    private long serialVersionUID = 1L;

    public Location(int locationId, String desc, Map<String, Integer> exits) {
        this.locationId = locationId;
        this.desc = desc;
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
