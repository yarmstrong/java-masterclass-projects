package com.hoken;

/*
    imagine this as a Node being a node in a set of NodeList. thats why its abstract super class has default methods for knowing ur neighbors left and right
*/
public class Node extends ListItem {
    /*
        CONSTRUCTOR: U NEED TO INSTANTIATE UR VAR VALUE (IN ABSTRACT ListItem CLASS).
        2ways to do it:
        1. since the var is accessible to u thru protected access modifier, u can do the setting or instantiate instead of super. and then since u wont be calling the has-arg super constructor, u need to define the no-arg constructor. so basically, ur just making ur life more complicated.
        2. OR since super class has its own instantiation already, just use them. u dont need to do repeat the same process, and u just have to call them right away. no need to edit the super to have another constructor so the no-arg constructor will work for u.. what if ur not even the author of the super class, and ur an outsider. u have no right at all. all u can do is re-use them
    */
    public Node(Object value) {
        super(value);
    }

    /* implementing the abstract methods */

    @Override
    ListItem next() {
        return this.rightLink;
    }

    @Override
    ListItem setNext(ListItem item) {
        this.rightLink = item;
        return this.rightLink;
    }

    @Override
    ListItem previous() {
        return this.leftLink;
    }

    @Override
    ListItem setPrevious(ListItem item) {
        this.leftLink = item;
        return this.leftLink;
    }

    @Override
    int compareTo(ListItem item) {
        if (item != null) {
            /*
                NOTE: String s = ((String) super.getValue()); // super.getValue() returns ur var value of type Object which is so generic when in fact, u saved a String obj, thats why ur so sure here that u can cast it into a String
            */
            /*
                QUESTION: if Node class knows that what its saving is a string, then if were using the umbrella of ListItem to generically reference a Node, how will the ListItem know that it shud be calling Node methods instead of another class that subclassed from ListItem also??? SEE: MyLinkedList.addItem() where ListItem calls concrete methods of Node (well we know its Node coz we have no other class, but what if we have?????)
            */
            return ((String) super.getValue()).compareTo((String) item.getValue());
            /*
                QUESTION: WILL this.getValue() WORKS THE SAME AS super.getValue()??
                return ((String) this.getValue()).compareTo((String) item.getValue());
             */
        } else {
            return -1; // ur comparing super to a null value, -1 is still compatible with the expected result, since a -1 value means that ur string is lesser than the challenger. if challenger is null, means ur string doesnt need to move in the order. stays the same
        }
        /*
            CompareTo() RESULT VALUES:
            0: means equal
            1: means ur string is greater/later in order (greater than 0) than ur being compared to
            -1: means ur string is less/earlier in order (less than 0) than the challenger
        */
    }
}
