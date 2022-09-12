package com.daviddevelops.pickaxelevels.Enchants.Enchant;

import com.daviddevelops.pickaxelevels.Enchants.CustomEnchant;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

public class Explosive extends CustomEnchant {

    public Explosive(){
        setEventType("BlockBreakEvent");
    }


    @Override
    public String getName() {
        return "Explosive";
    }

    @Override
    public String getDescription() {
        return "Explodes an area";
    }

    @Override
    public void perform(Player player, Block broken) {
        double chance = getProcChance()/100;
        double t = Math.random();
        if(t < chance){
            Location loc = broken.getLocation();
            World w = player.getWorld();
            w.createExplosion(loc, 5);
        }
    }
}
