package com.hoken;

class Worker {
    private String name;
    private boolean active;

    Worker(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    private String getName() {
        return name;
    }

    private boolean isActive() {
        return active;
    }

    synchronized void work(SharedResource sharedResource, Worker otherWorker) {
        while (active) { // while im active
            // check if im the owner of the sharedResource, if not wait til i got it and continue process til i get it (the checking will fail then)
            if (!sharedResource.getOwner().equals(this)) {
                try {
                    wait(10);
                } catch (InterruptedException e) {

                }
                continue; // once ive waited, check if im now the owner
            }

            // check if the otherWorker is active else, i need to give up the ownership of the sharedResource, and try again
            if (otherWorker.isActive()) {
                System.out.println(name + ": im giving up the resource to " + otherWorker.getName() + " coz they active.");
                sharedResource.setOwner(otherWorker);
                continue;
            }

            System.out.println(getName() + ": im now the owner and other resource is currently not active so i can finally make use of the resource.");
            active = false;
            sharedResource.setOwner(otherWorker); // im done so giving it back to other otherWorker
        }
    }
}
