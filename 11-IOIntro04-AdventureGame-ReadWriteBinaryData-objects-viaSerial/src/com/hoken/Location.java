package com.hoken;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/*
  MAKING AN OBJECT SERIALIZABLE INSTEAD OF GOING THRU THE WHOLE OBJ DATA
    JUST TO GET EACH PRIMITIVE TYPES BE SAVED INTO A FILE
  Serializable :
    will serialize recursively, so a class should have all its fields be
    Serializable
  serialVersionUID : must be added as instance variable
  For Location class, it has int and String primitive types so no need to
    fuss about if it implements Serializable. but Map, luckily for us,
    already implements Serializable so we dont have to do it ourselves
*/
public class Location implements Serializable {
    private final int locationId;
    private final String desc;
    private final Map<String, Integer> exits;

    private long serialVersionUID = 1L;

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
