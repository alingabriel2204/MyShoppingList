package com.alin.myshoppinglist.models;

import java.util.Map;

public class ShoppingListItem {
    public String productName;
    public long price;
    public long qty;
    public boolean checked;

    public ShoppingListItem() {
        productName = "";
        this.qty = 1;
        price = -1;
    }

    public ShoppingListItem(String productName) {
        this.productName = productName;
        this.qty = 1;
        this.price = -1;
    }

    public ShoppingListItem(String productName, int price) {
        this.productName = productName;
        this.qty = 1;
        this.price = price;
    }

    public ShoppingListItem(String productName, int price, int qty) {
        this.productName = productName;
        this.qty = qty;
        this.price = price;
    }

    public ShoppingListItem(String productName, int price, int qty, boolean checked) {
        this.productName = productName;
        this.qty = qty;
        this.price = price;
        this.checked = checked;
    }

    public ShoppingListItem(Map<String, Object> m) {
        this.productName = (String)m.get("productName");
        this.qty = (long)m.get("qty");
        this.price = (long)m.get("price");
        this.checked = (boolean)m.get("checked");
    }

    @Override
    public String toString() {
        return productName;
    }
}
