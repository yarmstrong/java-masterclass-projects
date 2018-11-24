package com.hoken;

public class HealthyBurger extends Hamburger {
    private String healthy1Name;
    private double healthy1price;

    private String healthy2Name;
    private double healthy2price;

    public HealthyBurger(double price, String meatType) {
        super("Healthy Burger", price, "brown rye", meatType);
    }

    public void addOnHealthy1(String name, double price) {
        this.healthy1Name = name;
        this.healthy1price = price;
    }

    public void addOnHealthy2(String name, double price) {
        this.healthy2Name = name;
        this.healthy2price = price;
    }

    @Override
    public double itemizeHamburger() {
        double healthyPrice = super.itemizeHamburger();

        if (this.healthy1Name != null) {
            healthyPrice += this.healthy1price;
        }

        if (this.healthy2Name != null) {
            healthyPrice += this.healthy2price;
        }

        return healthyPrice;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HealthyBurger {");
        sb.append("\n  healthy1Name: '").append(healthy1Name).append('\'');
        sb.append(",\n  healthy1price: ").append(healthy1price);
        sb.append(",\n  healthy2Name: '").append(healthy2Name).append('\'');
        sb.append(",\n  healthy2price: ").append(healthy2price);
        sb.append("\n}");
        sb.append(" => superclass ").append(super.toString());
        return sb.toString();
    }
}
