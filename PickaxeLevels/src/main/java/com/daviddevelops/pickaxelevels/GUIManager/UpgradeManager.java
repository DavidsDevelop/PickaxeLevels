package com.daviddevelops.pickaxelevels.GUIManager;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.EnchantManager;
import com.daviddevelops.pickaxelevels.Pickaxe;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerData;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerManager;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class UpgradeManager {

    EnchantManager enchantManager;

    public UpgradeManager(){
        enchantManager = EnchantManager.getInstance();
    }

    public void interacted(Player player, int slot, InventoryClickEvent event) {
        event.setCancelled(true);
        switch (slot){
            case 10:
                player.setItemInHand(enchantManager.upgradeEnchant(PlayerManager.getInstance().getPlayer(player), player.getItemInHand(), "Jackhammer"));
                break;
            case 11:
                player.setItemInHand(enchantManager.upgradeEnchant(PlayerManager.getInstance().getPlayer(player), player.getItemInHand(), "Explosive"));
                break;
            case 12:
                player.setItemInHand(enchantManager.upgradeEnchant(PlayerManager.getInstance().getPlayer(player), player.getItemInHand(), "Meteor"));
                break;
            case 13:
                player.setItemInHand(enchantManager.upgradeEnchant(PlayerManager.getInstance().getPlayer(player), player.getItemInHand(), "Fortune"));
                break;
            case 14:
                player.setItemInHand(enchantManager.upgradeEnchant(PlayerManager.getInstance().getPlayer(player), player.getItemInHand(), "Merchant"));
                break;
            case 15:
                player.setItemInHand(enchantManager.upgradeEnchant(PlayerManager.getInstance().getPlayer(player), player.getItemInHand(), "Crystallized"));
                break;
            case 16:
                player.setItemInHand(enchantManager.upgradeEnchant(PlayerManager.getInstance().getPlayer(player), player.getItemInHand(), "XPGrabber"));
                break;
        }
    }

    public void openInventory(Player p){
        ItemStack blackPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        blackPane = setItemDisplay(blackPane, "");
        ItemStack redPane = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        redPane = setItemDisplay(redPane, "");
        Inventory inv = Bukkit.createInventory(p, 27, "Pickaxe upgrades");
        for(int i = 0; i < 9; i++){
            if(i % 2 == 0){
                inv.setItem(i, blackPane);
            } else {
                inv.setItem(i, redPane);
            }
        }
        for(int i = 9; i < 18; i++){
            if(i % 2 == 0){
                inv.setItem(i, redPane);
            } else {
                inv.setItem(i, blackPane);
            }
        }
        for(int i = 18; i < 27; i++){
            if(i % 2 == 0){
                inv.setItem(i, redPane);
            } else {
                inv.setItem(i, blackPane);
            }
        }
        ItemStack jackhammer = new ItemStack(Material.TRIPWIRE_HOOK);
        jackhammer = setItemName(jackhammer, "Jackhammer");
        ItemStack explosive = new ItemStack(Material.TNT);
        explosive = setItemName(explosive, "Explosive");
        ItemStack meteor = new ItemStack(Material.MAGMA_BLOCK);
        meteor = setItemName(meteor, "Meteor");
        ItemStack fortune = new ItemStack(Material.RAW_GOLD);
        fortune = setItemName(fortune, "Fortune");
        ItemStack merchant = new ItemStack(Material.GOLD_NUGGET);
        merchant = setItemName(merchant, "Merchant");
        ItemStack crystallized = new ItemStack(Material.AMETHYST_SHARD);
        crystallized = setItemName(crystallized, "Crystallization");
        ItemStack xpgrabber = new ItemStack(Material.PHANTOM_MEMBRANE);
        xpgrabber = setItemName(xpgrabber, "XPGrabber");

        inv.setItem(10, jackhammer);
        inv.setItem(11, explosive);
        inv.setItem(12, meteor);
        inv.setItem(13, fortune);
        inv.setItem(14, merchant);
        inv.setItem(15, crystallized);
        inv.setItem(16, xpgrabber);

        p.openInventory(inv);
    }

    public void upgrade(int slot, Inventory inventory, Player p){}

    private boolean isUpgradable(){
        return false;
    }

    public ItemStack setItemDisplay(ItemStack i, String s){
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(translate(s));
        i.setItemMeta(meta);
        return i;
    }

    public ItemStack setItemName(ItemStack item, String name){
        ConfigurationSection CS = ConfigManager.getInstance().getConfig("Settings.yml");
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(translate(CS.getString(name+"Name")));
        List<String> newLore = new ArrayList<>();

        newLore = (List<String>) ConfigManager.getInstance().getConfig("Settings.yml").getList(name+"Lore");
        for(int i = 0; i < newLore.size(); i++){
            newLore.set(i, translate(newLore.get(i)));
        }
        meta.setLore(newLore);
        item.setItemMeta(meta);

        return item;
    }

    public String translate(String s){
        return ChatColor.translateAlternateColorCodes('&', "&f" + s);
    }



}
