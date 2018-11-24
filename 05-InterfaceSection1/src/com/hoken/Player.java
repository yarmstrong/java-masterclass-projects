package com.hoken;

import java.util.ArrayList;
import java.util.List;

public class Player implements ISaveable {
    private String name;
    private int hitPoints;
    private int strength;
    private String weapon;
    public static String[] playerInfo = new String[] { "name", "hitPoints", "strength", "weapon" };

    public Player(String name, int hitPoints, int strength) {
        this.name = name;
        this.hitPoints = hitPoints;
        this.strength = strength;
        this.weapon = "Sword";
    }

    @Override
    public String toString() {
        return "Player {" +
                "\n  name: '" + name + '\'' +
                ",\n  hitPoints: " + hitPoints +
                ",\n  strength: " + strength +
                ",\n  weapon: '" + weapon + '\'' +
                "\n}";
    }

    /**
     * method to be implemented by a class to write/save its instance variable values into a list collection, where list in this case acts as a device storage (in real life it should be a file or something)
     * @return the created list collection
     */
    @Override
    public List<String> write() {
        List<String> values = new ArrayList<>();
        values.add(0, this.name);
        values.add(1, ""+this.hitPoints);
        values.add(2, ""+this.strength);
        values.add(3, this.weapon);
        return values;
    }

    /**
     * method to be implemented by a class to read data from received list parameter (in real life it should be a file or something) and saved their values into the class's instance variable (sort of like a constructor but only in populating the instance variable
     * @param savedValues
     */
    @Override
    public void read(List<String> savedValues) {
//        if(!savedValues.isEmpty()) {
//            this.name = savedValues.get(0);
//            this.hitPoints = Integer.parseInt(savedValues.get(1));
//            this.strength = Integer.parseInt(savedValues.get(2));
//            this.weapon = savedValues.get(3);
//            System.out.println("Player info updated...");
//        }
        if(!savedValues.isEmpty()) {
            for (int i = 0; i < playerInfo.length; i++) {
                String data = savedValues.get(i);
                switch (i) {
                    case 0:
                        if (data == null) data = "";
                        this.name = data;
                        break;
                    case 1:
                        if (data == null) data = "0";
                        this.hitPoints = Integer.parseInt(data);
                        break;
                    case 2:
                        if (data == null) data = "0";
                        this.strength = Integer.parseInt(data);
                        break;
                    case 3:
                        if (data == null) data = "";
                        this.weapon = data;
                        break;
                    default:
                        break;
                }
            }
            System.out.println("Player info updated...");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String[] getPlayerInfo() {
        return playerInfo;
    }
}
