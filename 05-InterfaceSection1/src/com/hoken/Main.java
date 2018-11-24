package com.hoken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    // SOLUTION FOR THE ISSUE OF MULTIPLE SCANNER USE: 1 class shud use 1 instance only of Scanner obj and must only be close once, when app exits
    // in this class, main() will trigger Scanner twice inside method call, so in that method, use the class instance of scanner and dont close it. class will be responsible for closing it

    public static void main(String[] args) {
        Player tim = new Player("Tim", 10, 15);
        System.out.println("NEWLY CREATED PLAYER: " + tim);
        saveObject(tim); // arg must be implementing ISaveable interface

        tim.setHitPoints(8);
        System.out.println("PLAYER AFTER UPDATING THEIR HITPOINTS: " + tim);

        tim.setWeapon("Stormbringer");
        System.out.println("PLAYER AFTER UPDATING THEIR WEAPON: " + tim);

        saveObject(tim); // accepts ISaveable
        loadObject(tim); // accepts ISaveable

        System.out.println("#######################################\n");

        Monster ogre = new Monster("Ogre", 10, 15);
        System.out.println("NEWLY CREATED MONSTER: " + ogre);

        ogre.setHitPoints(8);
        System.out.println("PLAYER AFTER UPDATING THEIR HITPOINTS: " + ogre);

        saveObject(ogre); // accepts ISaveable
        loadObject(ogre); // accepts ISaveable

        /* 1. our player and monster objects uses their corresponding class reference type, so that u can still call their own methods (here .setHitPoints() which is not an abstract method by ISaveable)
        * 2. saveObject/loadObject methods are accepting ISaveable objects only, but we were able to pass both player and monster with no issues even if they currently use diff reference type bec the param expects objects that are able to implement the ISaveable interface, not their specific class type
        */

        scanner.close();
    }

    private static ArrayList<String> readValues(String objType){
        ArrayList<String> values = new ArrayList<>();
        String[] dataFields = new String[] {};

        if (objType.equals("monster"))
            dataFields = Monster.monsterInfo;
        else if (objType.equals("player"))
            dataFields = Player.playerInfo;

        System.out.println("Choose\n" +
                "1 TO ENTER " + objType.toUpperCase() + " INFO\n" +
                "0 TO RETURN\n");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.println("datafields: " + Arrays.toString(dataFields));
            for (int i = 0; i < dataFields.length; i++) {
                System.out.print("Enter " + dataFields[i] + ": ");
                String input = scanner.nextLine();
                if ("".equals(input)) input = null;
                values.add(i, input);
            }
        }

        return values;
    }

    /**
     * the call to ISaveable.write() just put the obj's instance var into a list, mimic-ing the real-life device storage saving
     * @param objToSave
     */
    private static void saveObject(ISaveable objToSave) {
        List<String> objData = objToSave.write();
        String[] arr = objData.toArray(new String[objData.size()]);
        for (int i = 0; i < objData.size(); i++) {
            System.out.println("Saving " + objData.get(i) + " to storage device.");
        }
        System.out.println("SAVED DATA IS: " + Arrays.toString(arr) + '\n');
    }

    /**
     * 1) readValues() => get input from user and save into a list (mimic the reading from a file)
     * 2) ISaveable.read() => will receive the list and reads the obj's data and save it into its instance var.
     * @param objToLoad
     */
    private static void loadObject(ISaveable objToLoad) {
        System.out.println("Loading/getting info from the user as their initial data...");

        String objType = "";
        if (objToLoad instanceof Monster)
            objType = "monster";
        else if (objToLoad instanceof Player)
            objType = "player";

        ArrayList<String> input = readValues(objType);
        objToLoad.read(input);
        System.out.println("LOADED " +  objType.toUpperCase() + " DATA: " + objToLoad + '\n');
    }
}
