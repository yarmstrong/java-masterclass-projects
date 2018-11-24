package com.hoken;

public class Labrador extends Dog {
    public Labrador(String name) {
        super(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof Labrador) {
            System.out.println("Obj "+extractClassName(""+o.getClass())+" is an instanceOf "+extractClassName(""+this.getClass()));
            String objName = ((Labrador) o).getName();
            return this.getName().equals(objName);
        } else {
            System.out.println("Obj "+extractClassName(""+o.getClass())+" is not instanceOf "+extractClassName(""+this.getClass()));
        }
        return false;
    }
}
