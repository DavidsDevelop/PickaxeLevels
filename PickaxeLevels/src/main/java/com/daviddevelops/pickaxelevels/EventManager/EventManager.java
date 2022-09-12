package com.daviddevelops.pickaxelevels.EventManager;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.CustomEnchant;
import com.daviddevelops.pickaxelevels.Enchants.EnchantManager;
import com.daviddevelops.pickaxelevels.Pickaxe;
import com.daviddevelops.pickaxelevels.PickaxeLevels;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class EventManager implements Listener {
    ArrayList<Material> PK = new ArrayList<Material>();
    public EventManager(){
        PK.add(Material.DIAMOND_PICKAXE);
        PK.add(Material.IRON_PICKAXE);
        PK.add(Material.GOLDEN_PICKAXE);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        PlayerManager.addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        PlayerManager.removePlayer(event.getPlayer());
    }

    @EventHandler
    public void onPickaxeBlockBreak(BlockBreakEvent event){
        if(Pickaxe.isPickaxe(event.getPlayer())){
            ItemStack i = event.getPlayer().getItemInHand();
            if(!Pickaxe.hasValues(i)){
                //Create values
                event.getPlayer().sendMessage("Creating values");
                i = Pickaxe.createValues(i);
            }
            //Add to values
            i = Pickaxe.addValues(i, event.getBlock());
            addBalance(event.getPlayer(), event.getBlock());
            //Set pickaxe
            event.getPlayer().setItemInHand(i);
        }
    }

    public void addBalance(Player p, Block b){
        ConfigurationSection CS = ConfigManager.getInstance().getConfig("Blocks.yml");
        int amt = CS.getInt(b.getBlockData().getMaterial().name() + ".value");
        PickaxeLevels.econ.depositPlayer(p, amt);
    }
}
