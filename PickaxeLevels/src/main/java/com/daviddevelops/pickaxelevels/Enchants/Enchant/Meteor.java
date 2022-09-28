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

public class Meteor extends CustomEnchant {

    public Meteor(){
        setEventType("BlockBreakEvent");
    }


    @Override
    public String getName() {
        return "Meteor";
    }

    @Override
    public String getDescription() {
        return "Increases fortune gains";
    }

    @Override
    public void perform(Player player, Block broken) {
        ConfigurationSection configSection = ConfigManager.getInstance().getConfig("EnchantSettings.yml").getConfigurationSection(getName());
        int chancePerLevel = configSection.getInt("chancePerLevel");
        CompoundTag tag = Pickaxe.getInstance().getCompoundTag(player.getItemInHand());
        int currentLevel = tag.getInt(getName());
        double procChance = chancePerLevel * currentLevel;

        double chance = procChance / 100;
        double t = Math.random();
        if (t < chance) {

        }
    }
}
