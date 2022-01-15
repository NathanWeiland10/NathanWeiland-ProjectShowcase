package com.company;

import javax.swing.*;
class BabyDragon extends Enemy {

    public BabyDragon() {
        super.setName("Baby Dragon");
        super.setMaxHealth(150);
        super.setHealth(150);
        super.setDamage(25);
        super.setHitChance(60);
        super.setDodgeChance(35);
        super.setLootAmount(225);
        super.setLevelRangeMin(9);
        super.setLevelRangeMax(13);
        super.setXP(200);
        super.setDefence(16);
        super.setEncounterChance(25);

        super.setSpecialMoveChance(20);

        super.setPNGFileName((getClass().getClassLoader().getResource("Baby_Dragon.png")));
        super.setSpawnSoundFileName((getClass().getClassLoader().getResource("BabyDragonSpawn.wav")));
        super.setEnemyType("Dragon");

        super.setItemDropChance(10);

        super.setHealthPercentToFleeAt(0.2);
        super.setChanceToFlee(25);

        super.setItemDrop(new Item("Scaly Shield", "Increases defence by +16 when equipped", "Shield", 16, 0, 13));
    }

    public void specialMove(Player player, JTextArea out) {

        double damageToTake = 45 - player.getPlayerDefence();
        if (damageToTake <= 0) {
            damageToTake = 1;
        }

        player.takeDamage(damageToTake);
        out.append("\n\nThe baby dragon hit you with a fireball for " + damageToTake + " damage!");
        PlaySound.play((getClass().getClassLoader().getResource("Fireball.wav")));
        PlaySound.play((getClass().getClassLoader().getResource(player.getPlayerHurtSounds())));

    }

}