package com.daviddevelops.pickaxelevels.PlayerManager;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class PlayerData{

    int MiningLevel, MiningXP, XPTillNextLevel;

    int Amethysts;

    Player player;

    public PlayerData(){

    }

    public int getXPTillNextLevel() {
        return XPTillNextLevel;
    }

    public void setXPTillNextLevel(int XPTillNextLevel) {
        this.XPTillNextLevel = XPTillNextLevel;
    }

    public int getMiningLevel() {
        return MiningLevel;
    }

    public void setMiningLevel(int miningLevel) {
        MiningLevel = miningLevel;
        ConfigurationSection CS = ConfigManager.getInstance().getConfig("Settings.yml");
        XPTillNextLevel = CS.getInt("PlayerXPCost") * MiningLevel * CS.getInt("PlayerXPMultiplier");
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

    public void addAmethysts(int amethysts){
        this.Amethysts += amethysts;
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

    public void addMiningXP(int miningXP) {
        this.MiningXP += miningXP;
        levelCheck(MiningXP);
    }

    public void levelCheck(int xp){
        ConfigurationSection CS = ConfigManager.getInstance().getConfig("Settings.yml");
        if(xp >= XPTillNextLevel){
            XPTillNextLevel = CS.getInt("PlayerXPCost") * MiningLevel * CS.getInt("PlayerXPMultiplier");
            player.sendMessage("Leveled up Mining");
            MiningLevel++;
            setMiningXP(0);
        }
    }
}
