package com.company;

import javax.swing.*;
class MegaBlueSlime extends Enemy {

    public MegaBlueSlime() {
        super.setName("Mega Blue Slime");
        super.setMaxHealth(250);
        super.setHealth(250);
        super.setDamage(18);
        super.setHitChance(65);
        super.setDodgeChance(10);
        super.setLootAmount(275);
        super.setLevelRangeMin(10);
        super.setLevelRangeMax(16);
        super.setXP(225);
        super.setDefence(3);
        super.setEncounterChance(30);

        super.setSpecialMoveChance(20);

        super.setPNGFileName((getClass().getClassLoader().getResource("Mega_Blue_Slime.png")));
        super.setSpawnSoundFileName((getClass().getClassLoader().getResource("MegaBlueSlimeSpawn.wav")));
        super.setEnemyType("Slime");

        super.setItemDropChance(10);

        super.setHealthPercentToFleeAt(0.1);
        super.setChanceToFlee(10);

        super.setItemDrop(new Item("Mega Slime Sword", "Increases damage by +18 when equipped", "Weapon", 18, 0, 14));
    }

    public void specialMove(Player player, JTextArea out) {

        double damageToTake = (super.getDamage() * 2 ) - player.getPlayerDefence();

        if (damageToTake <= 0) {
            damageToTake = 1;
        }

        player.takeDamage(damageToTake);
        out.append("\n\nThe mega blue slime smothered you for " + damageToTake + " damage!");
        PlaySound.play((getClass().getClassLoader().getResource("MegaSlimeSmother.wav")));
        PlaySound.play((getClass().getClassLoader().getResource(player.getPlayerHurtSounds())));

    }

}