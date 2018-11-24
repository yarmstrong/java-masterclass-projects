package com.hoken;

/*
    NodeList is the interface that gives a class the capability to be a NodeList (meaning have behavior of a NodeList) like it can do:
    1) get a particular root
    2. add item in ur list of nodes
    3. remove item from ur list of nodes
    4. traverse ur list of nodes
    where the implementor of this interface shud obviously have its own instance of the nodelist or list or whatever since interface cant do that for u. and instance variable is of course shud be defined in the concrete class not an interface. interface is just a contract of methods and static values
*/
public interface NodeList {
    ListItem getRoot();
    boolean addItem(ListItem item);
    boolean removeItem(ListItem item);
    void traverse(ListItem root);

    /*
        where root is the starting node in any node location u want to start traversing thats why a root will have data type of ListItem coz root is ur list tree, u traverse in there. u cant traverse if its 1 node only.
        so just think of root as the NodeList where u start, thats why its called head
    */
}
