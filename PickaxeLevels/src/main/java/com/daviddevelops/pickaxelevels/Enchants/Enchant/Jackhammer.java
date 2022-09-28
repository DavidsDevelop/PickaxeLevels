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
import java.util.Set;

public class Jackhammer extends CustomEnchant {

    public Jackhammer(){
        setEventType("BlockBreakEvent");
    }


    @Override
    public String getName() {
        return "Jackhammer";
    }

    @Override
    public String getDescription() {
        return "Increases fortune gains";
    }

    @Override
    public void perform(Player player, Block broken) {
        ConfigurationSection configSection = ConfigManager.getInstance().getConfig("EnchantSettings.yml").getConfigurationSection(getName());
        int chancePerLevel = configSection.getInt("chancePerLevel");
        CompoundTag tag = Pickaxe.getInstance().getCompoundTag(player.getItemInHand());
        int currentLevel = tag.getInt(getName());
        double procChance = chancePerLevel * currentLevel;

        double chance = procChance / 100;
        double t = Math.random();
        if (t < chance) {
            Bukkit.getScheduler().runTask(EnchantManager.getInstance().getPlugin(), new Runnable() {
                @Override
                public void run() {
                    Location loc = broken.getLocation();
                    World w = player.getWorld();
                    int maxX, maxZ, minZ, minX;
                    maxX = loc.getBlockX() + 5;
                    maxZ = loc.getBlockZ() + 5;
                    minX = loc.getBlockX() - 5;
                    minZ = loc.getBlockZ() - 5;
                    ArrayList<Material> blocks = new ArrayList<>();
                    Set<ProtectedRegion> regions = getRegions(loc);
                    if (regions == null) {
                        return;
                    }
                    for (ProtectedRegion region : regions) {
                        maxX = region.getMaximumPoint().getX();
                        maxZ = region.getMaximumPoint().getZ();
                        minX = region.getMinimumPoint().getX();
                        minZ = region.getMinimumPoint().getZ();
                    }
                    for (int z = minZ; z < maxZ; z++) {
                        for (int x = minX; x < maxX; x++) {
                            Block b = w.getBlockAt(new Location(w, x, loc.getY(), z));
                            blocks.add(b.getType());
                            if (b.getType() != Material.AIR) {
                                if (b.getType() != Material.BEDROCK) {
                                    b.setType(Material.AIR);
                                } else {
                                    calculateReward(player, blocks);
                                    return;
                                }
                            }
                        }
                    }
                    calculateReward(player, blocks);
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

    public Set<ProtectedRegion> getRegions(final Location block) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        final RegionManager rgm = container.get(BukkitAdapter.adapt(block.getWorld()));
        final ApplicableRegionSet ars = rgm.getApplicableRegions(BlockVector3.at(block.getX(), block.getY(), block.getZ()));
        if(ars.testState(null, PickaxeLevels.ENCHANT_SAFE)){
            return ars.getRegions();
        }
        return null;
    }
}
