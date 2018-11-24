package com.hoken;

import java.text.DecimalFormat;

public class Account {
    private double balance;
    private String acctNumber;
    private String acctName;
    private String acctAddress;
    private String acctEmailAddress;

    public Account(double balance, String acctNumber, String acctName, String acctAddress, String acctEmailAddress) {
        this.balance = balance;
        this.acctNumber = acctNumber;
        this.acctName = acctName;
        this.acctAddress = acctAddress;
        this.acctEmailAddress = acctEmailAddress;
    }

    public Account(String acctNumber, String acctName, String acctAddress) {
        this(1000.0, acctNumber, acctName, acctAddress, null);
    }

    public Account() {
        this("12345", "Default Name", "Default Address");
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Account {");
        sb.append("\n  balance: ").append(balance);
        sb.append(",\n  acctNumber: '").append(acctNumber).append('\'');
        sb.append(",\n  acctName: '").append(acctName).append('\'');
        sb.append(",\n  acctAddress: '").append(acctAddress).append('\'');
        sb.append(",\n  acctEmailAddress: '").append(acctEmailAddress).append('\'');
        sb.append("\n}");
        return sb.toString();
    }

    private String doubleToString(double d) {
//        return String.format("%.2f", d);
        return new DecimalFormat("##0.00").format(d);
    }

    public void deposit(double depositAmount) {
        this.balance += depositAmount;
        System.out.println("Deposit of " + doubleToString(depositAmount) + " successful. Current balance is " +
                doubleToString(this.balance) + ".");
    }

    public void withdrawal(double withdrawalAmount) {
        if ((this.balance - withdrawalAmount) < 0) {
            System.err.println("Withdrawal amount of " + doubleToString(withdrawalAmount) +
                    " cannot be processed. Insufficient fund of " + doubleToString(this.balance) + ".");
        } else {
            this.balance -= withdrawalAmount;
            System.out.println("Withdrawal amount of " + doubleToString(withdrawalAmount) +
                    " is successful. Remaining balance is " + doubleToString(this.balance) + ".");
        }
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAcctNumber() {
        return acctNumber;
    }

    public void setAcctNumber(String acctNumber) {
        this.acctNumber = acctNumber;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getAcctAddress() {
        return acctAddress;
    }

    public void setAcctAddress(String acctAddress) {
        this.acctAddress = acctAddress;
    }

    public String getAcctEmailAddress() {
        return acctEmailAddress;
    }

    public void setAcctEmailAddress(String acctEmailAddress) {
        this.acctEmailAddress = acctEmailAddress;
    }
}
