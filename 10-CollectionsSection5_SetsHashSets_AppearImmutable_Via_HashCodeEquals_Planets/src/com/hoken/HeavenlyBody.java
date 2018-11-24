package com.hoken;

import java.util.HashSet;
import java.util.Set;

public final class HeavenlyBody {
    private final String name;
    private final double orbitalPeriod;
    private final Set<HeavenlyBody> satellites;

    public HeavenlyBody(String name, double orbitalPeriod) {
        this.name = name;
        this.orbitalPeriod = orbitalPeriod;
        this.satellites = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public double getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public Set<HeavenlyBody> getSatellites() {
        return new HashSet<>(satellites);
    }

    public boolean addMoon(HeavenlyBody moon) {
        return this.satellites.add(moon);
    }

    /* since were using this class as element to be added into a HashSet
        we determined that as long as the name variables are the same
        then they will be the same

        1) same hashcode() result means jvm will always be saving and
        retrieving data on its particular place
        2) then equals() guarantee that it will be only be checking
        the name variable's equality and nothing else */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HeavenlyBody other = (HeavenlyBody) obj; /* this is to check that 
        we are not comparing a subclass coz they cud have a different
        implementation compared to this class. but if class is considered
        final, there wud be no problem with subclass */
        return this.name.equals(other.getName());
        /* if (obj instanceof HeavenlyBody) ==> docu says this is always false
            tho this work for for String class : if (anObject instanceof String) {... */
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + 57; // 57 is just our own customization
    }
}
