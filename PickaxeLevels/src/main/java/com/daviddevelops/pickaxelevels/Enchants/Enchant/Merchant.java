package com.daviddevelops.pickaxelevels.Enchants.Enchant;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.CustomEnchant;
import com.daviddevelops.pickaxelevels.Pickaxe;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Merchant extends CustomEnchant {

    public Merchant(){
        setEventType("BlockBreakEvent");
    }


    @Override
    public String getName() {
        return "Merchant";
    }

    @Override
    public String getDescription() {
        return "Increases fortune gains";
    }

    @Override
    public void perform(Player player, Block broken) {

    }
}
