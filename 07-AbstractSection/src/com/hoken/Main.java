package com.hoken;

/*
    DIFFERENCE BETWEEN INTERFACE vs ABSTRACT CLASS
    1.1 a class can implement more than 1 interface
    1.2 a class cam inherit up to 1 class (abstract or not) only
    2.1 interface methods are "public" and "abstract" by default (dont need to specify the keywords) but interface can now also have implemented method
    2.2 abstract class methods can be abstract (uses abstract keyword) or not (tho an abstract class must have at least 1 abstract method), the methods can be public, private, protected, default
    3.1 interface variables are only "public", "static" and "final" (will not change, and accessible without instantiating since u cant)
    3.2 abstract instance variables is inherited by their subclass just like normal class
    4.1 interface has a 'can do' relationship with its implementing class
    4.2 abstract has a 'is a' relationship with its subclass
    5.1 interface has no constructor so cant instantiate
    5.2 abstract has constructor but cant be instantiated (u need to create a concrete class 1st from it b4 u can instantiate)

    INTERFACE:
    => when unrelated classes need to implement same kind of methods meaning u want a data type to be able to specify a particular behavior but ur not concerned who implements the said behavior
    1. declaration of methods of a class, not implementation
    2. interface to define "what kind of operation", concrete class to define the operation
    3. interface is a contract between a class and the outsiders who will use this class that implements this interface (ie outsiders expect a class that implements a particular interface to have this particular list of methods
        => contract is enforced at build time by compiler (thats why in IDE it gives u warning that u need to implement else be an abstract class
    4. interface like abstract class can also have abstract and (java8) implemented (default) methods
    5. interface can implement another interface
    6. interface's primary purpose is abstraction (and super polymorph), decoupling the "what" from the "how"
    7. java9, interface is no longer absolute public and abstract. interface can now have private methods (used when 2 default methods from different interface share the common default code)
 */
public class Main {
    public static void main(String[] args) {

        MyLinkedList myList = new MyLinkedList();
//        String data = "5 3 6 1 5 4 9 2 8 7";
        String data = "P S A C V M L L Q E";
        String[] split = data.split(" ");
        for (String s : split) {
            myList.addItem(new Node(s));
        }

        myList.traverse(myList.getRoot());
        myList.removeItem(new Node("S"));
        myList.traverse(myList.getRoot());
        myList.removeItem(new Node("S"));

        myList.removeItem(new Node("5"));
        myList.removeItem(new Node("5"));
        myList.traverse(myList.getRoot());
    }
}
