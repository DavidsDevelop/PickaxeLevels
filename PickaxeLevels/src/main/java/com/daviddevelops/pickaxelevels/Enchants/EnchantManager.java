package com.daviddevelops.pickaxelevels.Enchants;


import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.Enchant.*;
import com.daviddevelops.pickaxelevels.Pickaxe;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerData;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class EnchantManager {

    private static EnchantManager single_inst = null;
    private Plugin plugin = null;
    ConfigManager configManager;
    Pickaxe pickaxeManager;
    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }
    public Plugin getPlugin(){return plugin;}

    public EnchantManager(){
        this.configManager = ConfigManager.getInstance();
        this.pickaxeManager = Pickaxe.getInstance();
    }

    protected static ArrayList<CustomEnchant> enchantments = new ArrayList<>();


    public ArrayList<CustomEnchant> getEnchantments(String eventType){
        ArrayList<CustomEnchant> enchants = new ArrayList<>();
        enchantments.forEach( (e) -> {if(e.getEventType().equalsIgnoreCase(eventType)){enchants.add(e);};});
        return enchants;
    }

    public void triggerEnchantments(ArrayList<CustomEnchant> enchants, Player player, Block broken){
        for (CustomEnchant e : enchants){
            e.perform(player, broken);
        }
    }

    public static void registerNewEnchant(CustomEnchant enchant){
        enchantments.add(enchant);
    }

    public static void registerEnchants(){
        enchantments.add(new Explosive());
        enchantments.add(new Amethysts());
        enchantments.add(new Fortune());
        enchantments.add(new Jackhammer());
        enchantments.add(new Merchant());
        enchantments.add(new Meteor());
        enchantments.add(new XPGrabber());
    }

    public ItemStack upgradeEnchant(PlayerData data, ItemStack pickaxe, String enchant){
        CompoundTag tag = pickaxeManager.getCompoundTag(pickaxe);
        int currentLevel = tag.getInt(enchant);

        ConfigurationSection configSection = configManager.getConfig("EnchantSettings.yml").getConfigurationSection(enchant);
        int levelCost = configSection.getInt("baseCost") * configSection.getInt("costMultiplier");
        int levelCap = configSection.getInt("maxLevel");
        if(currentLevel < levelCap && data.getAmethysts() > levelCost){
            String message = ConfigManager.getInstance().getConfig("Settings.yml").getString("EnchantUpgradeMessage");
            data.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{Enchant_Name}", enchant)));
            data.setAmethysts(data.getAmethysts() - levelCost);
            tag.putInt(enchant, ++currentLevel);
        }
        return pickaxeManager.setCompoundTag(tag, pickaxe);
    }


    public static EnchantManager getInstance() {
        if (single_inst == null) {
            single_inst = new EnchantManager();
            registerEnchants();
        }
        return single_inst;
    }

}
