package com.hoken;

public class VipCustomer {
    private String vipName;
    private String vipNumber;
    private double creditLimit;

    public VipCustomer(String vipName, String vipNumber, double creditLimit) {
        this.vipName = vipName;
        this.vipNumber = vipNumber;
        this.creditLimit = creditLimit;
    }

    public VipCustomer() {
        this("Default Name", "00000", 100_000.00);
    }

    public VipCustomer(String vipName, double creditLimit) {
        this(vipName, "for processing", creditLimit);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("VipCustomer {");
        sb.append("\n  vipName: '").append(vipName).append('\'');
        sb.append(",\n  vipNumber: '").append(vipNumber).append('\'');
        sb.append(",\n  creditLimit: ").append(creditLimit);
        sb.append("\n}");
        return sb.toString();
    }
}
