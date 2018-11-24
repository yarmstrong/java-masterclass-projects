package com.hoken;

class SharedResource {
    private Worker owner;

    SharedResource(Worker owner) {
        this.owner = owner;
    }

    Worker getOwner() {
        return owner;
    }

    synchronized void setOwner(Worker owner) {
        this.owner = owner;
    }
}
