package com.company;

import javax.swing.*;
class Mimic extends Enemy {

    public Mimic() {
        super.setName("Mimic");
        super.setMaxHealth(45);
        super.setHealth(45);
        super.setDamage(12);
        super.setHitChance(60);
        super.setDodgeChance(20);
        super.setLootAmount(100);
        super.setLevelRangeMin(3);
        super.setLevelRangeMax(6);
        super.setXP(80);
        super.setDefence(5);
        super.setEncounterChance(30);
        super.setSpecialMoveChance(25);
        super.setPNGFileName((getClass().getClassLoader().getResource("Mimic.png")));
        super.setSpawnSoundFileName((getClass().getClassLoader().getResource("MimicSpawn.wav")));
        super.setEnemyType("Mimic");

        super.setItemDropChance(15);

        super.setHealthPercentToFleeAt(0.2);
        super.setChanceToFlee(20);

        super.setItemDrop(new Item("Mimic ChestPiece", "Increases defence by +7 when equipped", "Breastplate", 7, 0, 5));
    }

    public void specialMove(Player player, JTextArea out) {

        out.append("\n\nThe mimic cursed you for 3 turns!");
        player.setStatusEffect(4, "Cursed");
        PlaySound.play((getClass().getClassLoader().getResource("MimicCurse.wav")));


    }

}