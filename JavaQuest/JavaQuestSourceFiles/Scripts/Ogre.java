package com.company;

import javax.swing.*;
class Ogre extends Enemy {

    public Ogre() {
        super.setName("Ogre");
        super.setMaxHealth(350);
        super.setHealth(350);
        super.setDamage(60);
        super.setHitChance(25);
        super.setDodgeChance(5);
        super.setLootAmount(400);
        super.setLevelRangeMin(12);
        super.setLevelRangeMax(17);
        super.setXP(450);
        super.setDefence(8);
        super.setEncounterChance(20);

        super.setSpecialMoveChance(20);

        super.setPNGFileName((getClass().getClassLoader().getResource("Ogre.png")));
        super.setSpawnSoundFileName((getClass().getClassLoader().getResource("OgreSpawn.wav")));
        super.setEnemyType("Ogre");

        super.setItemDropChance(5);

        super.setHealthPercentToFleeAt(0.1);
        super.setChanceToFlee(5);

        super.setItemDrop(new Item("Ogre Club", "Increases damage by by +22 when equipped", "Weapon", 22, 0, 20));
    }

    public void specialMove(Player player, JTextArea out) {

        double damageToTake = super.getDamage();

        if (damageToTake <= 0) {
            damageToTake = 1;
        }

        player.takeDamage(damageToTake);
        out.append("\n\nThe ogre crushed you for " + damageToTake + " damage!");
        PlaySound.play((getClass().getClassLoader().getResource(player.getPlayerHurtSounds())));

    }

}