package com.company;

import javax.swing.*;
import java.util.*;
public class Shop extends ArrayList<Item> {

    public Shop() {

    }

    private ArrayList<Item> allItems = new ArrayList<Item>();
    private ArrayList<Item> itemsInShop = new ArrayList<Item>();

    public void addItemToCompleteList(Item item) {
        this.allItems.add(item);
    }

    public void addItemToShop(Item item) {
        this.itemsInShop.add(item);
    }

    public ArrayList<Item> getItemsInShop() {
        return this.itemsInShop;
    }

    public void removeItem(int i) {
        this.itemsInShop.remove(i);
    }

    public String listItems(JTextArea out) {
        String items = "";
        if (this.itemsInShop.size() == 0) {
            return ("\n\nThere are no items currently in the shop.");
        } else {
            out.append("\n\nHere are the current items in the shop:\n");
            for (int i=0; i<itemsInShop.size(); i++) {
                items += "\n(" + (i) + ") " + this.itemsInShop.get(i).getName() + " (Cost: " + this.itemsInShop.get(i).getCost() + ", Lvl required: " + this.itemsInShop.get(i).getLevelRequirement() + "): " + this.itemsInShop.get(i).getDescription();
            }
        }
        return items;
    }


}