package com.hoken;

public class Main {

    public static void main(String[] args) {
        Hamburger burger1 = new Hamburger("Basic", 3.56, "white", "sausage");

        burger1.addOn1("Tomato", .27);
        burger1.addOn2("Lettuce", .75);
        burger1.addOn3("Cheese", 1.12);

        System.out.println(burger1.toString());
        System.out.println("Total Hamburger price is: " + burger1.itemizeHamburger());

        HealthyBurger healthy1 = new HealthyBurger(5.67, "Bacon");
        healthy1.addOnHealthy1("Spinach", 3.41);
        healthy1.addOn4("Fries", 5.43);
        System.out.println(healthy1.toString());
        System.out.println("Total Healthy price is: " + healthy1.itemizeHamburger());
    }
}
