package com.hoken;

public class Main {

    public static void main(String[] args) {
        System.out.println("====================");
        Dog d = new Dog("doggy", 24, 35, "poodle");
        System.out.println(d);

        System.out.println("====================");
        d.eat();
        d.run();

        System.out.println("====================");
        Animal a = new Animal();
        System.out.println(a);
    }
}
