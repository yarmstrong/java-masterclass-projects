package com.hoken;

import java.util.HashMap;
import java.util.Map;

/**
 * MAKING IMMUTABLE CLASS
 * - cant be modified after creation
 * - enhances encapsulation and reduces error from externals accessing the code
 * - ie: softwares that supports plugins. the objects they expose to externals for them use to create their plugins are safeguard so that they wont be changed.
 *
 * WAYS TO MAKE THIS LOCATION CLASS IMMUTABLE:
 *      1. PRIVATE FINAL VARIABLES: declaring variables as private with final. final means that u shudnt be changing ur variable after 1st instantiation and also to inform that u dont create corresponding setters
 *      2. MAKE GETTER METHODS IMMUTABLE: by returning a different copy to externals
 *      3. MAKE SETTER METHODS IMMUTABLE: removing the public method to add object to the hashmap, Main() can instantiate it with values, but setter will make a new copy first before saving it to the class final variable
 */
public class Location {
    private final int locationId;
    private final String desc;
    private final Map<String, Integer> exits;

    public Location(int locationId, String desc, Map<String, Integer> exits) {
        this.locationId = locationId;
        this.desc = desc;
        this.exits = (exits == null) ? new HashMap<>() : new HashMap<>(exits); // MAKING SETTER METHOD IMMUTABLE
            /* HashMap is a final variable and must be defaulted to empty map if received null value */
        this.exits.put("Q", 0);
    }

    public int getLocationId() {
        return locationId;
    }

    public String getDesc() {
        return desc;
    }

    public Map<String, Integer> getExits() {
        return new HashMap<>(exits); // MAKING GETTER METHOD IMMUTABLE by returning a different copy of this class exits variable. THIS IS ONE WAY TO SAFEGUARD YOUR DATA. externals can have a copy, but the shudnt be able to change the original
    }
}
