package com.daviddevelops.pickaxelevels;

import org.bukkit.block.Block;

public class Pickaxe {

    int PickaxeLevel, BlocksBroken, XP, XPTillLevel;
    String OwnerUUID, OwnerName;


    public void levelCheck(){
        if(XP >= XPTillLevel){
            XP = 0;
            PickaxeLevel++;
            //Get LevelXP Multiplier from Configs
            XPTillLevel = 0 * PickaxeLevel;
        }
    }

    public int calculateXP(Block blockBroken){
        //Get Block broken from Configs XP
        int brokenBlocksXP = 5;
        this.XP = brokenBlocksXP;
        levelCheck();
        return brokenBlocksXP;
    }

}
