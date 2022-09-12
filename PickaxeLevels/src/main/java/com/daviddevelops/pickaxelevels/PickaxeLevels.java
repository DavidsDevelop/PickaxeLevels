package com.daviddevelops.pickaxelevels;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.EnchantManager;
import com.daviddevelops.pickaxelevels.EventManager.EventManager;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PickaxeLevels extends JavaPlugin {

    public static Economy econ = null;
    //Clean and finish main class

    public void onEnable(){

        if (!setupEconomy() ) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        PlayerManager.getInstance().setPlugin(this);
        ConfigManager.getInstance().setPlugin(this);
        EnchantManager.getInstance().setPlugin(this);
        Pickaxe.getInstance().setPlugin(this);
        getServer().getPluginManager().registerEvents(new EventManager(), this);

    }

    public void onDisable(){

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
