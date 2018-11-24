package com.hoken;

public class Animal {
    private String name;
    private int brain;
    private int body;
    private int size;
    private int weight;

    public Animal() { // automatically invoked by subclass default constructor
        this("Animal", 2, 2, 2, 2);
        System.out.println("default animal constructor");
    }

    public Animal(String name, int brain, int body, int size, int weight) {
        System.out.println("overloaded animal constructor");
        this.name = name;
        this.brain = brain;
        this.body = body;
        this.size = size;
        this.weight = weight;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Animal {");
        sb.append("\n  name: '").append(name).append('\'');
        sb.append(",\n  brain: ").append(brain);
        sb.append(",\n  body: ").append(body);
        sb.append(",\n  size: ").append(size);
        sb.append(",\n  weight: ").append(weight);
        sb.append("\n}");
        return sb.toString();
    }

    public void eat() {
        System.out.println("Animal called: " + name + " eating...");
    }

    public void move(int speed) {
        System.out.println("Animal called: " + name + " moving @ speed " + speed + "...");
    }

    public String getName() {
        return name;
    }

    public int getBrain() {
        return brain;
    }

    public int getBody() {
        return body;
    }

    public int getSize() {
        return size;
    }

    public int getWeight() {
        return weight;
    }
}
