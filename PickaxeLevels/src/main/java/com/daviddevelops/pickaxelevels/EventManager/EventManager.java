package com.daviddevelops.pickaxelevels.EventManager;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.EnchantManager;
import com.daviddevelops.pickaxelevels.GUIManager.UpgradeManager;
import com.daviddevelops.pickaxelevels.Pickaxe;
import com.daviddevelops.pickaxelevels.PickaxeLevels;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class EventManager implements Listener {
    EnchantManager enchantManager;
    Pickaxe pickaxe;
    PlayerManager playerManager;
    UpgradeManager upgradeManager;
    public EventManager(){
        this.enchantManager = EnchantManager.getInstance();
        this.pickaxe = Pickaxe.getInstance();
        this.playerManager = PlayerManager.getInstance();
        this.upgradeManager = new UpgradeManager();
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player =event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_AIR && pickaxe.isPickaxe(player)){
            if(pickaxe.hasValues(player.getItemInHand())){
                upgradeManager.openInventory(player);
            }
        }
    }


    @EventHandler
    public void onInteractInventory(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player){
            Player player = (Player) event.getWhoClicked();
            Inventory i = event.getInventory();
            if(event.getView().getTitle().equalsIgnoreCase("Pickaxe upgrades")){
                upgradeManager.interacted(player, event.getSlot(), event);
            }
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        playerManager.addPlayer(event.getPlayer());
        playerManager.getPlayer(event.getPlayer()).getPlayer().sendMessage("TEST");
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        playerManager.removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onPickaxeBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(pickaxe.isPickaxe(player)){
            ItemStack i = player.getItemInHand();
            if(!pickaxe.hasValues(i)){
                //Create values
                i = pickaxe.createValues(i);
            }
            //Add to values
            i = pickaxe.addValues(i, block);
            playerManager.getPlayer(player).addMiningXP(ConfigManager.getInstance().getConfig("Blocks.yml").getInt(block.getBlockData().getMaterial().name() + ".XP"));
            //addBalance(player(), block());
            //i = enchantManager.upgradeEnchant(1, playerManager.getPlayer(player),i,"Explosive");
            //Set pickaxe
            player.setItemInHand(i);
            enchantManager.triggerEnchantments(enchantManager.getEnchantments(event.getEventName()), player, block);
        }
    }

}
