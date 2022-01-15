package com.company;

import javax.swing.*;
class Goblin extends Enemy {

    public Goblin() {
        super.setName("Goblin");
        super.setMaxHealth(18);
        super.setHealth(18);
        super.setDamage(5);
        super.setHitChance(60);
        super.setDodgeChance(5);
        super.setLootAmount(10);
        super.setLevelRangeMin(1);
        super.setLevelRangeMax(4);
        super.setXP(20);
        super.setDefence(3);
        super.setEncounterChance(45);

        super.setSpecialMoveChance(20);

        super.setPNGFileName((getClass().getClassLoader().getResource("Goblin.png")));
        super.setSpawnSoundFileName((getClass().getClassLoader().getResource("GoblinSpawn.wav")));
        super.setEnemyType("Goblin");

        super.setItemDropChance(20);

        super.setHealthPercentToFleeAt(0.3);
        super.setChanceToFlee(15);

        super.setItemDrop(new Item("Wooden Shield", "Increases defence by +1 when equipped", "Shield", 1, 0, 1));
    }

    public void specialMove(Player player, JTextArea out) {

        double damageToTake = (super.getDamage() * 2 ) - player.getPlayerDefence();

        if (damageToTake <= 0) {
            damageToTake = 1;
        }

        player.takeDamage(damageToTake);
        out.append("\n\nThe goblin sliced you for " + damageToTake + " damage!");
        PlaySound.play((getClass().getClassLoader().getResource(player.getPlayerHurtSounds())));

    }

}