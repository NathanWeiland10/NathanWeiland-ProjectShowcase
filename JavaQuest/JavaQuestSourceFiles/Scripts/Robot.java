package com.company;

import javax.swing.*;
class Robot extends Enemy {

    public Robot() {
        super.setName("Robot");
        super.setMaxHealth(150);
        super.setHealth(150);
        super.setDamage(35);
        super.setHitChance(75);
        super.setDodgeChance(50);
        super.setLootAmount(450);
        super.setLevelRangeMin(12);
        super.setLevelRangeMax(18);
        super.setXP(375);
        super.setDefence(35);
        super.setEncounterChance(20);

        super.setSpecialMoveChance(10);

        super.setPNGFileName((getClass().getClassLoader().getResource("Robot.png")));
        super.setSpawnSoundFileName((getClass().getClassLoader().getResource("RobotSpawn.wav")));
        super.setEnemyType("Robot");

        super.setItemDropChance(10);

        super.setHealthPercentToFleeAt(0.1);
        super.setChanceToFlee(20);

        super.setItemDrop(new Item("Robot Shield", "Increases defence by +25 when equipped", "Shield", 25, 0, 17));
    }

    public void specialMove(Player player, JTextArea out) {

        double damageToTake = 50;

        player.takeDamage(damageToTake);
        out.append("\n\nThe robot hit you with a laser for " + damageToTake + " damage!");
        PlaySound.play((getClass().getClassLoader().getResource("RobotLaserAttack.wav")));
        PlaySound.play((getClass().getClassLoader().getResource(player.getPlayerHurtSounds())));

    }

}