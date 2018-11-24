package com.hoken;

public class Planet extends HeavenlyBody {
    public Planet(String name, double orbitalPeriod) {
        super(name, orbitalPeriod, BodyTypes.PLANET);
    }

    @Override
    public boolean addSatellites(HeavenlyBody moon) {
        if (moon.getKey().getBodyType().equals(BodyTypes.MOON)) {
            return super.addSatellites(moon);
        }
        return false; // true if successfully added so if not moon obviously u still need to return a boolean
    }


}
