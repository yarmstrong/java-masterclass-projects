package com.hoken;

import java.util.*;

public class Basket {
    private final String name;
    private final Map<StockItem, Integer> list; /* big note here: i believe this mapping is just to map the item with its basket quantity? coz in the addStock() u r not really using the StockItem.adjustStock(). ur saving the basketCount into this basket list, and not in the StockItem's own field. */

    public Basket(String name) {
        this.name = name;
        // this.list  = new HashMap<>(); // this will return randomized / unordered list
        this.list = new TreeMap<>(); // this will return ordered list
    }

    public int addToBasket(StockItem item, int quantity) { // MainChallenge: update will always call the Map.put() either for update or addition
        if (item != null && quantity > 0) {
            int inBasket = list.getOrDefault(item, 0); // so if null is the result, it will be automatically set to 0
            list.put(item, quantity+inBasket);
            return quantity;
        }
        return 0;
    }

    public int removeFromBasket(StockItem item, int quantity) { // MainChallenge: either update by Map.put() or delete item from the list Map.remove()
        if (item != null && quantity > 0) {
            int inBasket = list.getOrDefault(item, 0); // so if null is the result, it will be automatically set to 0

            if (inBasket == 0) return 0;

            int newQuantity = inBasket - quantity;
            if (newQuantity > 0) {
                list.put(item, newQuantity);
            } else if (newQuantity == 0) { // remove
                list.remove(item);
            }
            return quantity;
        }
        return 0;
    }

    public void clearBasket() {
        list.clear();
    }

    public Map<StockItem, Integer> getItems() {
        return Collections.unmodifiableMap(list);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Shopping basket ");
        sb.append(name).append(" contains ").append(list.size()).append(" type of items.\n");
        double totalCost = 0.0;

        for (Map.Entry<StockItem, Integer> item : list.entrySet()) {
            // here u need both of the mapped data
            sb.append(item.getKey()).append(". || ").append(item.getValue()).append(" was purchased by you.\n");
            totalCost += item.getKey().getPrice() * item.getValue(); // so yeah ur not really using the stock item's stock quantity
        }

        return sb.append("Total cost: ").append(String.format("%.2f", totalCost)).toString();
    }
}