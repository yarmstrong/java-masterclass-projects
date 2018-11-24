package com.hoken;

public class Dog {
    private final String name;

    public Dog(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    String extractClassName(String str) {
        return str.substring(str.lastIndexOf('.')+1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof Dog) {
            System.out.println("Obj "+extractClassName(""+o.getClass())+" is an instanceOf "+extractClassName(""+this.getClass()));
            String objName = ((Dog) o).getName();
            return this.getName().equals(objName);
        } else {
            System.out.println("Obj "+extractClassName(""+o.getClass())+" is not instanceOf "+extractClassName(""+this.getClass()));
        }
        return false;
    }
}
