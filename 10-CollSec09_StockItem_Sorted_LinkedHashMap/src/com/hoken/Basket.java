package com.hoken;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Basket {
    private final String name;
    private final Map<StockItem, Integer> list; /* big note here: i believe this mapping is just to map the item with its basket quantity? coz in the addStock() u r not really using the StockItem.adjustStock(). ur saving the basketCount into this basket list, and not in the StockItem's own field. */

    public Basket(String name) {
        this.name = name;
        this.list  = new HashMap<>();
    }

    public int addToBasket(StockItem item, int quantity) {
        if (item != null && quantity > 0) { // process it
            int inBasket = list.getOrDefault(item, 0); // if item is not in the list yet, we cant have null value as int value so we use this particular method to return for us a default value we want if it fails
            list.put(item, quantity+inBasket);
            return inBasket; // OR SHUDNT IT BE quantity+inBasket
        }
        return 0;
    }

    public Map<StockItem, Integer> items() {
        return Collections.unmodifiableMap(list);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\nShopping basket ");
        sb.append(name).append(" contains ").append(list.size()).append(" items.\n");
        double totalCost = 0.0;

        for (Map.Entry<StockItem, Integer> item : list.entrySet()) {
            // here u need both of the mapped data
            sb.append(item.getKey()).append(". ").append(item.getValue()).append(" purchased.\n");
            totalCost += item.getKey().getPrice() * item.getValue(); // so yeah ur not really using the stock item's stock quanity
        }

        return sb.append("Total cost: ").append(totalCost).append(".\n").toString();
    }
}