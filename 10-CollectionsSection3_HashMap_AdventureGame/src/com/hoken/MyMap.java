package com.hoken;

import java.util.HashMap;
import java.util.Map;

public class MyMap {
    public static void main(String[] args) {
        Map<String, String> lang = new HashMap<>();
        lang.put("Java", "a compiled high level, object-oriented, platform independent language");
        lang.put("Python", "an interpreted, object-oriented, high-level programming language with dynamic semantics");
        lang.put("Algol", "an algorithmic language");
        lang.put("BASIC", "beginner's all purpose symbol");
        lang.put("Lisp", "therein lies madness");

        // GET OLD VALUE AND OVERRIDE WITH NEW ONE
        System.out.println("=============================");
        String oldValue = lang.put("Lisp", "Haha crazy lady!!");
        System.out.println(oldValue);
        System.out.println(lang.get("Lisp"));

        // DOUBLE CHECK BEFORE OVERRIDING
        System.out.println("=============================");
        if (lang.containsKey("Java")) {
            System.out.println("\"Java\"' key is already populated.");
        }
        System.out.println(lang.get("Java"));

        // LOOPING THRU THE MAP USING KEYS (NO GUARANTEED ORDER)
        System.out.println("=============================");
        listMe(lang);

        // REMOVING BY KEYS AND BY COMBI OF KEY/VALUE
        System.out.println("=============================");
        if (!lang.remove("Lisp", "Crazy")) {
            System.out.println("Theres no such combination key:value pair. Nothing to delete.");
        }
        lang.remove("BASIC");
        listMe(lang);

        // REPLACING AN EXISTING KEY AND GETTING THE OLD VALUE, FAILING TO REPLACE COZ NOT EXISTING GIVES NULL
        System.out.println("=============================");
        System.out.println("Replacing BASIC not existing: " + lang.replace("BASIC", "New Basic")); // null, no oldValue
        System.out.println("Replacing Lisp: " + lang.replace("Lisp", "im not crazy, u r!!!")); // returns oldValue
        System.out.println("Replacing COMBI key:value: " + lang.replace("Lisp", "oldValue", "newValue")); // false boolean
        listMe(lang);
    }

    public static void listMe(Map<String, String> map) {
        for (String key : map.keySet()) {
            System.out.println(key + ": " +map.get(key));
        }
    }
}
