package com.hoken;

import com.hoken.HeavenlyBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/****************************************************************************************
 * List ordered/has duplicate
 * Sets: is the umbrella interface that covers => " HashSet, LinkedHashSet, TreeSet "
 *     : no ordering / chaotic
 *     : cant contain duplicate (bec it checks for an object's hash)
 *     : u cant retrieve an item, but u cant check if item exists, and iterate over all items
 ****************************************************************************************
 * IMPORTANT: it uses hash when storing and retrieving data. HashMap uses hash to save the
 *     key and HashSet uses hash to save the element. jvm calculates an objects hash and
 *     store it in a particular location. then it recalculates the hash to look for the
 *     data at a particular location. if the keys u used are mutable, hash value of the key
 *     may change and it could lead to data loss (will not be able to retrieve data again)
 * HOW TO FIX:
 *     =>> 1) must use immutable objects (String, Integer)
 *     =>> 2) make ur mutable obj such that even if it changes in its state, the hash remains
 *     the same by overriding the hashCode() method (ie: Employee class has an employeeId that
 *     is not allowed to change after creation. name variable can change but it wont affect
 *     the hash, bec ur going to modify the hash calculation so that it will only be using
 *     the immutable employeeId)
 *     ==> 2.1) u can also override equals() method for
 *     a) this == obj
 *     b) obj != null || this.getClass() == obj.getClass() // instanceOf workaround
 *     c) Employee other = (Employee) obj (this.employeeId == other.employeeId
 * AS A RULE: if u override .equals(), u also need to override .hashCode() to maintain
 *     the contract that equal objects must have the equal hashcode.
 *     Scenario1: pluto1 (name: pluto, moons: 1) is to be added to set. hashCode() will
 *          compute (default) using pluto and 2 data and locate it in bucket A. since
 *          bucket A has no pluto object yet, it wont find a duplicate obj using equals()
 *          by name
 *     Scenario2: same pluto1 is added, ofc nothing has changed so it will be checked as
 *          duplicate and wont be added
 *     Scenario3; pluto2 (name: pluto moons: 2) the default hashCode() will give  diff
 *          computation bec of data pluto and 2 instead of 1. and will locate the bucket
 *          different from A. so if we now apply the .equals() method, then ofc there is
 *          no obj yet with name of pluto. and so this obj with duplicate name as the
 *          original data will be added regardless if we changed the rule for equality
 *          checking
 ****************************************************************************************
 * HashSet: most use of the Set group
 *     : DONT U DARE TRY TO GET A PARTICULAR VALUE, U USED IT SO U ONLY CARE TO SAVE AND
 *     SHOW IT ALL
 *     : makes use of HashMap logic, uses hashes to store the item and so also uses hash
 *     in checking duplicity of objects in sets
 *     : while HashMap has key:value mapping, HashSet only have keys which are processed
 *     like a HashMap key (makes use of hash)
 *     : whenever element is added to the set it becomes a key and a dummy object is
 *     stored as the value
 *     : very fast, used for union (HashSet.addAll()) and intersections
 ****************************************************************************************
 * LinkedHashSets / TreeSets: ??? later
 ****************************************************************************************/
public class Main {
    private static Map<String, HeavenlyBody> solarSystem = new HashMap<>(); // HashMap of planets and moons under the solar system
    private static Set<HeavenlyBody> planets = new HashSet<>(); // HashSet of planets only excluding the moons

    public static void main(String[] args) {

        /* PART 1: POPULATING HASHMAP AND HASHSET */
        HeavenlyBody temp = new HeavenlyBody("Mercury",88);
        solarSystem.put(temp.getName(), temp);
        planets.add(temp); // HashSet are single items, not key:value so u use the method add() not put()

        temp = new HeavenlyBody("Venus",225);
        solarSystem.put(temp.getName(), temp);
        planets.add(temp);

        temp = new HeavenlyBody("Earth",365);
        solarSystem.put(temp.getName(), temp);
        planets.add(temp);

        HeavenlyBody tempMoon = new HeavenlyBody("Moon", 27);
        solarSystem.put(tempMoon.getName(), tempMoon);
        temp.addMoon(tempMoon); // add moon to the HashSet inside the com.hoken.HeavenlyBody

        temp = new HeavenlyBody("Mars",365);
        solarSystem.put(temp.getName(), temp);
        planets.add(temp);

        tempMoon = new HeavenlyBody("Deimos", 27);
        solarSystem.put(tempMoon.getName(), tempMoon);
        temp.addMoon(tempMoon); // add moon to the HashSet inside the com.hoken.HeavenlyBody

        tempMoon = new HeavenlyBody("Phobos", 27);
        solarSystem.put(tempMoon.getName(), tempMoon);
        temp.addMoon(tempMoon);

        System.out.println("Planets:");
        printMe(planets); // prints all data in this set coz theres no way to choose a specific one

        System.out.println("Mars' Moon:");
        printMe(solarSystem.get("Mars").getSatellites()); // prints all data in this set coz theres no way to choose a specific one // but u got the planet data first from a HashMap specifying the key and u receive the corresponding planet obj

        /* IMPORTANCE OF IMMUTABLE CLASS AS KEYS IN HASHSET (AND ALSO HASHMAP) */
        // 1. add 1st instance of pluto obj: ADDED SUCCESSFULLY
        System.out.println("add 1st instance of pluto obj: ADDED SUCCESSFULLY");
        HeavenlyBody pluto = new HeavenlyBody("Pluto", 800);
        planets.add(pluto);
        printMe(planets);

        // 2. add another instance of pluto obj of the same data: IS NOT DUPLICATED
        System.out.println("add another instance of pluto obj of the same data: IS NOT DUPLICATED");
        planets.add(pluto);
        printMe(planets);

        // 3. add another pluto obj with different orbitalPeriod: IS ADDED BEC THE DEFAULT HASHCODE() SAYS IT IS A DIFFERENT OBJ FROM OUR 1ST PLUTO | IS NOT ADDED BEC HASHCODE() HAS BEEN MODIFIED TO CHECK ONLY THE OBJ'S NAME ONLY
        System.out.println("add another pluto obj with different orbitalPeriod: IS ADDED BEC THE DEFAULT HASHCODE() SAYS IT IS A DIFFERENT OBJ FROM OUR 1ST PLUTO | IS NOT ADDED BEC HASHCODE() HAS BEEN MODIFIED TO CHECK ONLY THE OBJ'S NAME ONLY");
        pluto = new HeavenlyBody("Pluto", 801);
        planets.add(pluto);
        printMe(planets);
    }

    public static void printMe(Map<String, HeavenlyBody> map) {
        for (String mapData : map.keySet()) {
            System.out.println("\t"+map.get(mapData).getName());
        }
    }

    public static void printMe(Set<HeavenlyBody> set) {
        for (HeavenlyBody setData : set) {
            System.out.println("\t"+setData.getName()+": "+setData.getOrbitalPeriod());
        }
    }
}












