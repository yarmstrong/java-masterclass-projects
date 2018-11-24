package com.hoken;

import java.util.Collections;
import java.util.HashMap;
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
            StockItem inStock = list.getOrDefault(item.getName(), item); // give me the one already saved in the list (via name key ) OR assign the new item in the same variable
            if (inStock != item) { // we got a stock saved, so we need to combi the current count to the new count (but we update the item not the one in the list)
                item.adjustStock(inStock.quantityInStock());
            }
            /* item instead of inStock will be manipulated. so that if not yet in the list, we put it in OR update item's quantity by adding the inStock's quantity count and then finally saved it in the list. either way, we'll end up putting item in the list. dont worry if inStock will be overridden or if we are replacing an obj with another obj. for jvm, the 2 obj are already created. ull gain nothing even if u want to retain inStock in the list. one of them will be out of reference after this block so just save any of the 2. but saving item was more efficient */
            list.put(item.getName(), item);
            return item.quantityInStock();
        }
        return 0;
    }

    public int sellStock(String item, int quantity) {
        StockItem inStock = list.getOrDefault(item, null);
        if (inStock != null && inStock.quantityInStock() >= quantity && quantity > 0) {
            inStock.adjustStock(-quantity);
            return quantity;
        }
        return 0;
    }

    public StockItem getItem(String key) {
        return list.get(key);
    }

    public Map<String, StockItem> getList() {
        return Collections.unmodifiableMap(list);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\nStock List:\n");
        double totalCost = 0.0;

        for (Map.Entry<String, StockItem> item : list.entrySet()) {
            StockItem stockItem = item.getValue();
            double itemTotal = stockItem.getPrice() * stockItem.quantityInStock();
            totalCost += itemTotal;

            sb.append(stockItem).append(". There are ")
                    .append(stockItem.quantityInStock()).append(" in stock. Value of items: ")
                    .append(String.format("%.02f", itemTotal)).append(".\n");
        }

        /* difference between:
            for (Map.Entry<String, StockItem> item : list.entrySet()) { ...
            for (StockItem item : list.values()) { ...
            use of Map.Entry is more effort bec u get a key:value, but u only need the value
            2nd one gives u the value right away so less effort */

        return sb.append("Total Cost: ").append(String.format("%.2f", totalCost)).append('\n').toString();
    }

}