package com.daviddevelops.pickaxelevels.PlayerManager;

import org.bukkit.entity.Player;

public class PlayerData{

    int MiningLevel, MiningXP, Amethysts;
    Player player;

    public int getMiningLevel() {
        return MiningLevel;
    }

    public void setMiningLevel(int miningLevel) {
        MiningLevel = miningLevel;
    }

    public int getMiningXP() {
        return MiningXP;
    }

    public void setMiningXP(int miningXP) {
        MiningXP = miningXP;
    }

    public int getAmethysts() {
        return Amethysts;
    }

    public void setAmethysts(int amethysts) {
        Amethysts = amethysts;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
