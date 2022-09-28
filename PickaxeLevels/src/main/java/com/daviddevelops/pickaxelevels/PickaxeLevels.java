package com.daviddevelops.pickaxelevels;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.EnchantManager;
import com.daviddevelops.pickaxelevels.EventManager.EventManager;
import com.daviddevelops.pickaxelevels.Expansions.PickaxeLevelsExpansion;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerData;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerManager;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PickaxeLevels extends JavaPlugin {

    public static StateFlag ENCHANT_SAFE;
    public static Economy econ = null;
    //Clean and finish main class

    public void onEnable(){

        if (!setupEconomy() ) {
            getServer().getPluginManager().disablePlugin(this);
        }

        ConfigManager.getInstance().setPlugin(this);
        PlayerManager.getInstance().setPlugin(this);
        EnchantManager.getInstance().setPlugin(this);
        Pickaxe.getInstance().setPlugin(this);
        getServer().getPluginManager().registerEvents(new EventManager(), this);


        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PickaxeLevelsExpansion(this).register();
        }
    }

    @Override
    public void onLoad(){
        registerFlag();
    }

    public void onDisable(){
        for(PlayerData data : PlayerManager.getInstance().players){
            PlayerManager.getInstance().removePlayer(data);
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            System.out.println("NO FAULT FOUND, SEEK HELP");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            System.out.println("NO RSP FOUND, SEEK HELP");
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;


    }

    public void registerFlag(){
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            // create a flag with the name "my-custom-flag", defaulting to true
            StateFlag flag = new StateFlag("Enchant-safe", true);
            registry.register(flag);
            ENCHANT_SAFE = flag; // only set our field if there was no error
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get("Enchant-safe");
            if (existing instanceof StateFlag) {
                ENCHANT_SAFE = (StateFlag) existing;
            } else {
            }
        }
    }

}
