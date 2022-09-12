package com.daviddevelops.pickaxelevels.PlayerManager;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.EnchantManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class PlayerManager {

    private static PlayerManager single_inst = null;
    private Plugin plugin = null;
    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public static ArrayList<PlayerData> players = new ArrayList<>();


    public static PlayerData getPlayer(Player player){
        for (PlayerData data : players) {
            if(data.getPlayer() == player){
                return data;
            }
        }
        //Player does not exist in context.
        return null;
    }

    public static void addPlayer(Player player){
        ConfigurationSection CS = ConfigManager.getInstance().getConfig("Players.yml").getConfigurationSection(player.getUniqueId().toString());
        if(CS == null){
            PlayerData data = new PlayerData();
            data.setPlayer(player);
            data.setAmethysts(0);
            data.setMiningLevel(0);
            data.setMiningXP(0);
            savePlayer(data);
            return;
        }
        PlayerData data = new PlayerData();
        data.setPlayer(player);
        data.setMiningXP(CS.getInt("MiningXP"));
        data.setAmethysts(CS.getInt("Amethysts"));
        data.setMiningLevel(CS.getInt("MiningLevel"));
    }

    public static void removePlayer(PlayerData data){
        savePlayer(data);
        players.remove(data);
    }
    public static void removePlayer(Player player){
        PlayerData data = getPlayer(player);
        if(data ==null){
            return;
        }
        savePlayer(data);
        players.remove(data);
    }

    private static void savePlayer(PlayerData data){
        ConfigurationSection CS = ConfigManager.getInstance().getConfig("Players.yml").getConfigurationSection(data.getPlayer().getUniqueId().toString());
        CS.set("Amethysts", data.getAmethysts());
        CS.set("MiningLevel", data.getMiningLevel());
        CS.set("MiningXP", data.getMiningXP());
    }

    public static PlayerManager getInstance() {
        if (single_inst == null) {
            single_inst = new PlayerManager();
        }
        return single_inst;
    }
}
