package com.hoken;

public class Dog extends Animal {

    private String breed;

    public Dog() {
        super(); // if not explicitly added, javac will added the default superclass constructor invocation for u. basically if u dont need to use a custom constructor, then javac will behind the scene add this line at the start of ur code
        System.out.println("default dog constructor");
    }

    public Dog(String name, int size, int weight, String breed) {
        super(name, 1, 1, size, weight);
        this.breed = breed;
        System.out.println("overloaded dog constructor");
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Dog {");
        sb.append("\n  breed: '").append(breed).append('\'');
        sb.append("\n}");
        sb.append(" => superclass ").append(super.toString());
        return sb.toString();
    }

    private void chew() {
        System.out.println(getName() + " chewing...");
    }

    @Override
    public void eat() {
        System.out.println("overridden eat method :");
        chew();
        super.eat(); // main reason why u do this is bec u r doing an override method wherein u want to reuse the superclass logic insteaad of re-typing.so u need to specify the method invokation using keyword super (as if ur calling animal.method()). also if u dont specify super, then this method is just calling itself recursively, infinite call til memory becomes full

        // u can still call other methods from this class and superclass. technically, if method exists here, of course its callable. now if ur trying to call method not here, but u inherited from superclass, its valid, u receive it via inheritance

    }

    public void run() {
        System.out.println(getName() + " running...");
        move(10); // u can call any inherited methods from superclass even without specifying super coz 1) also since u dont need to distinguish them like in Dog.eat invoking Animal.eat inside 2) if u make an overridden Dog.move from Animal.move
    }
}














