package com.hoken;

public class Deluxe extends Hamburger {
    public Deluxe() {
        super("Deluxe", 14.54, "white", "Sausage & Bacon");
        super.addOn1("Chips", 2.75);
        super.addOn2("Drinks", 1.81);
    }

}
