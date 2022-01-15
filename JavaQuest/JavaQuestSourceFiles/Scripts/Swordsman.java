package com.company;

import javax.swing.*;
class Swordsman extends Enemy {

    public Swordsman() {
        super.setName("Swordsman");
        super.setMaxHealth(75);
        super.setHealth(75);
        super.setDamage(15);
        super.setHitChance(70);
        super.setDodgeChance(15);
        super.setLootAmount(150);
        super.setLevelRangeMin(6);
        super.setLevelRangeMax(12);
        super.setXP(125);
        super.setDefence(12);
        super.setEncounterChance(30);

        super.setSpecialMoveChance(15);

        super.setPNGFileName((getClass().getClassLoader().getResource("Swordsman.png")));
        super.setSpawnSoundFileName((getClass().getClassLoader().getResource("SwordsmanSpawn.wav")));
        super.setEnemyType("Human");

        super.setItemDropChance(10);

        super.setHealthPercentToFleeAt(0.0);
        super.setChanceToFlee(100);

        super.setItemDrop(new Item("Knight Helmet", "Increases defence by +10 when equipped", "Helmet", 10, 0, 9));
    }

    public void specialMove(Player player, JTextArea out) {

        double damageToTake = super.getDamage();

        if (damageToTake <= 0) {
            damageToTake = 1;
        }

        player.takeDamage(damageToTake);
        out.append("\n\nThe swordsman sliced through your armor for " + damageToTake + " damage!");
        PlaySound.play((getClass().getClassLoader().getResource(player.getPlayerHurtSounds())));

    }

}