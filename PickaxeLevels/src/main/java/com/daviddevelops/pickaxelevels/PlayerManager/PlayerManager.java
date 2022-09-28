package com.daviddevelops.pickaxelevels.PlayerManager;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.Enchants.EnchantManager;
import com.daviddevelops.pickaxelevels.PickaxeLevels;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class PlayerManager {

    private static PlayerManager single_inst = null;
    private Plugin plugin = null;
    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public ArrayList<PlayerData> players = new ArrayList<>();


    public PlayerData getPlayer(Player player){
        for (PlayerData data : players) {
            if(data.getPlayer() == player){
                return data;
            }
        }
        //Player does not exist in context.
        return null;
    }

    public void addPlayer(Player player){
        ConfigurationSection CS = ConfigManager.getInstance().getConfig("Players.yml").getConfigurationSection(player.getName());
        if(CS == null){
            PlayerData data = new PlayerData();
            data.setPlayer(player);
            data.setAmethysts(0);
            data.setMiningLevel(0);
            data.setMiningXP(0);
            savePlayer(data);
            players.add(data);
            return;
        }
        PlayerData data = new PlayerData();
        data.setPlayer(player);
        data.setMiningXP(CS.getInt("MiningXP"));
        data.setAmethysts(CS.getInt("Amethysts"));
        data.setMiningLevel(CS.getInt("MiningLevel"));
        players.add(data);
    }

    public void removePlayer(PlayerData data){
        savePlayer(data);
        players.remove(data);
    }
    public void removePlayer(Player player){
        PlayerData data = getPlayer(player);
        if(data ==null){
            return;
        }
        savePlayer(data);
        players.remove(data);
    }

    private void savePlayer(PlayerData data){
        FileConfiguration FC = ConfigManager.getInstance().getConfig("Players.yml");
        ConfigManager.getInstance().setData(FC, data.getPlayer().getName() + ".Amethysts", data.getAmethysts());
        ConfigManager.getInstance().setData(FC, data.getPlayer().getName() + ".MiningXP", data.getMiningXP());
        ConfigManager.getInstance().setData(FC, data.getPlayer().getName() + ".MiningLevel", data.getMiningLevel());
    }

    public void loadPlayers(){
        for (Player p : Bukkit.getOnlinePlayers()) {
            addPlayer(p);
        }
    }

    public PlayerManager(){
        loadPlayers();
    }

    public static PlayerManager getInstance() {
        if (single_inst == null) {
            single_inst = new PlayerManager();
        }
        return single_inst;
    }
}
