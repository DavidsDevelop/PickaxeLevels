package com.daviddevelops.pickaxelevels.Enchants;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public abstract class CustomEnchant {

    int maxLevel = 200;
    int minLevel = 1;
    int currentLevel = 1;
    double multiplier = 0.5;
    double procChance = currentLevel*multiplier;
    String eventType = null;

    public String getEventType(){
     return eventType;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getProcChance() {
        return currentLevel*multiplier;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    //ex. "Explosive"
    public abstract String getName();

    //ex. "%chances% chance to proc"
    public abstract String getDescription();


    //code for the enchantment
    public abstract void perform(Player player, Block broken);
}
