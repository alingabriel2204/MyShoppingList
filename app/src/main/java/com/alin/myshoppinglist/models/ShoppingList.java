package com.alin.myshoppinglist.models;

import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
    public List<ShoppingListItem> items;
    public String name;

    public ShoppingList() {
        name = "";
        items = new ArrayList<>();
    }

    public ShoppingList(String name, List<ShoppingListItem> items) {
        this.name = name;
        this.items = items;
    }

    public void addItem(ShoppingListItem item) {
        items.add(item);
    }

    public void addItem(String item) {
        items.add(new ShoppingListItem(item));
    }

    @Override
    public String toString() {
        //return name + "\n" + new JSONArray(items).toString();
        return name;
    }
}
