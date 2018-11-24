package com.hoken;

public abstract class ListItem {
    /*
        CREATING INSTANCES OF THIS VERY SAME CLASS WITH THE CLASS SCOPE BLOCK
        note: any defined variables are inherited by subclass but based from restriction level, not all variables are accessible to them (see: singleton) while leads to the owner of the restricted variable to implement a public method for subclass to access them
       if access modifier is set to default, then default variables are accessible to subclass within the same package
       here, access modifier is set to protected, meaning that this public abstract can be subclassed from outside this package, and its protected variables are still accessible to them (default will not let u do that, it stays on the same package only)
    */
    protected ListItem rightLink = null;
    protected ListItem leftLink = null;

    protected Object value;

    public ListItem(Object value) {
        this.value = value;
    }

    /* list of abstract methods */
    abstract ListItem next();
    abstract ListItem setNext(ListItem item);
    abstract ListItem previous();
    abstract ListItem setPrevious(ListItem item);

    abstract int compareTo(ListItem item);

    /* list of override-able methods tho theyre just getters and setters */
    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
