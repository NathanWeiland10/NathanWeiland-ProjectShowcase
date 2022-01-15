package com.company;

import javax.swing.*;
class SkeletonWarrior extends Enemy {

    public SkeletonWarrior() {
        super.setName("Skeleton Warrior");
        super.setMaxHealth(200);
        super.setHealth(200);
        super.setDamage(25);
        super.setHitChance(55);
        super.setDodgeChance(20);
        super.setLootAmount(325);
        super.setLevelRangeMin(11);
        super.setLevelRangeMax(17);
        super.setXP(300);
        super.setDefence(18);
        super.setEncounterChance(30);

        super.setSpecialMoveChance(20);

        super.setPNGFileName((getClass().getClassLoader().getResource("Skeleton_Warrior.png")));
        super.setSpawnSoundFileName((getClass().getClassLoader().getResource("SkeletonWarriorSpawn.wav")));
        super.setEnemyType("Undead");

        super.setItemDropChance(10);

        super.setHealthPercentToFleeAt(0.2);
        super.setChanceToFlee(25);

        super.setItemDrop(new Item("Skeleton Armor", "Increases defence by +20 when equipped", "Breastplate", 20, 0, 16));
    }

    public void specialMove(Player player, JTextArea out) {

        double damageToTake = super.getDamage();

        if (damageToTake <= 0) {
            damageToTake = 1;
        }

        player.takeDamage(damageToTake);
        out.append("\n\nThe skeleton warrior cleaved through your armor for " + damageToTake + " damage!");
        PlaySound.play((getClass().getClassLoader().getResource(player.getPlayerHurtSounds())));

    }

}