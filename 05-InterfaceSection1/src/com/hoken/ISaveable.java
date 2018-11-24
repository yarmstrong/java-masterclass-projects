package com.hoken;

import java.util.List;

public interface ISaveable {

    // think  this, classes can be designed with methods for saving its important data. now, if we have many classes and each class has this capability, it is much simpler if u can just define an interface that put a class into contract that it needs to implement saving methods
    // two contract methods: 1) getSavedData() => returns ArrayList of values 2) populateData() => populate an object fields from an ArrayList??

    /**
     * method to be implemented by a class to write/save its instance variable values into a list collection, where list in this case acts as a device storage (in real life it should be a file or something)
     * @return the created list collection
     */
    List<String> write();

    /**
     * method to be implemented by a class to read data from received list parameter (in real life it should be a file or something) and saved their values into the class's instance variable (sort of like a constructor but only in populating the instance variable
     * @param savedValues
     */
    void read(List<String> savedValues);
}
