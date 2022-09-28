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

public class XPGrabber extends CustomEnchant {

    public XPGrabber(){
        setEventType("BlockBreakEvent");
    }


    @Override
    public String getName() {
        return "XPGrabber";
    }

    @Override
    public String getDescription() {
        return "Increases fortune gains";
    }

    @Override
    public void perform(Player player, Block broken) {
        PlayerData data = PlayerManager.getInstance().getPlayer(player);
        int xp = 0;
        xp += ConfigManager.getInstance().getConfig("Blocks.yml").getInt(broken.getBlockData().getMaterial().name() + ".XP");
        ConfigurationSection configSection = ConfigManager.getInstance().getConfig("EnchantSettings.yml").getConfigurationSection(getName());
        int bonusPerLevel = configSection.getInt("bonusPerLevel");
        CompoundTag tag = Pickaxe.getInstance().getCompoundTag(player.getItemInHand());
        int currentLevel = tag.getInt(getName());
        xp = xp*(currentLevel*bonusPerLevel);
        data.addMiningXP(xp);
    }
}
