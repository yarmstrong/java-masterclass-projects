package com.hoken;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class StockList {
    private final Map<String, StockItem> list;

    public StockList() {
        // this.list = new HashMap<>(); // this will return randomized / unordered list
        this.list = new LinkedHashMap<>(); // this will return ordered list
    }

    public int addStock(StockItem item) {
        if (item != null) {
            StockItem inStock = list.getOrDefault(item.getName(), item);
            if (inStock != item) {
                item.adjustStock(inStock.availableStock()); // the item being processed here is a new different obj from the list, where the reserved value is unchanged and is always set to 0; so call to availableStock will always return the original intended addition
            }
            list.put(item.getName(), item);
            return item.availableStock();
        }
        return 0;
    }

    public int sellStock(String item, int quantity) {
        StockItem inStock = list.get(item); // MainChallenge: replacing the call to adjustStock()
        if (inStock != null && quantity > 0) { // coz supposed to be it passed the reservation
            return inStock.finalizeStock(quantity);
        }
        return 0;
    }

    public int reserveStock(String item, int quantity) { // MainChallenge: to call the reservation code for each item
        StockItem inStock = list.getOrDefault(item, null);
        if (inStock != null && quantity > 0) { // timbuchalka's code
        // if (inStock != null && inStock.availableStock() >= quantity && quantity > 0) { // DEBUG ERROR: not really debug error, but to align with unreserveStock() where we found an error before, ==> verdict: we are already checking for availableStock() here and also in the StockItem.reserveStock(), so redundant code, but i retained it like that, coz if u reserveStock() directly to StockItem, they still need to do the checking. unless the only way to reserveStock is to call the method using this StockList, then ok, good. but if not, u better allow StockItem to protect itself. and ALSO I THINK THE CHECKING SHUD BE THE PRIORITY OF STOCKITEM, NOT THE STOCKLIST, BEC STOCKITEM KNOWS WHAT SHOULD BE ITS AVAILABLE STOCK NOT SOTCKLIST. SO OK WE ARE GONNA CHANGE THE CODE TO PUT THE FUNCTIONALITY TO STOCKITEM ONLY. NO MATTER WHAT, THE ERROR WUD ALWAYS BE CAUGHT BY STOCKITEM SO ITS ALRIGHT
            return inStock.reserveStock(quantity);
        }
        return  0;


    }

    public int unreserveStock(String item, int quantity) { // MainChallenge: to call the unreservation code for each item
        StockItem inStock = list.getOrDefault(item, null);
        if (inStock != null && quantity > 0) { // timbuchalka's code
        // if (inStock != null && inStock.availableStock() >= quantity && quantity > 0) { // DEBUG ERROR: scenario carQuantity = 2;  reserved = 2; available = 0; so its wrong to use availableStock for comparison. also since u already checked from ur basket that u r allowed to deduct this number, then there shudnt really be any more checking.
            return inStock.unreserveStock(quantity);
        }
        return  0;
    }

    public StockItem getItem(String key) {
        return list.get(key);
    }

    public Map<String, StockItem> getList() {
        return Collections.unmodifiableMap(list);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Stock List:\n");
        double totalCost = 0.0;

        for (Map.Entry<String, StockItem> item : list.entrySet()) {
            StockItem stockItem = item.getValue();

            // String name =stockItem.getName();
            // if (name.equals("car") || name.equals("juice") || name.equals("cup") || name.equals("bread")) { // bbm car debugging
                double itemTotal = stockItem.getPrice() * stockItem.availableStock();
                totalCost += itemTotal;

                sb.append(stockItem).append(". There are ")
                        .append(stockItem.availableStock()).append(" in stock. Value of items: ")
                        .append(String.format("%.02f", itemTotal)).append(".\n");
            // }
        }

        /* difference between:
            for (Map.Entry<String, StockItem> item : list.entrySet()) { ...
            for (StockItem item : list.values()) { ...
            use of Map.Entry is more effort bec u get a key:value, but u only need the value
            2nd one gives u the value right away so less effort */

        return sb.append("Total Cost: ").append(String.format("%.2f", totalCost)).append('\n').toString();
    }

}