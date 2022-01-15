package com.company;

import javax.swing.*;
class Slime extends Enemy {

    public Slime() {
        super.setName("Slime");
        super.setMaxHealth(12);
        super.setHealth(12);
        super.setDamage(3);
        super.setHitChance(50);
        super.setDodgeChance(5);
        super.setLootAmount(5);
        super.setLevelRangeMin(1);
        super.setLevelRangeMax(3);
        super.setXP(10);
        super.setDefence(2);
        super.setEncounterChance(55);

        super.setSpecialMoveChance(30);

        super.setPNGFileName((getClass().getClassLoader().getResource("BlueSlime.png")));
        super.setSpawnSoundFileName((getClass().getClassLoader().getResource("SlimeSpawn.wav")));
        super.setEnemyType("Slime");

        super.setItemDropChance(10);

        super.setHealthPercentToFleeAt(0.3);
        super.setChanceToFlee(10);

        super.setItemDrop(new Item("Slime Ring", "Increases your damage against slimes by +3 when equipped", "Ring", 0, 0, 1, "+3 damage against slimes"));
    }

    public void specialMove(Player player, JTextArea out) {

        out.append("\n\nThe slime poisoned you for 2 turns!");
        player.setStatusEffect(3, "Poison");
        PlaySound.play((getClass().getClassLoader().getResource("SlimePoison.wav")));

    }

}