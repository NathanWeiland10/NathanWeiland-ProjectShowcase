package com.company;

import javax.swing.*;
class MetalSlime extends Enemy {

    public MetalSlime() {
        super.setName("Metal Slime");
        super.setMaxHealth(8);
        super.setHealth(8);
        super.setDamage(10);
        super.setHitChance(75);
        super.setDodgeChance(30);
        super.setLootAmount(150);
        super.setLevelRangeMin(2);
        super.setLevelRangeMax(8);
        super.setXP(600);
        super.setDefence(99);
        super.setEncounterChance(5);

        super.setSpecialMoveChance(20);

        super.setPNGFileName((getClass().getClassLoader().getResource("Metal_Slime.png")));
        super.setSpawnSoundFileName((getClass().getClassLoader().getResource("MetalSlimeSpawn.wav")));
        super.setEnemyType("Slime");

        super.setItemDropChance(10);

        super.setHealthPercentToFleeAt(0.3);
        super.setChanceToFlee(35);

        super.setItemDrop(new Item("Pure Metal Sword", "Increases damage by +10 when equipped", "Weapon", 10, 0, 6));
    }

    public void specialMove(Player player, JTextArea out) {

        double damageToTake = 30 - player.getPlayerDefence();

        if (damageToTake <= 0) {
            damageToTake = 1;
        }

        player.takeDamage(damageToTake);
        out.append("\n\nThe metal slime hit you with a fireball for " + damageToTake + " damage!");
        PlaySound.play((getClass().getClassLoader().getResource("Fireball.wav")));
        PlaySound.play((getClass().getClassLoader().getResource(player.getPlayerHurtSounds())));

    }

}