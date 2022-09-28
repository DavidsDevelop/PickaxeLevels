package com.daviddevelops.pickaxelevels.Expansions;

import com.daviddevelops.pickaxelevels.ConfigManager.ConfigManager;
import com.daviddevelops.pickaxelevels.PickaxeLevels;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerData;
import com.daviddevelops.pickaxelevels.PlayerManager.PlayerManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PickaxeLevelsExpansion extends PlaceholderExpansion {

    private final PickaxeLevels plugin;

    public PickaxeLevelsExpansion(PickaxeLevels plugin){
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "Pickaxes";
    }

    @Override
    public @NotNull String getAuthor() {
        return "David Develops - MadHatter#9090";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return false; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        //User level, XPTillNextLevel,
        if(params.equalsIgnoreCase("Amethysts")){
            Player player1 = (Player) player;
            PlayerData data = PlayerManager.getInstance().getPlayer(player1);
            return String.valueOf(data.getAmethysts());
        }

        if(params.equalsIgnoreCase("PlayerLevel")) {
            Player player1 = (Player) player;
            PlayerData data = PlayerManager.getInstance().getPlayer(player1);
            return String.valueOf(data.getMiningLevel());
        }

        if(params.equalsIgnoreCase("XPTillNextLevel")){
            Player player1 = (Player) player;
            PlayerData data = PlayerManager.getInstance().getPlayer(player1);
            return String.valueOf(data.getXPTillNextLevel());
        }

        if(params.equalsIgnoreCase("PlayerXP")){
            Player player1 = (Player) player;
            PlayerData data = PlayerManager.getInstance().getPlayer(player1);
            return String.valueOf(data.getMiningXP());
        }

        return null; // Placeholder is unknown by the Expansion
    }
}
