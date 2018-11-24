package com.hoken;

public class Hamburger {
    private String name;
    private double price;
    private String breadRollType;
    private String meatType;

    private String addOn1Name;
    private double addOn1Price;

    private String addOn2Name;
    private double addOn2Price;

    private String addOn3Name;
    private double addOn3Price;

    private String addOn4Name;
    private double addOn4Price;

    public Hamburger(String name, double price, String breadRollType, String meatType) {
        this.name = name;
        this.price = price;
        this.breadRollType = breadRollType;
        this.meatType = meatType;
    }

    public void addOn1(String name, double price) {
        this.addOn1Name = name;
        this.addOn1Price = price;
    }

    public void addOn2(String name, double price) {
        this.addOn2Name = name;
        this.addOn2Price = price;
    }

    public void addOn3(String name, double price) {
        this.addOn3Name = name;
        this.addOn3Price = price;
    }

    public void addOn4(String name, double price) {
        this.addOn4Name = name;
        this.addOn4Price = price;
    }

    public double itemizeHamburger() {
        double hmPrice = this.price;

        if (this.addOn1Name != null) {
            hmPrice += this.addOn1Price;
        }

        if (this.addOn2Name != null) {
            hmPrice += this.addOn2Price;
        }

        if (this.addOn3Name != null) {
            hmPrice += this.addOn3Price;
        }

        if (this.addOn4Name != null) {
            hmPrice += this.addOn4Price;
        }

        return hmPrice;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Hamburger {");
        sb.append("\n  name: '").append(name).append('\'');
        sb.append(",\n  price: ").append(price);
        sb.append(",\n  breadRollType: '").append(breadRollType).append('\'');
        sb.append(",\n  meatType: '").append(meatType).append('\'');
        sb.append(",\n  addOn1Name: '").append(addOn1Name).append('\'');
        sb.append(",\n  addOn1Price: ").append(addOn1Price);
        sb.append(",\n  addOn2Name: '").append(addOn2Name).append('\'');
        sb.append(",\n  addOn2Price: ").append(addOn2Price);
        sb.append(",\n  addOn3Name: '").append(addOn3Name).append('\'');
        sb.append(",\n  addOn3Price: ").append(addOn3Price);
        sb.append(",\n  addOn4Name: '").append(addOn4Name).append('\'');
        sb.append(",\n  addOn4Price: ").append(addOn4Price);
        sb.append("\n}");
        return sb.toString();
    }
}
