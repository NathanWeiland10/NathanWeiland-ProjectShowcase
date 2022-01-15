package com.company;

import javax.swing.*;
class Shade extends Enemy {

    public Shade() {
        super.setName("Shade");
        super.setMaxHealth(100);
        super.setHealth(100);
        super.setDamage(22);
        super.setHitChance(70);
        super.setDodgeChance(80);
        super.setLootAmount(300);
        super.setLevelRangeMin(10);
        super.setLevelRangeMax(17);
        super.setXP(250);
        super.setDefence(5);
        super.setEncounterChance(25);

        super.setSpecialMoveChance(25);

        super.setPNGFileName((getClass().getClassLoader().getResource("Shade.png")));
        super.setSpawnSoundFileName((getClass().getClassLoader().getResource("ShadeSpawn.wav")));
        super.setEnemyType("Shadow");

        super.setItemDropChance(10);

        super.setHealthPercentToFleeAt(0.2);
        super.setChanceToFlee(25);

        super.setItemDrop(new Item("Shadow Armor", "Increases defence by +18 when equipped", "Breastplate", 18, 0, 14));
    }

    public void specialMove(Player player, JTextArea out) {

        double manaDrain = 10;

        player.loseMana(manaDrain);
        out.append("\n\nThe shade stole " + manaDrain + " mana!");
        PlaySound.play((getClass().getClassLoader().getResource("ManaSteal.wav")));

    }

}