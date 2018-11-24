package com.hoken;

public class Main {

    public static void main(String[] args) {

        Account bobAcct = new Account();
        System.out.println(bobAcct);

        bobAcct.withdrawal(1005);
        bobAcct.deposit(5.24);
        bobAcct.withdrawal(1005);

        VipCustomer vip = new VipCustomer("sss", "01222", 20_000.01);
        VipCustomer vip2 = new VipCustomer();
        VipCustomer vip3 = new VipCustomer("vip3", 23_000.25);
        System.out.println();
        System.out.println(vip.toString());
        System.out.println(vip2.toString());
        System.out.println(vip3.toString());

        System.out.println();
        bobAcct.setAcctName("bob");
        bobAcct.withdrawal(bobAcct.getBalance());
        System.out.println(bobAcct.toString());
    }
}
