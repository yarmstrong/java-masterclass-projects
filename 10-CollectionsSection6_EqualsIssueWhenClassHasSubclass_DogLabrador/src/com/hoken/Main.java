package com.hoken;

/**
 * RULES WHEN U MODIFY THE RULE FOR EQUALITY CHECKING BETWEEN OBJECTS:
 * 1. use classes that are marked final, so u only worry within that class (no need to think about subclass) how u implement
 *      the equality checking and ur variable for equality checking is final
 * 2. when ur classed cant be final, u need to worry how the subclass equality checking would impact the superclass
 *      checking.
 *    a. final variables inherited from superclass will still be immutable
 *    b. so the issue here actually is if subclass is allowed to create its own equality checking. esp when instanceOf
 *      is being used in equals() method (u cant do superclass instanceOf subclass in subclass.equals() method bec the
 *      comparison result of that is always false)
 *    c. so if subclass will work out right, then allow it.
 *    d. but most of the time, u make the superclass equality checking to be applied to all subclass, meaning subclass
 *      cant override / implement their own equality checking and will follow superclass rule
 *
 * BASICALLY IF U CARE ABOUT SUBCLASS COMPARISON TO BE TRUE, ALLOW INSTANCEOF COMPARISON
 * BUT IF U DONT CARE ABOUT SUBCLASS, U USU MAKE UR CLASS FINAL AND SO U USE THE CLASSNAME VS CLASSNAME COMPARISON
 *      IF THEYRE NOT THE SAME CLASS, OF COURSE THEY WILL NEVER BE THE SAME AT ALL
 *      BUT IF THERES SUBCLASS U CANT COMPARE BY CLASSNAME, OF COURSE THEY WILL HAVE DIFFERENT NAMES DOOFUS, SO WHAT
 *      U NEED TO DO IS THE CHECKING OF INSTANCEOF (BUT ONLY THE SMALLER TO BIGGER UMBRELLA KIND OF CASTING ONLY)
 *
 */
public class Main {
    public static void main(String[] args) {
        Labrador lab = new Labrador("lab");
        Dog dog = new Dog("lab");
        System.out.println("Dog.equals(Lab): "+dog.equals(lab)); // true =>> Obj Labrador is an instanceOf Dog
        System.out.println("Lab.equals(Dog): "+lab.equals(dog)); // false ==> Obj Dog is not instanceOf Labrador
        // tho the 2 obj's name are the same, the equality checking wud fail immediately in the instance comparison
    }
}
