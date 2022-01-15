package com.company;

import javax.swing.*;
class RedSlime extends Enemy {

    public RedSlime() {
        super.setName("Red Slime");
        super.setMaxHealth(30);
        super.setHealth(30);
        super.setDamage(7);
        super.setHitChance(65);
        super.setDodgeChance(20);
        super.setLootAmount(25);
        super.setLevelRangeMin(2);
        super.setLevelRangeMax(5);
        super.setXP(50);
        super.setDefence(4);
        super.setEncounterChance(40);
        super.setSpecialMoveChance(50);
        super.setPNGFileName((getClass().getClassLoader().getResource("Red_Slime.png")));
        super.setSpawnSoundFileName((getClass().getClassLoader().getResource("RedSlimeSpawn.wav")));
        super.setEnemyType("Slime");

        super.setItemDropChance(10);

        super.setHealthPercentToFleeAt(0.3);
        super.setChanceToFlee(15);

        super.setItemDrop(new Item("Luxurious Ring", "Doubles the amount of gold dropped by enemies when equipped", "Ring", 1, 0, 4, "2x enemy gold drop"));
    }

    public void specialMove(Player player, JTextArea out) {

        double damageToTake = (super.getDamage() * 2 ) - player.getPlayerDefence();

        if (damageToTake <= 0) {
            damageToTake = 1;
        }

        player.takeDamage(damageToTake);
        out.append("\n\nThe red slime smothered you for " + damageToTake + " damage!");
        PlaySound.play((getClass().getClassLoader().getResource("RedSlimeSmother.wav")));
        PlaySound.play((getClass().getClassLoader().getResource(player.getPlayerHurtSounds())));


    }

}