package com.hoken;

public class StockItem implements Comparable<StockItem> {
    private final String name;
    private double price;
    private int quantity;

    public StockItem(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
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

    public int quantityInStock() {
        return quantity;
    }

    public void setPrice(double price) {
        if (Double.compare(price, 0.0) > 0) {
            this.price = price;
        }
    }

    public void adjustStock(int quantity) {
        int newQuantity = this.quantity + quantity;
        if (newQuantity >= 0) /* we had a bug here: this method is
        used to sell and stock on items. quantity can be zero out
        when buyer bought everything. the only restriction is (-) */
            this.quantity = newQuantity;
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
        return this.name + " : price " + this.price + " : quantity " + this.quantity;
    }
}