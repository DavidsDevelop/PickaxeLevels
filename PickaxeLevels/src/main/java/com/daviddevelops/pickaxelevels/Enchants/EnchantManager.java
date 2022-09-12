package com.daviddevelops.pickaxelevels.Enchants;


import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.Enchant.Explosive;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class EnchantManager {

    private static EnchantManager single_inst = null;
    private Plugin plugin = null;
    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    protected static ArrayList<CustomEnchant> enchantments = new ArrayList<>();


    public ArrayList<CustomEnchant> getEnchantments(String eventType){
        ArrayList<CustomEnchant> enchants = new ArrayList<>();
        enchantments.forEach( (e) -> {if(e.getEventType().equalsIgnoreCase(eventType)){enchants.add(e);};});
        return enchants;
    }

    public static void registerNewEnchant(CustomEnchant enchant){
        enchantments.add(enchant);
    }

    public static void registerEnchants(){
        enchantments.add(new Explosive());
    }

    public static EnchantManager getInstance() {
        if (single_inst == null) {
            single_inst = new EnchantManager();
            registerEnchants();
        }
        return single_inst;
    }

}
