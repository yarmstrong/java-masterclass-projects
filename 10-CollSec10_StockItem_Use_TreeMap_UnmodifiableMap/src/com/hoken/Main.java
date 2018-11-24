package com.hoken;

/**
 ** LIST INTERFACE => ArrayList and LinkedList
 *   => ordering is important from ArrayList but big list cud be heavy in memory
 *   => while LinkedList is also burdened by memory consumption, it is more
 *   efficient in inserting into the list compared to normal ArrayList
 ** MAP INTERFACE
 *   => HashMap and variant of LinkedHashMap : has key-value pair, with is easier
 *   in retrieval if u already know the key to use instead of doing the loop (ie:
 *   the direction game adventure) but since objects are saved into memory buckets
 *   irrelevant of its order, it wud always give an unordered list. but with
 *   LinkedHashMap use get the power of hash and also the linking capability
 *   => Sorted map: Tree map
 ** SET INTERFACE
 *   => HashSet and variant of linked HashSet
 *   => sorted set: tree set
 ** QUEUE / DEQUEUE
 */
public class Main {
    private static StockList stockList = new StockList();
    private static Basket timBasket = new Basket("Tim");

    public static void main(String[] args) {

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

        for (String itemName : stockList.getList().keySet()) {
            System.out.println(itemName);
        }


        /* PART2 : SELLING STOCKS AND BUYING STOCKS */
        // Basket timBasket = new Basket("Tim");
        sellItem(timBasket, "car", 1);
        System.out.println(timBasket);

        sellItem(timBasket, "car", 1);
        System.out.println(timBasket);

        if (sellItem(timBasket, "car", 1) != 1) {
            // no more car @ this point
            System.out.println("no more cars to sell..");
        }
        sellItem(timBasket, "spanner", 1); // not in stock
        System.out.println(timBasket);

        sellItem(timBasket, "juice", 4);
        sellItem(timBasket, "cup", 12);
        sellItem(timBasket, "bread", 1);
        System.out.println(timBasket);

        System.out.println(stockList);

    }

    public static int sellItem(Basket basket, String item, int quantity) {
        StockItem stockItem = stockList.getItem(item);
        if (stockItem == null) {
            System.out.println("We dont sell this item: "+item);
            return 0;
        }
        if (stockList.sellStock(item, quantity) > 0) { // able to deduct from store
            if (basket.addToBasket(stockItem, quantity) > 0) { // so add it to ur basket
                return quantity;
            }
        }
        return 0;
    }
}
