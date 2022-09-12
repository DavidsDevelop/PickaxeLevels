package com.daviddevelops.pickaxelevels;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.EnchantManager;
import com.daviddevelops.pickaxelevels.EventManager.EventManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PickaxeLevels extends JavaPlugin {

    //Clean and finish main class

    public void onEnable(){
        ConfigManager.getInstance().setPlugin(this);
        EnchantManager.getInstance().setPlugin(this);
        getServer().getPluginManager().registerEvents(new EventManager(), this);

    }

    public void onDisable(){

    }

}
