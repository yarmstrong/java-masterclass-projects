package com.hoken;

public class MyLinkedList implements NodeList {
    private ListItem root;

    public MyLinkedList() {
        this(null);
    }

    private MyLinkedList(ListItem root) {
        this.root = root;
    }

    @Override
    public ListItem getRoot() {
        return this.root;
    }

    @Override
    public boolean addItem(ListItem newItem) {
        // we have no current root/head (no list at all as present by being headless), so the passed item will be it
        if (this.root == null) {
            this.root = newItem;
            return true;
        }

        ListItem currentItem = this.root;
        /*
            when u called item1.addItem(item2) from Main(), item1 refers to this.root and that is where we start, in the root. but since we are going to traverse from item1 (til we reached the end of line) to compare item2, we need a separate var currentItem as the current one being checked. meaning that, initialization, currentItem = item1 aka this.root, but if need to traverse more, then we override currentItem var with a new one down the line
            so basically item1 will remain as item1 in Main(), it will not be overridden no matter what. what can possibly change tho is its left and right reference if the newItem will be item1's neighbor

        */
        while (currentItem != null) {
            int compare = currentItem.compareTo(newItem);

            /*
                sorting is started from the root (1) same as root (2) to be added before root (backward) or (3) move forward til u reach null adding new in the end of list, or be turned backward and so definitely be saved on that exact location
            */

            if (compare < 0) { // forward direction
                // currentItem is early than new, so u need to traverse forward, comparing newItem to each succeeding data to determine its correct location
                if (currentItem.next() != null) {
                    currentItem = currentItem.next();
                } else {
                    // no more next, means weve finally reached the end, with currentItem being the last item, but now, it will be reassigned to be the penultimate, with newItem being the new last item. to do that, make newItem's prev = currentItem while currentItem's next = newItem
                    currentItem.setNext(newItem).setPrevious(currentItem);
                    return true;
                }
            } else if (compare > 0) { // backward direction
                if (currentItem.previous() != null) {
                    // currentItem is in the middle of the list, make newItem in between curr.prev and curr
                    currentItem.previous().setNext(newItem).setPrevious(currentItem.previous());
                    newItem.setNext(currentItem).setPrevious(newItem);
                } else {
                    // currentItem is the root, make newItem as root
                    currentItem.setPrevious(newItem).setNext(currentItem);
                    this.root = newItem;
                }
                return true;
                /*
                    ########## OK FUCK I UNDERSTAND NOW... ##########
                    MyLinkedList.addItem() means that u add an new but need to put it in correct order in the list.

                    We start from root bec where do we even begin to traverse a list but to start from the start. we move forward or backward.

                    the forward one will either continuous forward til reached end of list and u add new, or continuous forward and then thrown backward (and will use the backward logic)

                    BASED FROM TUTORIAL:
                    if backward, either 1) curr has prev (will only be true if forward forward then backwards and backwards is not ur starting point), insert new between them, prev>new>curr
                    or 2) curr has no prev (1st loop, u go right here immediately, end of story), new will be inserted before curr, null>new>curr and also this.root = new (the new head)
                    ########## ########## ##### ########## ##########
                */
            } else {
                System.out.println(newItem.getValue() + " is already in the list.");
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean removeItem(ListItem item) {
        if (this.root == null) {
            System.out.println("List is empty. Nothing to remove");
            return false;
        }
        /*
            i received an item, need to traverse the list and check if:
            a) item > curr: 1 means after so move the list
            b) item = curr: 0 means delete this item
            c) item < curr: -1 means will never have to compare a lesser value in the remaining list of higher value (where digits are higher than string)

            A B C D
            delete c
            C > A, move
            C > B, move
            C == C, delete

            A B C E F G
            delete D (not existing)
            D > A, move
            D > B, move
            D > C, move
            D < E, ull never find D after E meaning if u havent deleted it yet, then it doesnt exist in the list. exit now coz its nonsense to check the rest of the list
        */
        ListItem currentItem = this.root;
        while (currentItem != null) {
            int compare = item.compareTo(currentItem);
            if (compare > 0) {
                currentItem = currentItem.next();
            } else if (compare == 0) {
                //delete
                currentItem.previous().setNext(currentItem.next());
                currentItem.next().setPrevious(currentItem.previous());
                System.out.println(item.getValue() + " is deleted.");
                return true;
            } else {
                // exit immediately, will never have to compare a lesser value in the remaining list of higher value (where digits are higher than string)
                return false;
            }
        }
        return false;
    }

    @Override
    public void traverse(ListItem item) {
        if (item == null) { // the item is the root of a list. if not set, then there is no list at all
            System.out.println("List is empty.");
        } else {
            while (item != null) {
                System.out.println(item.getValue());
                item = item.next();
            }
            System.out.println();
        }
    }
}
