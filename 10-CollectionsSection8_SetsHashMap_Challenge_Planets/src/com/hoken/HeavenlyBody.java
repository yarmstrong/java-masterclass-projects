package com.hoken;

import java.util.HashSet;
import java.util.Set;

/**
 * ADDING AN INNER CLASS KEY TO THIS CLASS RESPONSIBLE IN HOLDING THE IMMUTABLE DATA TO BE USED AS KEY OBJECT IN A HASHMAP
 * STEPS:
 * 1. create the inner class, constructor receiving and saving the immutable data name and bodytype
 * 2. remove these 2 var taken by Key class, including getters
 * 3. in replacement, add the Key var and corresponding getter (for the outer class) and makeKey() returning a new instance of the Key everytime externals needs a key object for comparing (note that u shudnt be giving ur very own key copy)
 * 4. update the outer class way of equality checking, shud now only rely on how Key implements the equality checking
 */
public class HeavenlyBody {
    private final Key key;
    private final double orbitalPeriod;
    private final Set<HeavenlyBody> satellites;

    public enum BodyTypes {
        STAR,
        PLANET,
        DWARF_PLANET,
        MOON,
        COMET,
        ASTEROID
    }

    public HeavenlyBody(String name, double orbitalPeriod, BodyTypes bodyType) {
        this.key = new Key(name, bodyType);
        this.orbitalPeriod = orbitalPeriod;
        this.satellites = new HashSet<>();
    }

    public static Key makeKey(String name, BodyTypes bodyType) {
        return new Key(name, bodyType);
    }

    public Key getKey() {
        return key;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public Set<HeavenlyBody> getSatellites() {
        return new HashSet<>(satellites);
    }

    public boolean addSatellites(HeavenlyBody satellite) {
        return this.satellites.add(satellite);
    }

    /* since were using this class as element to be added into a HashSet
        we determined that as long as the name variables are the same
        then they will be the same

        1) same hashcode() result means jvm will always be saving and
        retrieving data on its particular place
        2) then equals() guarantee that it will be only be checking
        the name variable's equality and nothing else */
    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj instanceof HeavenlyBody) {
            HeavenlyBody other = (HeavenlyBody) obj; // where obj is any instance of this class and any subclass
            return this.key.equals(other.getKey());
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return this.key.hashCode();
    }

    @Override
    public String toString() {
        return this.key.getName() + ": " + this.key.getBodyType() + ", " + this.getOrbitalPeriod();
    }

    public static class Key {
        private final String name;
        private BodyTypes bodyType;

        public Key(String name, BodyTypes bodyType) {
            this.name = name;
            this.bodyType = bodyType;
        }

        public String getName() {
            return name;
        }

        public BodyTypes getBodyType() {
            return bodyType;
        }

        @Override
        public int hashCode() {
            return this.name.hashCode() + 57 + this.bodyType.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || this.getClass() != obj.getClass()) return false;
            Key other = (Key) obj;
            if (this.name.equals(other.getName())) {
                return this.bodyType.equals(other.getBodyType());
            }
            return false;
        }

        @Override
        public String toString() {
            return this.name + ": " + this.bodyType;
        }
    }
}
