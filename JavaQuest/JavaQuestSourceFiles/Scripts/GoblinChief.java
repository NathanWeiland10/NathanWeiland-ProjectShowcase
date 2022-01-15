package com.company;

import javax.swing.*;
class GoblinChief extends Enemy {

    public GoblinChief() {
        super.setName("Goblin Chief");
        super.setMaxHealth(100);
        super.setHealth(100);
        super.setDamage(20);
        super.setHitChance(55);
        super.setDodgeChance(5);
        super.setLootAmount(200);
        super.setLevelRangeMin(7);
        super.setLevelRangeMax(14);
        super.setXP(150);
        super.setDefence(8);
        super.setEncounterChance(35);

        super.setSpecialMoveChance(10);

        super.setPNGFileName((getClass().getClassLoader().getResource("Goblin_Chief.png")));
        super.setSpawnSoundFileName((getClass().getClassLoader().getResource("GoblinChiefSpawn.wav")));
        super.setEnemyType("Goblin");

        super.setItemDropChance(10);

        super.setHealthPercentToFleeAt(0.1);
        super.setChanceToFlee(15);

        super.setItemDrop(new Item("Goblin Chief Sword", "Increases damage by by +11 when equipped", "Weapon", 11, 0, 10));
    }

    public void specialMove(Player player, JTextArea out) {

        double damageToTake = (super.getDamage() * 2 ) - player.getPlayerDefence();

        if (damageToTake <= 0) {
            damageToTake = 1;
        }

        player.takeDamage(damageToTake);
        out.append("\n\nThe goblin chief bashed you for " + damageToTake + " damage!");
        PlaySound.play((getClass().getClassLoader().getResource(player.getPlayerHurtSounds())));

    }

}