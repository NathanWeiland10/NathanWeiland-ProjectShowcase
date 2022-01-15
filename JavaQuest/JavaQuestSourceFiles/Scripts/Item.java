package com.company;

public class Item {

    private String itemName;
    private String itemDescription;
    private String itemType;
    private int statusIncrease;
    private int itemCost;

    private int levelRequirement;

    private String specialEffect;

    public Item(String in, String id, String it, int si, int c, int lr) {
        this.itemName = in;
        this.itemDescription = id;
        this.itemType = it;
        this.statusIncrease = si;
        this.itemCost = c;
        this.levelRequirement = lr;
    }

    public Item(String in, String id, String it, int si, int c, int lr, String sp) {
        this.itemName = in;
        this.itemDescription = id;
        this.itemType = it;
        this.statusIncrease = si;
        this.itemCost = c;
        this.levelRequirement = lr;
        this.specialEffect = sp;
    }


    public String getName() {
        return this.itemName;
    }

    public String getDescription() {
        return this.itemDescription;
    }

    public int getCost() {
        return this.itemCost;
    }

    public String getType() {
        return this.itemType;
    }

    public int getLevelRequirement() {
        return this.levelRequirement;
    }

    public int getStatusIncrease() {
        return this.statusIncrease;
    }

    public String toString() {
        String type = "";
        if (this.itemType == "Weapon") {
            type = "to attack";
        } else if (this.itemType == "Helmet") {
            type = "to defense";
        } else if (this.itemType == "Shield") {
            type = "to defense";
        } else if (this.itemType == "Breastplate") {
            type = "to defense";
        }
        return this.itemName + ": +" + this.statusIncrease + " " + type + ", Lvl required: " + this.levelRequirement;
    }


}