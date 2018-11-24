package com.hoken;

public class StockItem implements Comparable<StockItem> {
    private final String name;
    private double price;
    private int quantity;
    private int reserved;

    public StockItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.reserved = 0;
    }

    public StockItem(String name, double price) {
        this(name, price, 0);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int availableStock() { // MainChallenge: those asking for quantity shud reflect that u have potential buyer and so will not deceive them coz that wud be an error in the reservation part if they try to over-reserve so non-sense
        return quantity-reserved;
    }

    public void setPrice(double price) {
        if (Double.compare(price, 0.0) > 0) {
            this.price = price;
        }
    }

    public void adjustStock(int quantity) { // MainChallenge: called only when performing checkout, not reservation
        int newQuantity = this.quantity + quantity;
        if (newQuantity >= 0)
            this.quantity = newQuantity;
    }

    public int reserveStock(int quantity) { // MainChallenge: reservation updates only the reserved, not the quantity
        if (quantity <= this.availableStock()) {
            this.reserved += quantity;
            return quantity;
        }
        return 0;
    }

    public int unreserveStock(int quantity) { // MainChallenge: cancellation updates only the reserved, not the quantity
        if (quantity <= this.reserved) {
            reserved -= quantity;
            return quantity;
        }
        return 0;
    }

    public int finalizeStock(int quantity) { // MainChallenge: reflect the successful transaction to the quantity and reserved variables
        if (quantity <= this.reserved) {
            this.reserved -= quantity; // no longer reserved
            this.quantity -= quantity; // finally bought and reduced the stock
            return quantity;
        }
        return 0;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        return this.name.equals(((StockItem) obj).getName());
    }

    @Override
    public int compareTo(StockItem o) {
        if (this == o) return 0;
        if (o != null) {
            return this.name.compareTo(o.getName());
        }
        throw new NullPointerException();
    }

    @Override
    public String toString() {
        return this.name + " : price " + this.price + " : quantity " + this.quantity + " : reservedInStock " + this.reserved;
    }
}