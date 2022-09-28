package com.daviddevelops.pickaxelevels.Enchants.Enchant;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.CustomEnchant;
import com.daviddevelops.pickaxelevels.Pickaxe;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerData;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerManager;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Amethysts extends CustomEnchant {

    public Amethysts(){
        setEventType("BlockBreakEvent");
    }


    @Override
    public String getName() {
        return "Crystallized";
    }

    @Override
    public String getDescription() {
        return "Increased Amethysts drops";
    }

    @Override
    public void perform(Player player, Block broken) {
        ConfigurationSection configSection = ConfigManager.getInstance().getConfig("EnchantSettings.yml").getConfigurationSection(getName());
        int chancePerLevel = configSection.getInt("chancePerLevel");
        CompoundTag tag = Pickaxe.getInstance().getCompoundTag(player.getItemInHand());
        int currentLevel = tag.getInt(getName());
        int baseChance = configSection.getInt("baseChance");
        double procChance = baseChance + chancePerLevel*currentLevel;

        double chance = procChance/100;
        double t = Math.random();
        if(t < chance){
            PlayerData data = PlayerManager.getInstance().getPlayer(player);
            int amount = (int) (Math.random() * 100) + (currentLevel*3);
            data.addAmethysts(amount);
            player.sendMessage("Total Crystals " + data.getAmethysts());
        }
    }
}
