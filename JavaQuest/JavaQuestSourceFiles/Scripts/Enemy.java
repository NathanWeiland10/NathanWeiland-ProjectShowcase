package com.company;

import javax.swing.*;
import java.util.*;
import java.net.*;

public abstract class Enemy {

    private static Random rng = new Random();

    private String name;
    private double currentHealth;
    private double maxHealth;
    private double damage;
    private double hitChance;
    private double dodgeChance;
    private int lootAmount;
    private int levelRangeMin;
    private int levelRangeMax;
    private int XP;
    private double defence;
    private int encounterChance;
    private int specialMoveChance;
    private URL pngFileName;
    private URL spawnSoundFileName;

    private Item itemDrop;
    private int itemDropChance;
    private double healthPercentToFleeAt;
    private int chanceToFlee;

    private String enemyType;

    public Enemy() {

    }

    public abstract void specialMove(Player player, JTextArea out);

    public String getName() {
        return this.name;
    }

    public double getCurrentHealth() {
        return this.currentHealth;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }

    public double getDamage() {
        return this.damage;
    }

    public double getHitChance() {
        return this.hitChance;
    }

    public double getDodgeChance() {
        return this.dodgeChance;
    }

    public int getLootAmount() {
        return this.lootAmount;
    }

    public int getMinLevelAmount() {
        return this.levelRangeMin;
    }

    public int getMaxLevelAmount() {
        return this.levelRangeMax;
    }

    public int getXP() {
        return this.XP;
    }

    public double getDefence() {
        return this.defence;
    }

    public int getEncounterChance() {
        return this.encounterChance;
    }

    public int getSpecialMoveChance() {
        return this.specialMoveChance;
    }

    public URL getPNGFileName() {
        return this.pngFileName;
    }

    public URL getSpawnSoundFileName() {
        return this.spawnSoundFileName;
    }

    public String getItemDropName() {
        return this.itemDrop.getName();
    }

    public double getHealthPercentToFleeAt() {
        return this.healthPercentToFleeAt;
    }

    public int getChanceToFlee() { return this.chanceToFlee; }

    public String getEnemyType() { return this.enemyType; }

    public int getItemDropChance() { return itemDropChance; }

    public boolean tryItemDrop() {
        Random dropRNG = new Random();
        int dropChance = (1 + (dropRNG.nextInt(100)));
        if (dropChance <= this.itemDropChance) {
            return true;
        } else {
            return false;
        }
    }

    public void dropItem(Player player, JTextArea output) {
        output.append("\n\nThe enemy dropped an item: " + this.itemDrop.getName() + "!");
        player.addToInventory(this.itemDrop);
        PlaySound.play((getClass().getClassLoader().getResource("DropItem.wav")));
    }

    public boolean tryEncounter() {
            Random encRNG = new Random();
            int encChance = (1 + (encRNG.nextInt(100)));
            if (encChance <= this.encounterChance) {
                return true;
            } else {
                return false;
            }
    }

    public boolean trySpecialMove() {
        Random specialRNG = new Random();
        int specialChance = (1 + (specialRNG.nextInt(100)));
        if (specialChance <= this.specialMoveChance) {
            return true;
        } else {
            return false;
        }
    }

    public boolean tryFlee() {
        Random fleeRNG = new Random();
        int flee = (1 + (fleeRNG.nextInt(100)));
        if (flee <= this.chanceToFlee) {
            return true;
        } else {
            return false;
        }
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setMaxHealth(double m) {
        this.maxHealth = m;
    }

    public void setHealth(double change) {
        this.currentHealth = change;
    }

    public void setDamage(double d) {
        this.damage = d;
    }

    public void setHitChance(double h) {
        this.hitChance = h;
    }

    public void setDodgeChance(double d) {
        this.dodgeChance = d;
    }

    public void setLootAmount(int l) {
        this.lootAmount = l;
    }

    public void setLevelRangeMin(int m) {
        this.levelRangeMin = m;
    }

    public void setLevelRangeMax(int m) {
        this.levelRangeMax = m;
    }

    public void setXP(int x) {
        this.XP = x;
    }

    public void setDefence(double d) {
        this.defence = d;
    }

    public void setPNGFileName(URL f) {
        this.pngFileName = f;
    }

    public void setSpawnSoundFileName(URL s) {
        this.spawnSoundFileName = s;
    }

    public void setEncounterChance(int e) {
        this.encounterChance = e;
    }

    public void setSpecialMoveChance(int s) {
        this.specialMoveChance = s;
    }

    public void setItemDropChance(int c) {
        this.itemDropChance = c;
    }

    public void setItemDrop(Item item) {
        this.itemDrop = item;
    }

    public void changeHealth(double change) {
        this.currentHealth = this.currentHealth + change;
    }

    public void setHealthPercentToFleeAt(double s) {
        this.healthPercentToFleeAt = s;
    }

    public void setChanceToFlee(int c) { this.chanceToFlee = c; }

    public void setEnemyType(String t) { this.enemyType = t; }

    public void hit(Player other, JTextArea out) {

        int chance = rng.nextInt(100);
        if (chance <= this.hitChance) {
            double damageToTake = this.damage - other.getPlayerDefence();
            if (damageToTake <= 0) {
                damageToTake = 1;
            }
            other.takeDamage(damageToTake);
            out.append("\n\nThe enemy hit you for " + damageToTake + " damage.");
            PlaySound.play((getClass().getClassLoader().getResource(other.getPlayerHurtSounds())));
        } else {
            out.append("\n\nThe enemy swung and missed.");
        }

    }

    public String toString() {
        return "\n\n" + name + ":\nCurrent health: " + currentHealth + " / " + maxHealth;
    }

    public String compareWith() {
        return this.name + ": (Health: " + this.currentHealth + " / " + this.maxHealth + ", Attack Power: " + this.damage + ")";
    }

}