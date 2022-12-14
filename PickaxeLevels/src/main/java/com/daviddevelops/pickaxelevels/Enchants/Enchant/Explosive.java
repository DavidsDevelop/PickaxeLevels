package com.daviddevelops.pickaxelevels.Enchants.Enchant;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.CustomEnchant;
import com.daviddevelops.pickaxelevels.Enchants.EnchantManager;
import com.daviddevelops.pickaxelevels.Pickaxe;
import com.daviddevelops.pickaxelevels.PickaxeLevels;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Explosive extends CustomEnchant {

    public Explosive(){
        setEventType("BlockBreakEvent");
    }


    @Override
    public String getName() {
        return "Explosive";
    }

    @Override
    public String getDescription() {
        return "Explodes an area";
    }

    @Override
    public void perform(Player player, Block broken) {
        ConfigurationSection configSection = ConfigManager.getInstance().getConfig("EnchantSettings.yml").getConfigurationSection(getName());
        int chancePerLevel = configSection.getInt("chancePerLevel");
        CompoundTag tag = Pickaxe.getInstance().getCompoundTag(player.getItemInHand());
        int currentLevel = tag.getInt(getName());
        double procChance = chancePerLevel*currentLevel;

        double chance = procChance/100;
        double t = Math.random();
        if(t < chance){
            Bukkit.getScheduler().runTask(EnchantManager.getInstance().getPlugin(), new Runnable() {
                @Override
                public void run() {
                    Location loc = broken.getLocation();
                    World w = player.getWorld();
                    int maxX, maxZ, minZ, minX;
                    maxX = loc.getBlockX() +5;
                    maxZ = loc.getBlockZ() +5;
                    minX = loc.getBlockX() -5;
                    minZ = loc.getBlockZ() -5;
                    ArrayList<Material> blocks = new ArrayList<>();
                    if(getRegions(new Location(w, minX, loc.getBlockY(), minZ)) && getRegions(new Location(w, maxX, loc.getBlockY(), maxZ))){
                        for(int y = loc.getBlockY(); y > -100; y--){
                            for(int z = minZ; z < maxZ; z++){
                                for(int x = minX; x < maxX; x++){
                                    blocks.add(w.getBlockAt(new Location(w, x, y, z)).getType());
                                    Block b = w.getBlockAt(new Location(w, x, y, z));
                                    // Check if block is within region with flags 'Enchant-Safe'
                                    if(b.getType() != Material.AIR){
                                        if(b.getType() != Material.BEDROCK){
                                            b.setType(Material.AIR);
                                        } else{
                                            calculateReward(player, blocks);
                                            return;
                                        }
                                    }
                                }
                            }

                            if(!getRegions(new Location(w,loc.getBlockX(), y, loc.getZ()))){
                                calculateReward(player, blocks);
                                return;
                            }

                        }
                        calculateReward(player, blocks);
                    }
                }
            });
        }
    }

    private void calculateReward(Player player, ArrayList<Material> mats) {
        ConfigurationSection configSection = ConfigManager.getInstance().getConfig("EnchantSettings.yml").getConfigurationSection(getName());
        int bonusPerLevel = configSection.getInt("bonusPerLevel");
        configSection = ConfigManager.getInstance().getConfig("EnchantSettings.yml").getConfigurationSection("Merchant");
        int merchantBonus = configSection.getInt("bonusPerLevel");
        CompoundTag tag = Pickaxe.getInstance().getCompoundTag(player.getItemInHand());
        int currentLevel = tag.getInt("Fortune");
        int currentMerchantlevel = tag.getInt("Merchant");
        ConfigurationSection CS = ConfigManager.getInstance().getConfig("Blocks.yml");
        int value = 0;
        for(Material mat : mats){
            value = CS.getInt(mat.name() + ".value");
            double money = value + value*(currentLevel*(bonusPerLevel+(currentMerchantlevel*merchantBonus)));
            PickaxeLevels.econ.depositPlayer(player, money);
        }

    }

    public boolean getRegions(final Location block) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        final RegionManager rgm = container.get(BukkitAdapter.adapt(block.getWorld()));
        final ApplicableRegionSet ars = rgm.getApplicableRegions(BlockVector3.at(block.getX(), block.getY(), block.getZ()));
        if(ars.testState(null, PickaxeLevels.ENCHANT_SAFE)){
            return true;
        }
        return false;
    }
}
