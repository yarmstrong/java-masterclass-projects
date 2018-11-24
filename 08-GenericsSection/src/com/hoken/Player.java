package com.hoken;

public class Player {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        String str = "" + this.getClass();
        return str.substring(str.lastIndexOf('.')+1);
    }
}
