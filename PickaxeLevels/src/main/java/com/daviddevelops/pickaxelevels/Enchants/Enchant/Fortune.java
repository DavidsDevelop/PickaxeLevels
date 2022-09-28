package com.daviddevelops.pickaxelevels.Enchants.Enchant;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.CustomEnchant;
import com.daviddevelops.pickaxelevels.Pickaxe;
import com.daviddevelops.pickaxelevels.PickaxeLevels;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerData;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerManager;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Fortune extends CustomEnchant {

    public Fortune(){
        setEventType("BlockBreakEvent");
    }


    @Override
    public String getName() {
        return "Fortune";
    }

    @Override
    public String getDescription() {
        return "Increased block drops";
    }

    @Override
    public void perform(Player player, Block broken) {
        ConfigurationSection configSection = ConfigManager.getInstance().getConfig("EnchantSettings.yml").getConfigurationSection(getName());
        int bonusPerLevel = configSection.getInt("bonusPerLevel");
        configSection = ConfigManager.getInstance().getConfig("EnchantSettings.yml").getConfigurationSection("Merchant");
        int merchantBonus = configSection.getInt("bonusPerLevel");
        CompoundTag tag = Pickaxe.getInstance().getCompoundTag(player.getItemInHand());
        int currentLevel = tag.getInt(getName());
        int currentMerchantlevel = tag.getInt("Merchant");
        ConfigurationSection CS = ConfigManager.getInstance().getConfig("Blocks.yml");
        int value = CS.getInt(broken.getBlockData().getMaterial().name() + ".value");;
        double money = value + value*(currentLevel*(bonusPerLevel+(currentMerchantlevel*merchantBonus)));
        PickaxeLevels.econ.depositPlayer(player, money);
        String msg = ConfigManager.getInstance().getConfig("Settings.yml").getString("");
        player.sendMessage();
    }
}
