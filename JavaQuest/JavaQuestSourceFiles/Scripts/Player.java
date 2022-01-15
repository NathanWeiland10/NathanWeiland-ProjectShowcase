package com.company;

import javax.swing.*;
import java.util.*;
import java.lang.Math;
import java.net.*;

public class Player extends ArrayList<Item> {

    private static Random rng = new Random();

    private String playerName;
    private double currentPlayerHealth = 100;
    private double maxPlayerHealth = 100;
    private double currentPlayerMana = 10;
    private double maxPlayerMana = 10;
    private double playerMeleeDamage = 10;
    private int chanceToHit = 100;
    private int chanceToDodge = 10;
    private int playerGold = 100;
    private int playerXP = 0;
    private int playerLevel = 1;
    private int XPForLevelUp = 100;
    private double playerDefence = 0;
    private ArrayList<String> spells = new ArrayList<String>();
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private ArrayList<Item> equippedItems = new ArrayList<Item>();
    private String difficulty = "Easy";

    private String weaponSlot = "None";
    private String helmetSlot = "None";
    private String shieldSlot = "None";
    private String breastplateSlot = "None";
    private String ringSlot = "None";

    private String debuffName = "None";
    private int debuffDuration = 0;
    private int debuffTurn = 0;

    private String commands = "\n\nCommand List:\n(As you progress through the game, you will unlock more commands)\n\nFight: Go into battle and fight against an enemy to gain loot and XP\n\nStore: Go inside of the shop to purchase weapons, armor, and other useful items\n\nInn: Go inside of the inn to rest and restore HP and MP\n\nInventory: Check the items you have in your inventory and the items you have equipped\n\nEquip: Choose an item to equip to your player\n\nMenu: Go to back to the menu and change your game preferences (progress will not be reset)\n\nUnequip: Choose an item to unequip to your player\n\nAutoscroll: Enable / fix the console to autoscroll text\n\nRemove: Choose an item to remove from your inventory\n\nSpells: See what spells you have currently unlocked";

    private double healMPCost = 3;
    private double healHPAmount = 25;

    private double midhealMPCost = 5;
    private double midhealHPAmount = 75;

    private double fireballCost = 8;
    private double fireballDamage = 30;

    private double blastCost = 14;
    private double blastDamage = 70;

    private ArrayList<String> attackSounds = new ArrayList<String>(Arrays.asList("Hit.wav", "Hit2.wav", "Hit3.wav", "Hit4.wav"));
    private ArrayList<String> hurtSounds = new ArrayList<String>(Arrays.asList("PlayerHit1.wav", "PlayerHit2.wav", "PlayerHit3.wav"));

    public Player(String n) {
        this.playerName = n;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public double getCurrentPlayerHealth() {
        return this.currentPlayerHealth;
    }

    public double getMaxPlayerHealth() {
        return this.maxPlayerHealth;
    }

    public double getPlayerMeleeDamage() {
        return this.playerMeleeDamage;
    }

    public double getCurrentPlayerMana() { return this.currentPlayerMana; }

    public double getMaxPlayerMana() { return this.maxPlayerMana; }

    public int getPlayerGold() {
        return this.playerGold;
    }

    public int getPlayerXP() {
        return this.playerXP;
    }

    public int getPlayerLevel() {
        return this.playerLevel;
    }

    public int getPlayerHitChance() {
        return this.chanceToHit;
    }

    public int getPlayerDodgeChance() {
        return this.chanceToDodge;
    }

    public int getXpPUntilLevel() {
        return this.XPForLevelUp - this.playerXP;
    }

    public double getPlayerDefence() {
        return this.playerDefence;
    }

    public ArrayList<String> getSpells() {
        return this.spells;
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    public ArrayList<Item> getEquippedItems() {
        return this.equippedItems;
    }

    public String getRingSlot() { return this.ringSlot; }

    public String getPlayerHurtSounds() {
        String sound;
        Collections.shuffle(hurtSounds);
        sound = (hurtSounds.get(0));
        return sound;
    }

    public String getDebuffName() { return this.debuffName; }

    public int getDebuffDuration() { return this.debuffDuration; }

    public void increaseMoney(int amount) {
        this.playerGold += amount;
    }

    public void changeGold(int amount) {
        this.playerGold -= amount;
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void equipItem(Item item, JTextArea output) {

        boolean willEquip = true;

        if (!this.weaponSlot.equals("None") && item.getType().equals("Weapon")) {
            output.append("\n\nYou already have a weapon equipped. Please unequip your current weapon first.");
            willEquip = false;
        } else if (!this.helmetSlot.equals("None") && item.getType().equals("Helmet")) {
            output.append("\n\nYou already have a helmet equipped. Please unequip your current helmet first.");
            willEquip = false;
        } else if (!this.shieldSlot.equals("None") && item.getType().equals("Shield")) {
            output.append("\n\nYou already have a shield equipped. Please unequip your current shield first.");
            willEquip = false;
        } else if (!this.breastplateSlot.equals("None") && item.getType().equals("Breastplate")) {
            output.append("\n\nYou already have a breastplate equipped. Please unequip your current breastplate first.");
            willEquip = false;
        } else if (!this.ringSlot.equals("None") && item.getType().equals("Ring")) {
            output.append("\n\nYou already have a ring equipped. Please unequip your current ring first.");
            willEquip = false;
        }

        if (this.playerLevel < item.getLevelRequirement()) {
            PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
            output.append("\n\nYou are not a high enough level to equip the " + item.getName() + ".");
            willEquip = false;
        }


        if (willEquip) {
            output.append("\n\nYou have equipped the " + item.getName() + ".");

        if (item.getType().equalsIgnoreCase("Weapon")) {
            this.weaponSlot = item.getName();

            output.append("\n\nYour damage has increased by +" + item.getStatusIncrease());
            this.playerMeleeDamage += item.getStatusIncrease();

        } else if (item.getType().equalsIgnoreCase("Helmet")) {
            this.helmetSlot = item.getName();

            output.append("\n\nYour defence has increased by +" + item.getStatusIncrease());
            this.playerDefence += item.getStatusIncrease();

        } else if (item.getType().equalsIgnoreCase("Shield")) {
            this.shieldSlot = item.getName();

            output.append("\n\nYour defence has increased by +" + item.getStatusIncrease());
            this.playerDefence += item.getStatusIncrease();

        } else if (item.getType().equalsIgnoreCase("Breastplate")) {
            this.breastplateSlot = item.getName();

            output.append("\n\nYour defence has increased by +" + item.getStatusIncrease());
            this.playerDefence += item.getStatusIncrease();

        } else if (item.getType().equalsIgnoreCase("Ring")) {
            this.ringSlot = item.getName();

        }
            this.equippedItems.add(item);
            this.inventory.remove(item);
            PlaySound.play((getClass().getClassLoader().getResource("Equip.wav")));

        }
    }

    public void unequipItem(Item item, JTextArea output) {

            output.append("\n\nYou have unequipped the " + item.getName() + ".");

            if (item.getType().equals("Weapon")) {
                weaponSlot = "None";
                this.playerMeleeDamage -= item.getStatusIncrease();

            } else if (item.getType().equals("Helmet")) {
                helmetSlot = "None";
                this.playerDefence -= item.getStatusIncrease();

            } else if (item.getType().equals("Shield")) {
                shieldSlot = "None";
                this.playerDefence -= item.getStatusIncrease();

            } else if (item.getType().equals("Breastplate")) {
                breastplateSlot = "None";
                this.playerDefence -= item.getStatusIncrease();

            } else if (item.getType().equals("Ring")) {
                ringSlot = "None";

            }

            PlaySound.play((getClass().getClassLoader().getResource("Equip.wav")));
            this.equippedItems.remove(item);
            this.inventory.add(item);
    }

    public void removeItem(Item item, JTextArea output) {
        this.inventory.remove(item);
        output.append("\n\nYou have removed the " + item.getName() + " from your inventory.");
        PlaySound.play((getClass().getClassLoader().getResource("Cancel.wav")));
    }

    public void removeAllItems(JTextArea output) {
        this.inventory = new ArrayList<Item>();
        output.append("\n\nYou have removed all items from your inventory.");
        PlaySound.play((getClass().getClassLoader().getResource("Cancel.wav")));
    }

    public void increaseXP(int amount) {
        this.playerXP += amount;
    }

    public boolean playerTryFlee() {
        Random fleeRNG = new Random();
        int chanceToFlee = 50;
        int flee = (1 + (fleeRNG.nextInt(100)));
        if (flee <= chanceToFlee) {
            return true;
        } else {
            return false;
        }
    }

    public void levelUp(JTextArea out, Shop shop) {

        this.playerLevel++;
        this.XPForLevelUp = ((this.playerLevel * 100) * (this.playerLevel));

        int randomHealthIncrease = (int)(Math.random() * (10 - 5 + 1) + 5);
        this.maxPlayerHealth += randomHealthIncrease;
        int randomDamageIncrease = (int)(Math.random() * (5 - 1 + 1) + 1);
        this.playerMeleeDamage += randomDamageIncrease;
        int randomHitChanceIncrease = (int)(Math.random() * (3 - 1 + 1) + 1);
        this.chanceToHit += randomHitChanceIncrease;
        int randomDodgeChanceIncrease = (int)(Math.random() * (3 - 1 + 1) + 1);
        this.chanceToDodge += randomDodgeChanceIncrease;
        int randomManaIncrease = (int)(Math.random() * (8 - 2 + 1) + 2);
        this.maxPlayerMana += randomManaIncrease;

        this.currentPlayerHealth = this.maxPlayerHealth;
        this.currentPlayerMana = this.maxPlayerMana;

        out.append("\n\nYour level has increased: Level " + playerLevel + "!\n\nYour stats have increased:\nMax health increased +" + randomHealthIncrease + "\nDamage increased +" + randomDamageIncrease + "\nChance to hit increased +" + randomHitChanceIncrease + "\nChance to dodge increased +" + randomDodgeChanceIncrease + "\nMax mana increased +" + randomManaIncrease);

        switch(this.playerLevel) {

            // Items will be added to the shop based on the player's level:
            case 2:
                out.append("\nLearned a new spell: 'Heal'!");
                spells.add("Heal");
                commands += "\n\nHeal: Spend " + this.healMPCost + " MP to heal " + this.healHPAmount + " HP";
                shop.addItemToShop(new Item("Copper Sword", "Increases damage by +2 when equipped", "Weapon", 2, 250, 2));
                shop.addItemToShop(new Item("Copper Shield", "Increases defence by +2 when equipped", "Shield", 2, 100, 2));
                break;
            case 3:
                shop.addItemToShop(new Item("Copper Helmet", "Increases damage by +3 when equipped", "Helmet", 3, 400, 3));
                break;
            case 4:
                out.append("\nLearned a new battle spell: 'Fireball'!");
                spells.add("Fireball");
                commands += "\n\nFireball (In-battle only): Spend " + this.fireballCost + " MP to hit an enemy for " + this.fireballDamage + " damage";
                shop.addItemToShop(new Item("Copper Breastplate", "Increases defence by +5 when equipped", "Breastplate", 5, 800, 4));
                shop.addItemToShop(new Item("Steel Sword", "Increases damage by +4 when equipped", "Weapon", 4, 600, 4));
                break;
            case 5:
                shop.addItemToShop(new Item("Steel Shield", "Increases defence by +5 when equipped", "Shield", 5, 1000, 5));
                break;
            case 6:
                out.append("\nLearned a new battle spell: 'Mid-Heal'!");
                spells.add("Mid-Heal");
                commands += "\n\nMid-Heal: Spend " + this.midhealMPCost + " MP to heal " + this.midhealHPAmount + " HP";
                shop.addItemToShop(new Item("Steel Helmet", "Increases defence by +6 when equipped", "Helmet", 6, 1300, 6));
                break;
            case 7:
                shop.addItemToShop(new Item("Steel Breastplate", "Increases defence by +9 when equipped", "Breastplate", 9, 1700, 7));
                shop.addItemToShop(new Item("Gold Sword", "Increases damage by +7 when equipped", "Weapon", 7, 2000, 7));
                break;
            case 8:
                out.append("\nLearned a new spell: 'Blast'!");
                spells.add("Blast");
                commands += "\n\nBlast (In-battle only): Spend " + this.blastCost + " MP to hit an enemy for " + this.blastDamage + " damage";
                shop.addItemToShop(new Item("Gold Shield", "Increases defence by +8 when equipped", "Shield", 8, 2300, 8));
                break;
            case 9:
                out.append("\n\nLearned a new command: Memory Forest. The Memory Forest will allow you to fight against monsters that you have out-leveled.");
                commands += "\n\nMemory Forest: Travel to the Memory Forest to fight monsters that are currently within your level or under your level";
                shop.addItemToShop(new Item("Gold Helmet", "Increases defence by +10 when equipped", "Helmet", 10, 2500, 9));
                shop.addItemToShop(new Item("Gold Breastplate", "Increases defence by +14 when equipped", "Breastplate", 14, 3000, 9));
                break;

        }

        while (this.XPForLevelUp <= this.playerXP) {
            this.levelUp(out, shop);
        }

    }

    public void changeMaxHealth(int change) {
        this.maxPlayerHealth = this.maxPlayerHealth + change;
    }

    public void changeCurrentHealth(int amount) {
        if ((this.currentPlayerHealth += amount) >= this.maxPlayerHealth) {
            this.currentPlayerHealth = this.maxPlayerHealth;
        } else {
            this.currentPlayerHealth += amount;
        }
    }

    public void changeMeleeDamage(int change) {
        this.playerMeleeDamage = this.playerMeleeDamage + change;
    }

    public void changeChanceToHit(int change) {
        this.chanceToHit = this.chanceToHit + change;
    }

    public void changeChanceToDodge(int change) {
        this.chanceToDodge = this.chanceToDodge + change;
    }

    public void changeDefence(int amount) {
        this.playerDefence += amount;
    }

    public void advanceTurn() {
        this.debuffTurn++;
    }

    public void resetTurn() {
        this.debuffTurn = 0;
        this.debuffName = "None";
    }

    public void takeDamage(double amount) {
        this.currentPlayerHealth -= amount;
        if (this.currentPlayerHealth <= 0) {
            this.currentPlayerHealth = 0;
        }
    }

    public void loseMana(double amount) {
        this.currentPlayerMana -= amount;
        if (this.currentPlayerMana <= 0) {
            this.currentPlayerMana = 0;
        }
    }

    public boolean checkIfCanHeal(JTextArea out) {
        double spellCost = 0;
        if (this.ringSlot.equalsIgnoreCase("Fairy Ring")) {
            spellCost = healMPCost / 2;
        } else {
            spellCost = healMPCost;
        }
        if ((this.currentPlayerMana - spellCost) < 0) {
           out.append("\n\nYou do not have enough MP to cast heal!");
            return false;
        } else {
            return true;
        }
    }

    public boolean checkIfCanFireball(JTextArea out) {
        double spellCost = 0;
        if (this.ringSlot.equalsIgnoreCase("Fairy Ring")) {
            spellCost = fireballCost / 2;
        } else {
            spellCost = fireballCost;
        }
        if ((this.currentPlayerMana - spellCost) < 0) {
            out.append("\n\nYou do not have enough MP to cast fireball!");
            return false;
        } else {
            return true;
        }
    }

    public boolean checkIfCanMidHeal(JTextArea out) {
        double spellCost = 0;
        if (this.ringSlot.equalsIgnoreCase("Fairy Ring")) {
            spellCost = midhealMPCost / 2;
        } else {
            spellCost = midhealMPCost;
        }
        if ((this.currentPlayerMana - spellCost) < 0) {
            out.append("\n\nYou do not have enough MP to cast mid-heal!");
            return false;
        } else {
            return true;
        }
    }

    public boolean checkIfCanBlast(JTextArea out) {
        double spellCost = 0;
        if (this.ringSlot.equalsIgnoreCase("Fairy Ring")) {
            spellCost = blastCost / 2;
        } else {
            spellCost = blastCost;
        }
        if ((this.currentPlayerMana - spellCost) < 0) {
            out.append("\n\nYou do not have enough MP to cast blast!");
            return false;
        } else {
            return true;
        }
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(String dif) {
        this.difficulty = dif;
    }

    public void setName(String name) {
        this.playerName = name;
    }

    public void setCurrentPlayerHealth(double amount) { this.currentPlayerHealth = amount; }

    public void setMaxPlayerMana(double amount) { this.currentPlayerMana = amount; }

    public void setDebuffName(String n) { this.debuffName = n; }

    public void setDebuffDuration(int n) { this.debuffDuration = n; }

    public void setStatusEffect(int duration, String statusEffect) {

        this.debuffDuration = duration;
        this.debuffName = statusEffect;
        this.debuffTurn = 0;

    }

    public void takeStatusEffect(JTextArea out) {

        if (this.debuffDuration > this.debuffTurn) {
            if (this.debuffName.equalsIgnoreCase("Poison")) {
                out.append("\n\nYou took 5.0 poison damage!");
                this.currentPlayerHealth -= 5.0;
            } else if (this.debuffName.equalsIgnoreCase("Cursed")) {
                out.append("\n\nYou took 10.0 cursed damage!");
                this.currentPlayerHealth -= 10.0;
            }
        } else if (this.debuffDuration == this.debuffTurn && !this.debuffName.equalsIgnoreCase("None")) {
            out.append("\n\nYour " + this.debuffName + " debuff has worn off");
            this.debuffDuration = 0;
            this.debuffName = "\n\nNone";
        } else {
            this.debuffDuration = 0;
            this.debuffName = "\n\nNone";
        }

    }

    public void heal() {

            double spellCost = 0;
            double healAmount = 0;
            if (this.ringSlot.equalsIgnoreCase("Fairy Ring")) {
                spellCost = healMPCost / 2;
                healAmount = healHPAmount * 2;
            } else {
                spellCost = healMPCost;
                healAmount = healHPAmount;
            }

            PlaySound.play((getClass().getClassLoader().getResource("Heal.wav")));
            this.currentPlayerHealth += healAmount;
            this.currentPlayerMana -= spellCost;
            if (this.currentPlayerHealth >= this.maxPlayerHealth) {
                this.currentPlayerHealth = this.maxPlayerHealth;
            }

    }

    public void midHeal() {

        double spellCost = 0;
        double healAmount = 0;
        if (this.ringSlot.equalsIgnoreCase("Fairy Ring")) {
            spellCost = midhealMPCost / 2;
            healAmount = midhealHPAmount * 2;
        } else {
            spellCost = midhealMPCost;
            healAmount = midhealHPAmount;
        }

        PlaySound.play((getClass().getClassLoader().getResource("Heal.wav")));
        this.currentPlayerHealth += healAmount;
        this.currentPlayerMana -= spellCost;
        if (this.currentPlayerHealth >= this.maxPlayerHealth) {
            this.currentPlayerHealth = this.maxPlayerHealth;
        }

    }

    public void hit(Enemy other, JTextArea out, ScreenShaker shaker) {
        int chance = rng.nextInt(100);
        if (chance <= this.chanceToHit) {
            double damageToTake = this.playerMeleeDamage - (int)other.getDefence();

            if (other.getEnemyType().equalsIgnoreCase("Slime") && this.ringSlot.equalsIgnoreCase("Slime Ring")) {
                damageToTake += 3;
            }

            if (damageToTake <= 0) {
                damageToTake = 1;
            }
            other.changeHealth(damageToTake * -1);
            out.append("\n\nYou hit the enemy for " + damageToTake + " damage.");

            Collections.shuffle(attackSounds);

            PlaySound.play(getClass().getClassLoader().getResource(attackSounds.get(0)));

            shaker.startShake();

        } else {
            out.append("\n\nYou swung and missed.");
        }

        if (other.getCurrentHealth() <= 0) {
            other.setHealth(0);
        }

    }

    public void fireball(Enemy other, JTextArea out, ScreenShaker shaker) {

        double damageToTake = this.fireballDamage - (int)other.getDefence();
        double spellCost = 0;

        if (other.getEnemyType().equalsIgnoreCase("Slime") && this.ringSlot.equalsIgnoreCase("Slime Ring")) {
            damageToTake += 3;
        } else if (this.ringSlot.equalsIgnoreCase("Fairy Ring")) {
            spellCost = fireballCost / 2;
            damageToTake *= 2;
        } else {
            spellCost = fireballCost;
        }

        if (damageToTake <= 0) {
            damageToTake = 1;
        }

        other.changeHealth(damageToTake * -1);
        out.append("\n\nYou struck the enemy with a fireball for " + damageToTake + " damage.");
        PlaySound.play((getClass().getClassLoader().getResource("Fireball.wav")));
        shaker.startShake();

        if (other.getCurrentHealth() <= 0) {
            other.setHealth(0);
        }

        this.currentPlayerMana -= spellCost;

    }

    public void blast(Enemy other, JTextArea out, ScreenShaker shaker) {

        double damageToTake = this.blastDamage - (int)other.getDefence();
        double spellCost = 0;

        if (other.getEnemyType().equalsIgnoreCase("Slime") && this.ringSlot.equalsIgnoreCase("Slime Ring")) {
            damageToTake += 3;
        } else if (this.ringSlot.equalsIgnoreCase("Fairy Ring")) {
            spellCost = blastDamage / 2;
            damageToTake *= 2;
        } else {
            spellCost = blastCost;
        }

        if (damageToTake <= 0) {
            damageToTake = 1;
        }

        other.changeHealth(damageToTake * -1);
        out.append("\n\nYou blasted the enemy for " + damageToTake + " damage.");
        PlaySound.play((getClass().getClassLoader().getResource("Blast.wav")));
        shaker.startShake();

        if (other.getCurrentHealth() <= 0) {
            other.setHealth(0);
        }

        this.currentPlayerMana -= spellCost;

    }

    public String playerCommands() {
        return this.commands;
    }

    public String toString() {
        int untilNextLevel = this.XPForLevelUp - this.playerXP;
        return this.playerName + " (Level " + this.playerLevel + "):\nDifficulty: " + this.difficulty + "\nHealth: " + + this.currentPlayerHealth + " / " + this.maxPlayerHealth + "\nMana: " + this.currentPlayerMana + " / " + this.maxPlayerMana + "\n\nAttack power: " + this.playerMeleeDamage + "\nDefence: " + this.playerDefence + "\nHit chance: " + this.chanceToHit + "\nDodge chance: " + this.chanceToDodge + "\n\nGold: " + this.playerGold + "\nXP: " + this.playerXP + "\nXP until next level: " + untilNextLevel + "\n\nInventory: " + this.inventoryToString() + "\n\nEquipped Items: " + equippedItemsToString();
    }

    public String compareWith() {
        return this.playerName + ": (Health: " + this.currentPlayerHealth + " / " + this.maxPlayerHealth + ", Attack Power: " + this.playerMeleeDamage + ")";
    }

    public String checkSpells() {
        String currentSpells = "";
        if (spells.size() == 0) {
            currentSpells = "\nNo spells currently learned.";
        } else {
            currentSpells = spells.toString();
        }
        return "\n\nCurrent spells: " + currentSpells;
    }

    public String checkInventory() {
        String items = "";
        if (inventory.size() == 0) {
            items = "You have no items currently in your inventory.";
        } else {
            items = this.itemToString();
        }
        return "Inventory: " + items + "\n\nEquipped weapon: " + this.weaponSlot + "\nEquipped helmet: " + this.helmetSlot + "\nEquipped shield: " + this.shieldSlot + "\nEquipped breastplate: " + this.breastplateSlot + "\nEquipped ring: " + this.ringSlot;
    }

    public String inventoryToString() {
        String items = "";
        int spaceCounter = this.inventory.size() - 1;
        if (this.inventory.size() == 0) {
            return ("No items");
        } else {
            for (int i=0; i<this.inventory.size(); i++) {
                if (spaceCounter != 0) {
                    items += this.inventory.get(i).getName() + ", ";
                } else {
                    items += this.inventory.get(i).getName();
                }
            }
        }
        return items;
    }

    public String itemToString() {
        String items = "";
        int enterCounter = this.inventory.size() - 1;
            for (int i=0; i<this.inventory.size(); i++) {
                if (enterCounter != 0) {
                    items += this.inventory.get(i).getName() + " (" + this.inventory.get(i).getType() + ", Lvl required: " + this.inventory.get(i).getLevelRequirement() + ")\n";
                } else {
                    items += this.inventory.get(i).getName() + " (" + this.inventory.get(i).getType() + ", Lvl required: " + this.inventory.get(i).getLevelRequirement() + ")";
                }
            }

        return items;
    }

    public String equippedItemsToString() {
        return "\nWeapon: " + this.weaponSlot + "\nHelmet: " + this.helmetSlot + "\nShield: " + this.shieldSlot + "\nBreastplate: " + this.breastplateSlot + "\nRing: " + this.ringSlot;
    }

    public String getItemNames() {
        String allItems = "";
        for (int i=0; i<this.inventory.size(); i++) {
            allItems += this.inventory.get(i).getName();
        }
        for (int i=0; i<this.equippedItems.size(); i++) {
            allItems += this.equippedItems.get(i).getName();
        }
        return allItems;
    }

    public String equipToString() {
        String items = "";
        for (int i = 0; i < this.getInventory().size(); i++) {
            items += ("\n(" + (i) + ") " + this.getInventory().get(i).getName() + ": " + this.getInventory().get(i).getDescription() + " (Lvl Req: " + this.getInventory().get(i).getLevelRequirement() + ")");
        }
        return items;
    }

    public String UnequipToString() {
        String items = "";
        for (int i = 0; i < this.getEquippedItems().size(); i++) {
            items += ("\n(" + (i) + ") " + this.getEquippedItems().get(i).getName() + ": " + this.getEquippedItems().get(i).getDescription());
        }
        return items;
    }

}