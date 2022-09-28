package com.daviddevelops.pickaxelevels;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.CustomEnchant;
import com.daviddevelops.pickaxelevels.Enchants.EnchantManager;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerData;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Pickaxe {

    private static final List<Material> PK = new ArrayList<Material>();
    private static Pickaxe single_inst = null;
    private Plugin plugin = null;
    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }


    public boolean levelCheck(int xp, int level){
        ConfigurationSection CS = ConfigManager.getInstance().getConfig("Settings.yml");
        return xp > CS.getInt("PickaxeXPCost") * level * CS.getInt("PickaxeXPMultiplier");
    }

    public int calculateXP(Block blockBroken, ConfigurationSection CS){
        //Get Block broken from Configs XP
        return CS.getInt(blockBroken.getBlockData().getMaterial().name() + ".XP");
    }

    public boolean isPickaxe(Player p){
        if(PK.contains(p.getItemInHand().getType())){
            return true;
        }
        return false;
    }

    public boolean hasValues(ItemStack pickaxeI){
        CompoundTag tag = getCompoundTag(pickaxeI);
        return tag.contains("BlocksBroken");
    }

    public ItemStack addValues(ItemStack pickaxeI, Block broken){
        CompoundTag tag = getCompoundTag(pickaxeI);
        ConfigurationSection CS = ConfigManager.getInstance().getConfig("Blocks.yml");

        int blocksbroken = tag.getInt("BlocksBroken")+1;
        int pickaxeExperience = tag.getInt("PickaxeExperience");
        int pickaxeLevel = tag.getInt("PickaxeLevel");
        pickaxeExperience += calculateXP(broken, CS);
        if(levelCheck(pickaxeExperience, pickaxeLevel)){
            pickaxeExperience = 0;
            pickaxeLevel++;
        }

        tag.putInt("BlocksBroken", blocksbroken);
        tag.putInt("PickaxeExperience", pickaxeExperience);
        tag.putInt("PickaxeLevel", pickaxeLevel);

        return setCompoundTag(tag, pickaxeI);
    }

    public ItemStack createValues(ItemStack pickaxeI){
        CompoundTag tag = getCompoundTag(pickaxeI);
        tag.putInt("BlocksBroken", 1);
        tag.putInt("PickaxeExperience", 1);
        tag.putInt("PickaxeLevel", 1);
        for(CustomEnchant e : EnchantManager.getInstance().getEnchantments("BlockBreakEvent")){
            tag.putInt(e.getName(), 0);
        }
        return setCompoundTag(tag, pickaxeI);
    }

    public CompoundTag getCompoundTag(ItemStack pickaxeI){
        net.minecraft.world.item.ItemStack pickaxeN = CraftItemStack.asNMSCopy(pickaxeI);
        CompoundTag pickaxeC = (pickaxeN.hasTag()) ? pickaxeN.getTag() : new CompoundTag();
        return pickaxeC;
    }

    public ItemStack setCompoundTag(CompoundTag tag, ItemStack pickaxeI){
        net.minecraft.world.item.ItemStack pickaxeN = CraftItemStack.asNMSCopy(pickaxeI);
        pickaxeN.setTag(tag);
        pickaxeI = CraftItemStack.asBukkitCopy(pickaxeN);
        //Temporary Testing
        ItemMeta pickaxeMeta = pickaxeI.getItemMeta();
        pickaxeMeta = updateLore(tag, pickaxeMeta);
        pickaxeI.setItemMeta(pickaxeMeta);
        return pickaxeI;
    }

    public ItemMeta updateLore(CompoundTag tag, ItemMeta pickaxe){
        int blocksbroken = tag.getInt("BlocksBroken");
        pickaxe.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8[ &6Level &e" + tag.getInt("PickaxeLevel") + " &8]"));
        List<String> newLore = new ArrayList<>();
        newLore.add("&2&lStatistics");
        newLore.add("&aBlocks mined &f" + blocksbroken);
        newLore.add(" ");
        newLore.add("&c&lEnchantments");
        for(CustomEnchant e : EnchantManager.getInstance().getEnchantments("BlockBreakEvent")){
            newLore.add("&e" + e.getName() + " &f" + tag.get(e.getName()));
        }

        List<String> lore = new ArrayList<>();
        for(String line : newLore){
            lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        pickaxe.setLore(lore);
        return pickaxe;
    }


    public static Pickaxe getInstance() {
        if (single_inst == null) {
            single_inst = new Pickaxe();
            PK.add(Material.DIAMOND_PICKAXE);
            PK.add(Material.IRON_PICKAXE);
            PK.add(Material.GOLDEN_PICKAXE);
            PK.add(Material.NETHERITE_PICKAXE);
        }
        return single_inst;
    }


}
