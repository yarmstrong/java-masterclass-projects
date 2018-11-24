package com.hoken;

public class Main {
    public static void main(String[] args) {
        PolitePerson jane = new PolitePerson("Jane");
        PolitePerson john = new PolitePerson("John");

        new Thread(() -> jane.sayHello(john)).start();
        new Thread(() -> john.sayHello(jane)).start();
    }

    /* DEADLOCK : both threads were able to execute their sayHello() method but hangs in
        executing their sayHelloBack() method. so this is where the deadlock happens
    1. Thread1 acquires the lock on the jane object and enters the sayHello() method. prints to the console and got suspended by JVM still holding jane lock
    2. Thread2 acquires the lock on the john object and enters the sayHello() method. prints to the console and got suspended by JVM still holding john lock (question, why do john object able to query jane's name if Thread1 is already holding jane lock??) => ANS: thread1 calls for jane.sayHello() with john param, sync method sayHello will only lock the 'this' object (jane) and not the parameter so john is still free, only jane is locked by thread1. same happens with thread2. john's sayHello method is called so 'this' / john is locked. while the passed param jane object is not locked. and so when thread1 tries to execute sayHelloBack via john, sayHelloBack is a synchronized method and so will require for john object to be locked. the deadlock arises bec thread2 has john already locked.
    3. Thread1 runs again and triggers sayHelloBack() method using john object that was passed into jane's sayHello() method, but Thread2 holds john lock, so Thread1 suspends.
    4. Thread2 runs again and triggers sayHelloBack() method using jane object that was passed into john's sayHello() method, but Thread1 holds jane lock, so Thread2 suspends.

    DEADLOCK HERE OCCURS BEC AGAIN THE ORDER OF GETTING THE LOCK ARE IN DIFFERENT ORDER. THREAD1: JANE TO JOHN VS THREAD2: JOHN TO JANE
    SOLUTION? the tutorial did not even suggest any solution on this. just that if u faced deadlock, maybe this is the reason why. so u need
    to fix it */

    static class PolitePerson {
        private final String name;

        PolitePerson(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        synchronized void sayHello(PolitePerson person) {
            System.out.format("%s: %s" + " has said hello to me!%n", this.name, person.getName());
            person.sayHelloBack(this); // where 'this' is the current person. basically, from original call of jane.sayHello(john), itll become john.sayHello(jane)
        }

        synchronized void sayHelloBack(PolitePerson person) {
            System.out.format("%s: %s has said hello back to me!%n", this.name, person.getName());
            // person.sayHelloBack(this); // u shud add this. it will create a loop, going back and forth of calling sayHello() and then sayHelloBack() and then calls sayHello() again, which starts the endless loop
        }
    }
}
