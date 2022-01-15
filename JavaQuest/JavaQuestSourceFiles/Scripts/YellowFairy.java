package com.company;

import javax.swing.*;
class YellowFairy extends Enemy {

    public YellowFairy() {

        super.setName("Yellow Fairy");
        super.setMaxHealth(50);
        super.setHealth(50);
        super.setDamage(10);
        super.setHitChance(60);
        super.setDodgeChance(50);
        super.setLootAmount(100);
        super.setLevelRangeMin(4);
        super.setLevelRangeMax(8);
        super.setXP(80);
        super.setDefence(3);
        super.setEncounterChance(20);
        super.setSpecialMoveChance(20);
        super.setPNGFileName((getClass().getClassLoader().getResource("Yellow_Fairy.png")));
        super.setSpawnSoundFileName((getClass().getClassLoader().getResource("YellowFairySpawn.wav")));
        super.setEnemyType("Fairy");

        super.setItemDropChance(15);

        super.setHealthPercentToFleeAt(0.3);
        super.setChanceToFlee(35);

        super.setItemDrop(new Item("Fairy Ring", "Reduces spell costs and increases spell effectiveness", "Ring", 0, 0, 6, "Improved spell casting"));

    }

    public void specialMove(Player player, JTextArea out) {

        double damageToTake = 70 - player.getPlayerDefence();

        if (damageToTake <= 0) {
            damageToTake = 1;
        }

        player.takeDamage(damageToTake);
        out.append("\n\nThe yellow fairy blasted you for " + damageToTake + " damage!");
        PlaySound.play((getClass().getClassLoader().getResource("Blast.wav")));
        PlaySound.play((getClass().getClassLoader().getResource(player.getPlayerHurtSounds())));

    }

}