package com.daviddevelops.pickaxelevels;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.EnchantManager;
import net.milkbowl.vault.economy.Economy;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Pickaxe {

    public static ArrayList<Material> PK = new ArrayList<Material>();
    private static Pickaxe single_inst = null;
    private Plugin plugin = null;
    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public static boolean levelCheck(int xp){
        if(xp >= 10){
            return true;
        }
        return false;
    }

    public static int calculateXP(Block blockBroken, ConfigurationSection CS){
        //Get Block broken from Configs XP
        return CS.getInt(blockBroken.getBlockData().getMaterial().name() + ".XP");
    }

    public static boolean isPickaxe(Player p){
        if(PK.contains(p.getItemInHand().getType())){
            return true;
        }
        return false;
    }

    public static boolean hasValues(ItemStack pickaxeI){
        CompoundTag tag = getCompoundTag(pickaxeI);
        if(tag.contains("BlocksBroken")){
            return true;
        }
        return false;
    }

    public static ItemStack addValues(ItemStack pickaxeI, Block broken){
        CompoundTag tag = getCompoundTag(pickaxeI);
        ConfigurationSection CS = ConfigManager.getInstance().getConfig("Blocks.yml");

        int blocksbroken = tag.getInt("BlocksBroken")+1;
        int pickaxeExperience = tag.getInt("PickaxeExperience");
        int pickaxeLevel = tag.getInt("PickaxeLevel");
        pickaxeExperience += calculateXP(broken, CS);
        if(levelCheck(pickaxeExperience)){
            pickaxeExperience = 0;
            pickaxeLevel++;
        }

        tag.putInt("BlocksBroken", blocksbroken);
        tag.putInt("PickaxeExperience", pickaxeExperience);
        tag.putInt("PickaxeLevel", pickaxeLevel);

        return setCompoundTag(tag, pickaxeI);
    }

    public static ItemStack createValues(ItemStack pickaxeI){
        CompoundTag tag = getCompoundTag(pickaxeI);
        tag.putInt("BlocksBroken", 1);
        tag.putInt("PickaxeExperience", 1);
        tag.putInt("PickaxeLevel", 1);
        return setCompoundTag(tag, pickaxeI);
    }

    public static CompoundTag getCompoundTag(ItemStack pickaxeI){
        net.minecraft.world.item.ItemStack pickaxeN = CraftItemStack.asNMSCopy(pickaxeI);
        CompoundTag pickaxeC = (pickaxeN.hasTag()) ? pickaxeN.getTag() : new CompoundTag();
        return pickaxeC;
    }

    public static ItemStack setCompoundTag(CompoundTag tag, ItemStack pickaxeI){
        net.minecraft.world.item.ItemStack pickaxeN = CraftItemStack.asNMSCopy(pickaxeI);
        pickaxeN.setTag(tag);
        pickaxeI = CraftItemStack.asBukkitCopy(pickaxeN);
        //Temporary Testing
        ItemMeta pickaxeMeta = pickaxeI.getItemMeta();
        pickaxeMeta.setDisplayName("Level [" + tag.getInt("PickaxeLevel") + "]");
        pickaxeI.setItemMeta(pickaxeMeta);
        return pickaxeI;
    }


    public static Pickaxe getInstance() {
        if (single_inst == null) {
            single_inst = new Pickaxe();
            PK.add(Material.DIAMOND_PICKAXE);
            PK.add(Material.IRON_PICKAXE);
            PK.add(Material.GOLDEN_PICKAXE);
        }
        return single_inst;
    }

}
