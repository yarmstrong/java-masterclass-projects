package com.hoken;

import java.util.Map;

/**
 * GOAL OF MAIN CHALLENGE:
 * 1. add a reservation capability for a StockItem in our StockList
 * 2. when reserved, amount of item is not yet reflected since it
 * hasnt been checkedout yet
 * 3. but current reservations must prevent other reservation attempts
 * from reserving from than what the item can really reserve for
 * potential selling
 * 4. add also the ability to cancel the reservation
 * 5. add a checkout capability which will finally allow u to reduct
 * ur reservations from the StockList
 * 6. once checkout is completed, the user's basket (cart) shud be cleared
 */
public class Main {

    private static StockList stockList = new StockList();
    private static Basket timBasket = new Basket("Tim");

    public static void main(String[] args) {

        // list the available items from the stocklist

        // addToCheckOut(StockItem, quantity)
        /*  1. StockList.reserve(item, quantity)
            2. addToBasket():
            3. cancelRequest():
            4. ADDITIONAL: need to update the getter of quantity to reflect
                the reserveCount */

        // checkOutBasket(list)
        /*  loop for each item
            1. StockList.sellStock(item, quantity) to finally reduce the list count
            2. prepareTheTotalAndClearTheBasket() */

        /* the methods by level of class:
            1. StockItem.reserveStock():
                toReserveCount <= realQuantity - reservedCount;
                if true, reserved++++, then reflect addtion in ur basket
            2. StockItem.unreserveStock():
                toCancelCount <= reservedCount (if only theres reservation#)
                if true, reserved++++, then reflect subtraction in ur basket
            3. StockItem.availableStock()
                reflect the reserved to the outgoing quantity wi,thot changing the current value
            4. StockItem.finalizeStock()
                reflect the reserved to the quantity and remove count from reserved

            5. StockList.reserveStock() to call to StockItem.reserveStock()
            6. StockList.unreserveStock() to call to StockItem.unreserveStock()
            7. StockList.sellStock() => updated to call StockItem.finalizeStock()

            8. Basket.addToBasket() to add/update the cart
            9. Basket.removeFromBasket() to reduce/remove from the cart
            10. Basket.clearBasket() to literal clear

            11. Main.buyStock() => triggers the reservation and add to cart, and add more
            12. Main.removeStock() => triggera the unreserve and remove or reduce from cart
            13. Main.checkOut() => triggers the

         */

        /* PART 1: INITIALIZING THE STOCKLIST */
        StockItem temp = new StockItem("bread", 0.86, 100);
        stockList.addStock(temp);

        temp = new StockItem("cake", 1.10, 7);
        stockList.addStock(temp);

        temp = new StockItem("car", 12.50, 2);
        stockList.addStock(temp);

        temp = new StockItem("chair", 62.0, 10);
        stockList.addStock(temp);

        temp = new StockItem("cup", 0.50, 200);
        stockList.addStock(temp);
        temp = new StockItem("cup", 0.45, 7);
        stockList.addStock(temp);

        temp = new StockItem("door", 72.95, 4);
        stockList.addStock(temp);

        temp = new StockItem("juice", 2.50, 36);
        stockList.addStock(temp);

        temp = new StockItem("phone", 96.99, 35);
        stockList.addStock(temp);

        temp = new StockItem("towel", 2.40, 80);
        stockList.addStock(temp);

        temp = new StockItem("vase", 8.76, 40);
        stockList.addStock(temp);

        System.out.println(stockList);

        // for (String itemName : stockList.getList().keySet()) {
        //     System.out.println(itemName);
        // }


        /* PART2 : SELLING STOCKS AND BUYING STOCKS */
        // Basket timBasket = new Basket("Tim");
        buyItem(timBasket, "car", 1);
        System.out.println(timBasket);

        buyItem(timBasket, "car", 1);
        System.out.println(timBasket);

        if (buyItem(timBasket, "car", 1) != 1) {
            // no more car @ this point
            System.out.println("no more cars to reserve..");
        }

        System.out.println();
        buyItem(timBasket, "spanner", 1);
        System.out.println(); // not in stock
        System.out.println(timBasket);

        buyItem(timBasket, "juice", 4);
        buyItem(timBasket, "cup", 12);
        buyItem(timBasket, "bread", 1);
        // System.out.println(timBasket);

        System.out.println(stockList);

        Basket custBasket = new Basket("customer");
        buyItem(custBasket, "cup", 100);
        buyItem(custBasket, "juice", 5);
        removeItem(custBasket, "cup", 1);
        System.out.println(custBasket);

        System.out.println("\nbbm 1 stock list ----------------------");
        System.out.println(stockList);

        removeItem(timBasket, "car", 1);

        System.out.println("1ST REMOVAL OF CAR:");
        System.out.println(stockList);
        System.out.println(timBasket);

        removeItem(timBasket, "cup", 9);

        System.out.println("REMOVAL OF 9CUPS:");
        System.out.println(stockList);
        System.out.println(timBasket);

        removeItem(timBasket, "car", 1);

        System.out.println("2nd REMOVAL OF CAR:");
        System.out.println(stockList);
        System.out.println(timBasket);

        System.out.println("cars removed:" + removeItem(timBasket, "car", 1));

        System.out.println("\n\nbbm 2 stock list ----------------------");
        System.out.println(stockList);

        System.out.println(timBasket);
        System.out.println("this tim before----------------");

        removeItem(timBasket, "bread", 1);
        removeItem(timBasket, "cup", 3);

        System.out.println("\n\nbbm 3 stock list tim's cup is now 0 ----------------------");
        System.out.println(stockList);
        System.out.println(timBasket);

        removeItem(timBasket, "juice", 4);
        removeItem(timBasket, "cup", 3);

        System.out.println("\n\nbbm 4 stock list ----------------------");
        System.out.println(stockList);

        System.out.println("this tim after----------------");
        System.out.println(timBasket);

        System.out.println("\nthe stockList after tim's non-purchase----------------");
        System.out.println(stockList);

        System.out.println("\nBefore and after checkout of custBasket:");
        System.out.println(custBasket);
        System.out.println(stockList);
        checkOut(custBasket);
        System.out.println(custBasket);
        System.out.println(stockList);

        StockItem car = stockList.getList().get("car");
        if (car != null) {
            car.adjustStock(2000);
        }
        if (car != null) {
            stockList.getItem("car").adjustStock(-1000);
        }
        System.out.println(stockList);

        checkOut(timBasket);
        System.out.println(timBasket);
        System.out.println(stockList);
    }

    public static int buyItem(Basket basket, String item, int quantity) {
        StockItem stockItem = stockList.getItem(item);
        if (stockItem == null) {
            System.out.println("We dont sell this item: "+item);
            return 0;
        }
        // MainChallenge: process here is to check 1st for reservation, only then can u add it to ur cart bec u have check if they were available first
        if (stockList.reserveStock(item, quantity) > 0) { // sellStock() replaced by reserveStock()
            return basket.addToBasket(stockItem, quantity); // once we have reserved, then we can reflect it to our cart
        }
        return 0;
    }

    public static int removeItem(Basket basket, String item, int quantity) {
        StockItem stockItem = stockList.getItem(item);
        if (stockItem == null) {
            System.out.println("We dont sell this item: "+item);
            return 0;
        }
        // MainChallenge: process here is to check if ur able to remove such quantity from ur cart, only then can u request for unreservation
        if (basket.removeFromBasket(stockItem, quantity) == quantity) { // able to remove such amount from ur cart
            return stockList.unreserveStock(item, quantity);
        }
        return 0;
    }

    // MainChallenge: to buy from stock and clear ur cart
    public static void checkOut(Basket basket) {
        if (basket.getItems().isEmpty()) {
            System.out.println("you have nothing to checkout.");
            return;
        }
        // loop thru the basket list and do the buyItem()
        for (Map.Entry<StockItem, Integer> item : basket.getItems().entrySet()) {
            stockList.sellStock(item.getKey().getName(), item.getValue());
        }
        basket.clearBasket();
    }
}
